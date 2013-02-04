package TabRunner

import grails.converters.JSON
import tabrunner.BallotService
import tabrunner.TournamentService

class TournamentController {

    def BallotService ballotService
    def TournamentService tournamentService

    def list() {
        render Tournament.list() as JSON
    }

    def create() {
        Tournament tournament = new Tournament(tournamentName: params.tournamentName)
        tournament.save()
        render ""
    }

    def addTeam() {
        Team newTeam = new Team(
                schoolName:params.schoolName,
                teamNumber: params.teamNumber,
                coachName: params.coachName,
        )
        if (params.competitors instanceof String) {
            newTeam.addToCompetitors(new Competitor(competitorName: params.competitors))
        }
        else {
            for (c in params.competitors) {
                newTeam.addToCompetitors(new Competitor(competitorName: c))
            }
        }
        Tournament tournament = Tournament.get(params.id)
        tournament.addToTeams(newTeam)
        tournament.save()
        render ""
    }

    def addJudge() {
        Judge judge = new Judge(judgeName: params.judgeName)
        Tournament tournament = Tournament.get(params.id)
        tournament.addToJudges(judge)
        tournament.save()
        render ""
    }

    def teams() {
        Tournament tournament = Tournament.get(Long.parseLong(params.id))
        render tournament.getTeams() as JSON
    }

    def rounds() {
        render Tournament.get(params.id).getRounds() as JSON
    }

    def judges() {
        render Tournament.get(params.id).getJudges() as JSON
    }

    def generateRound() {
        Tournament tournament = Tournament.get(params.id)
        Round round = new Round(roundName: params.roundName)
        def previousRounds = tournament.getRounds()
        tournament.addToRounds(round)
        round.save()
        Team[] teams = tournament.getTeams().toArray()
        Judge[] judges = tournament.getJudges().toArray()
        def teamData = new HashMap<Integer, Object>()
        for (team in teams) {
            teamData.put(team.teamNumber,  [
                    team: team,
                    wins: 0,
                    teamConflicts: [],
                    judgeConflicts: []
            ])
        }
        tournamentService.gatherTeamDataFromPreviousRounds(previousRounds, teamData)
        def sortedTeamData = teamData.values().sort(new Comparator<Object>() {
            @Override
            int compare(Object team1, Object team2) {
                return team2.wins - team1.wins
            }
        })

        def remainingJudges = []
        remainingJudges.addAll(judges) // list with unused judges

        // validation
        response.setContentType("JSON")
        if (teams.length > judges.length) {
            def dif = teams.length - judges.length
            response.sendError(400, "Need " + dif + " more judges")
        }
        else if (teams.length % 2 == 1) {
            response.sendError(400, "Need an even number of teams")
        }

//        else if (previousRounds.size() % 2 == 1 || false) {
//            //TODO implement side constrained rounds
//        }
        else {
            // Non side constrained rounds
            while (!sortedTeamData.isEmpty()) {
                def teamPData = sortedTeamData.pop()
                def ignoreTeamConflictIndex = 0
                while (teamPData.teamConflicts.contains(sortedTeamData[ignoreTeamConflictIndex].team.getTeamNumber())) {
                    ignoreTeamConflictIndex++
                }
                def teamDData = sortedTeamData.remove(ignoreTeamConflictIndex)

                Pairing pairing = new Pairing(teamD: teamDData.team, teamP: teamPData.team)

                def ignoreJudgeConflictIndex = 0 // Counter to find judges without conflicts
                while (teamPData.judgeConflicts.contains(remainingJudges[ignoreJudgeConflictIndex]
                    || teamDData.judgeConflicts.contains(remainingJudges[ignoreJudgeConflictIndex]))) {
                    ignoreJudgeConflictIndex++ // Either team has a conflict
                }
                pairing.addToBallots(new Ballot(judge: remainingJudges.remove(ignoreJudgeConflictIndex))) // Use judge
                while (teamPData.judgeConflicts.contains(remainingJudges[ignoreJudgeConflictIndex]
                        || teamDData.judgeConflicts.contains(remainingJudges[ignoreJudgeConflictIndex]))) {
                    ignoreJudgeConflictIndex++ // Either team has a conflict
                }
                pairing.addToBallots(new Ballot(judge: remainingJudges.remove(ignoreJudgeConflictIndex))) // Use judge
                round.addToPairings(pairing)
                pairing.save()

                //Todo remove randomizing ballots for new rounds
                for (ballot in pairing.getBallots()) {
                    ballotService.randomizeBallot(ballot)
                }
            }
            round.save()
            tournament.save()
            for (judge in judges) {
                judge.save()
            }
            for (team in teams) {
                team.save()
            }
        }
        render ""
    }

    def generateTeamRankings() {
        Tournament tournament = Tournament.get(params.id)
        def rounds = tournament.getRounds()
        Team[] teams = tournament.getTeams().toArray()
        def teamData = new HashMap<Integer, Object>()
        for (team in teams) {
            teamData.put(team.teamNumber,  [
                    team: team,
                    teamNumber: team.teamNumber,
                    schoolName: team.schoolName,
                    wins: 0,
                    teamConflicts: [],
                    judgeConflicts: []
            ])
        }
        tournamentService.gatherTeamDataFromPreviousRounds(rounds, teamData)
        def sortedTeamData = teamData.values().sort(new Comparator<Object>() {
            @Override
            int compare(Object team1, Object team2) {
                return team2.wins - team1.wins
            }
        })

        render sortedTeamData as JSON
    }

}
