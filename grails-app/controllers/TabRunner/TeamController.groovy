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
        if(params.competitors instanceof String) {
            def info = JSON.parse(params.competitors)
            if(info.id.toString() != "null") {
                Competitor c = Competitor.get(info.id)
                c.competitorName = info.competitorName
                c.save()
            }
            else {
                team.addToCompetitors(new Competitor(competitorName: info.competitorName, team: team))
            }
        }
        else {
            for(competitor in params.competitors) {
                def info = JSON.parse(competitor)
                if(info.id.toString() != "null") {
                    Competitor c = Competitor.get(info.id)
                    c.competitorName = info.competitorName
                    c.save()
                }
                else {
                    team.addToCompetitors(new Competitor(competitorName: info.competitorName, team: team))
                }
            }
        }
        team.save()
        render ""
    }
}
