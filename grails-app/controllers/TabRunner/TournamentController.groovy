package TabRunner

import grails.converters.JSON

class TournamentController {

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
        for (c in params.competitors) {
            newTeam.addToCompetitors(new Competitor(competitorName: c))
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
        Team[] teams = tournament.getTeams().toArray()
        Judge[] judges = tournament.getJudges().toArray()
        int teamCounter = 0
        int judgeCounter = 0
        while (teamCounter < teams.size()) {
            round.addToPairings(
                    new Pairing(teamD: teams[teamCounter++], teamP: teams[teamCounter++])
                    .addToBallots(new Ballot(judge: judges[judgeCounter++]))
                    .addToBallots(new Ballot(judge: judges[judgeCounter++]))
            )
        }
        tournament.addToRounds(round)
        tournament.save()
        for (judge in judges) {
            judge.save()
        }
        render ""
    }

}
