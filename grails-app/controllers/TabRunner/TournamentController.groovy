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
        tournament.addToRounds(round)
        round.save()
        Team[] teams = tournament.getTeams().toArray()
        Judge[] judges = tournament.getJudges().toArray()
        int teamCounter = 0
        int judgeCounter = 0
        response.setContentType("JSON")
        if (teams.length > judges.length) {
            def dif = teams.length - judges.length
            response.sendError(400, "Need " + dif + " more judges")
        }
        else if (teams.length % 2 == 1) {
            response.sendError(400, "Need an even number of teams")
        }
        else {
            while (teamCounter < teams.size()) {
                Pairing pairing = new Pairing(teamD: teams[teamCounter++], teamP: teams[teamCounter++])
                pairing.addToBallots(new Ballot(judge: judges[judgeCounter++]))
                pairing.addToBallots(new Ballot(judge: judges[judgeCounter++]))
                round.addToPairings(pairing)
                pairing.save()
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

}
