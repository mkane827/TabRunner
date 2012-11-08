package TabRunner

import grails.converters.JSON

class TournamentController {

    def list() {
        render Tournament.list() as JSON
    }

    def create() {
        def t = new Tournament(tournamentName: params.tournamentName)
        t.save()
        render ""
    }

    def addTeam() {
        def newTeam = new Team(
                schoolName:params.schoolName,
                teamNumber: params.teamNumber,
                coachName: params.coachName,
        )
        for (c in params.competitors) {
            newTeam.addToCompetitors(new Competitor(competitorName: c))
        }
        newTeam.save()
        Tournament.get(params.id).addToTeams(newTeam)
        render ""
    }

    def teams() {
        def tournament = Tournament.get(Long.parseLong(params.id))
        render tournament.getTeams() as JSON
    }

    def rounds() {
        render Tournament.get(params.id).getRounds() as JSON
    }

    def generateRound() {
        Tournament tournament = Tournament.get(params.id)
        Round round = new Round(roundName: params.roundName)
        Team[] teams = tournament.getTeams().toArray()
        int i = 0
        while (i < teams.size()) {
            round.addToPairings(
                    new Pairing(teamD: teams[i], teamP: teams[++i])
//                    .addToBallots(new Ballot(
//                            judge: new Judge(judgeName: "Judge 1"))
//                    )
//                    .addToBallots(new Ballot(
//                            judge: new Judge(judgeName: "Judge 2"))
//                    )
            )
            ++i
        }
        tournament.addToRounds(round)
        tournament.save()
        render ""
    }

}
