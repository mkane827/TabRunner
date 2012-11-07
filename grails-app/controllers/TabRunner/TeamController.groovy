package TabRunner

import grails.converters.JSON

class TeamController {

    def getCompetitors() {
        render Team.get(params.id).competitors as JSON
    }

    def edit() {
        Team team = Team.get(params.id)
        team.teamNumber = Integer.parseInt(params.teamNumber)
        team.schoolName = params.schoolName
        team.coachName = params.coachName
        for (competitor in params.competitors) {
            def info = JSON.parse(competitor)
            Competitor c = Competitor.get(info.id)
            c.competitorName = info.competitorName
            c.save()
        }
        team.save()
        render ""
    }
}
