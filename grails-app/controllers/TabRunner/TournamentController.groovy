package TabRunner

import grails.converters.JSON

class TournamentController {

    def list() {
        render Tournament.list() as JSON
    }

    def create() {
        def content = JSON.parse(request)
        def t = new Tournament(tournamentName: content.getAt("tournamentName"))
        t.save()
        render ""
    }

    def addTeam() {
        def content = JSON.parse(request)
        def newTeam = new Team(
                schoolName:params.schoolName,
                teamNumber: params.teamNumber,
                coachName: params.coachName
        )
        newTeam.save()
        Tournament.get(params.id).teams.add(newTeam)
        render ""
    }

    def teams() {
        def tournament = Tournament.get(Long.parseLong(params.id))
        render tournament.teams as JSON
    }
}
