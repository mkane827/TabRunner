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

    Comparator<Object> teamComparator = new Comparator<Object>() {
        @Override
        int compare(Object team1, Object team2) {
            if (team1.wins != team2.wins) {
                return team2.wins - team1.wins
            }
            else if (team1.combinedStrength != team2.combinedStrength) {
                return team2.combinedStrength - team1.combinedStrength
            }
            else if (team1.opponenetsCombinedStrength != team2.opponentsCombinedStrength) {
                return team2.opponentsCombinedStrength - team1.opponenetscCmbinedStrength
            }
            else {
                return team2.pointDifferential - team1.pointDifferential
            }
        }
    }

    def generateRound() {
        Tournament tournament = Tournament.get(params.id)
        def previousRounds = tournament.getRounds()
        Round round = new Round(roundName: params.roundName)
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
                    judgeConflicts: [],
                    sideConstraint: 0, // 0 for none, 1 for constrained to D, -1 for constrained to P
                    pointDifferential: 0,
                    combinedStrength: 0,
                    opponentCombinedStrength: 0
            ])
        }
        tournamentService.gatherTeamDataFromPreviousRounds(previousRounds, teamData)

        for(team in teamData.keySet()) {
            for (opponent in teamData.get(team).teamConflicts) {
                teamData.get(team).combinedStrength += teamData.get(opponent).wins
            }
        }

        for(team in teamData.keySet()) {
            for (opponent in teamData.get(team).teamConflicts) {
                teamData.get(team).opponentCombinedStrength += teamData.get(opponent).combinedStrength
            }
        }

        // validation
        response.setContentType("JSON")
        if (teams.length > judges.length) {
            def dif = teams.length - judges.length
            response.sendError(400, "Need " + dif + " more judges")
        }
        else if (teams.length % 2 == 1) {
            response.sendError(400, "Need an even number of teams")
        }

        def sortedTeamData = teamData.values().sort(teamComparator)
        def sortedTeamNumbers = []
        for (team in sortedTeamData) {
            sortedTeamNumbers.add(team.team.getTeamNumber())
        }
        def pairings = tournamentService.pairTeams(sortedTeamNumbers.toArray(), teamData)
        tournamentService.assignJudgesToPairings(judges, pairings, teamData)

        for(pairing in pairings) {
            Pairing newPairing = new Pairing(teamP: teamData.get(pairing.teamP).team,
                    teamD: teamData.get(pairing.teamD).team)
            newPairing.addToBallots(new Ballot(judge: pairing.judge1))
            newPairing.addToBallots(new Ballot(judge: pairing.judge2))
            round.addToPairings(newPairing)
            newPairing.save()
            for (ballot in newPairing.getBallots()) {
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
                    judgeConflicts: [],
                    sideConstraint: 0, // 0 for none, 1 for constrained to D, -1 for constrained to P
                    pointDifferential: 0,
                    combinedStrength: 0,
                    opponentCombinedStrength: 0
            ])
        }
        tournamentService.gatherTeamDataFromPreviousRounds(rounds, teamData)

        for(team in teamData.keySet()) {
            for (opponent in teamData.get(team).teamConflicts) {
                teamData.get(team).combinedStrength += teamData.get(opponent).wins
            }
        }

        for(team in teamData.keySet()) {
            for (opponent in teamData.get(team).teamConflicts) {
                teamData.get(team).opponentCombinedStrength += teamData.get(opponent).combinedStrength
            }
        }

        def sortedTeamData = teamData.values().sort(teamComparator)

        render sortedTeamData as JSON
    }

}
