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
            newTeam.addToCompetitors(new Competitor(competitorName: params.competitors, team: newTeam))
        }
        else {
            for (c in params.competitors) {
                newTeam.addToCompetitors(new Competitor(competitorName: c, team: newTeam))
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

    def generateIndividualRankings() {
        Tournament tournament = Tournament.get(params.id)
        def attorneyData = new HashMap<Long, Object>()
        def witnessData = new HashMap<Long, Object>()
        def rounds = tournament.getRounds()
        for (round in rounds) {
            for (pairing in round.getPairings()) {
                for (ballot in pairing.getBallots()) {
                    Competitor rank1Attorney = ballot.getRank1Attorney()
                    Competitor rank2Attorney = ballot.getRank2Attorney()
                    Competitor rank3Attorney = ballot.getRank3Attorney()
                    Competitor rank4Attorney = ballot.getRank4Attorney()

                    Competitor rank1Witness = ballot.getRank1Witness()
                    Competitor rank2Witness = ballot.getRank2Witness()
                    Competitor rank3Witness = ballot.getRank3Witness()
                    Competitor rank4Witness = ballot.getRank4Witness()

                    // Attorneys
                    if (rank1Attorney != null) {
                        if (attorneyData.containsKey(rank1Attorney.getId())) {
                            attorneyData.get(rank1Attorney.getId()).ranks += 5
                        }
                        else {
                            Team team = rank1Attorney.getTeam()
                            attorneyData.put(rank1Attorney.getId(), [
                                    competitorName: rank1Attorney.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 5
                            ])
                        }
                    }

                    if (rank2Attorney != null) {
                        if (attorneyData.containsKey(rank2Attorney.getId())) {
                            attorneyData.get(rank2Attorney.getId()).ranks += 4
                        }
                        else {
                            Team team = rank2Attorney.getTeam()
                            attorneyData.put(rank2Attorney.getId(), [
                                    competitorName: rank2Attorney.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 4
                            ])
                        }
                    }

                    if (rank3Attorney != null) {
                        if (attorneyData.containsKey(rank3Attorney.getId())) {
                            attorneyData.get(rank3Attorney.getId()).ranks += 3
                        }
                        else {
                            Team team = rank3Attorney.getTeam()
                            attorneyData.put(rank3Attorney.getId(), [
                                    competitorName: rank3Attorney.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 3
                            ])
                        }
                    }

                    if (rank4Attorney != null) {
                        if (attorneyData.containsKey(rank4Attorney.getId())) {
                            attorneyData.get(rank4Attorney.getId()).ranks += 2
                        }
                        else {
                            Team team = rank4Attorney.getTeam()
                            attorneyData.put(rank4Attorney.getId(), [
                                    competitorName: rank4Attorney.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 2
                            ])
                        }
                    }

                    // Witnesses
                    if (rank1Witness != null) {
                        if (witnessData.containsKey(rank1Witness.getId())) {
                            witnessData.get(rank1Witness.getId()).ranks += 5
                        }
                        else {
                            Team team = rank1Witness.getTeam()
                            witnessData.put(rank1Witness.getId(), [
                                    competitorName: rank1Witness.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 5
                            ])
                        }
                    }

                    if (rank2Witness != null) {
                        if (witnessData.containsKey(rank2Witness.getId())) {
                            witnessData.get(rank2Witness.getId()).ranks += 4
                        }
                        else {
                            Team team = rank2Witness.getTeam()
                            witnessData.put(rank2Witness.getId(), [
                                    competitorName: rank2Witness.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 4
                            ])
                        }
                    }

                    if (rank3Witness != null) {
                        if (witnessData.containsKey(rank3Witness.getId())) {
                            witnessData.get(rank3Witness.getId()).ranks += 3
                        }
                        else {
                            Team team = rank3Witness.getTeam()
                            witnessData.put(rank3Witness.getId(), [
                                    competitorName: rank3Witness.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 3
                            ])
                        }
                    }

                    if (rank4Witness != null) {
                        if (witnessData.containsKey(rank4Witness.getId())) {
                            witnessData.get(rank4Witness.getId()).ranks += 2
                        }
                        else {
                            Team team = rank4Witness.getTeam()
                            witnessData.put(rank4Witness.getId(), [
                                    competitorName: rank4Witness.getCompetitorName(),
                                    teamNumber: team.getTeamNumber(),
                                    schoolName: team.getSchoolName(),
                                    ranks: 2
                            ])
                        }
                    }
                }
            }
        }
        def json = [
                attorney: attorneyData.values(),
                witness: witnessData.values()
        ]
        render json as JSON
    }

}
