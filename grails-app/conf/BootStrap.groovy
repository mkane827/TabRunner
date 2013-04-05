import TabRunner.Tournament
import TabRunner.Team
import TabRunner.Judge
import TabRunner.Competitor

class BootStrap {

    def init = { servletContext ->
        if (false) {
            Tournament t1 = new Tournament(tournamentName: "Tournament 1")
            t1.save()
            Tournament t2 = new Tournament(tournamentName: "Tournament 2")
            t2.save()

            def team1 = new Team(teamNumber: 123, schoolName: "School 1", coachName: "Coach 1").save()
            for(i in 1..10) {
                team1.addToCompetitors(new Competitor(competitorName: "Team123 Competitor " + i, team: team1).save())
            }
            team1.save()

            def team2 = new Team(teamNumber: 273, schoolName: "School 2", coachName: "Coach 2").save()
            for(i in 1..10) {
                team2.addToCompetitors(new Competitor(competitorName: "Team273 Competitor " + i, team: team2).save())
            }
            team2.save()

            def team3 = new Team(teamNumber: 983, schoolName: "School 3", coachName: "Coach 3").save()
            for(i in 1..10) {
                team3.addToCompetitors(new Competitor(competitorName: "Team983 Competitor " + i, team: team3).save())
            }
            team3.save()

            def team4 = new Team(teamNumber: 235, schoolName: "School 4", coachName: "Coach 4").save()
            for(i in 1..10) {
                team4.addToCompetitors(new Competitor(competitorName: "Team235 Competitor " + i, team: team4).save())
            }
            team4.save()

            t1.addToTeams(team1)
            team1.save()
            t1.addToTeams(team2)
            team2.save()
            t1.addToTeams(team3)
            team3.save()
            t1.addToTeams(team4)
            team4.save()
            for (i in 1..20) {
                t1.addToJudges(new Judge(judgeName: "Judge " + i).save())
            }
            t1.save()


            def team5 = new Team(teamNumber: 435, schoolName: "School 5", coachName: "Coach 5").save()
            for(i in 1..10) {
                team5.addToCompetitors(new Competitor(competitorName: "Team435 Competitor " + i, team: team5).save())
            }
            team5.save()

            def team6 = new Team(teamNumber: 734, schoolName: "School 6", coachName: "Coach 6").save()
            for(i in 1..10) {
                team6.addToCompetitors(new Competitor(competitorName: "Team734 Competitor " + i, team: team6).save())
            }
            team6.save()

            def team7 = new Team(teamNumber: 397, schoolName: "School 7", coachName: "Coach 7").save()
            for(i in 1..10) {
                team7.addToCompetitors(new Competitor(competitorName: "Team397 Competitor " + i, team: team7).save())
            }
            team7.save()

            def team8 = new Team(teamNumber: 582, schoolName: "School 8", coachName: "Coach 8").save()
            for(i in 1..10) {
                team8.addToCompetitors(new Competitor(competitorName: "Team582 Competitor " + i, team: team8).save())
            }
            team8.save()

            t2.addToTeams(team5)
            team5.save()
            t2.addToTeams(team6)
            team6.save()
            t2.addToTeams(team7)
            team7.save()
            t2.addToTeams(team8)
            team8.save()
            for (i in 21..40) {
                t2.addToJudges(new Judge(judgeName: "Judge " + i).save())
            }
            t2.save()
        }
        else if(true) {
            Tournament tournament = new Tournament(tournamentName: "Hello, World")
            tournament.save()

            for (i in 1..50) {
                def team = new Team(teamNumber: 100 + i, schoolName: "School " + i, coachName: "Coach " + i).save()
                for(j in 1..10) {
                    team.addToCompetitors(new Competitor(competitorName: team.getTeamNumber() + " Competitor " + j, team: team).save())
                }
                tournament.addToTeams(team)
                team.save()
                tournament.save()
            }
            for(i in 1..200) {
                tournament.addToJudges(new Judge(judgeName: "Judge " + i).save())
            }
            tournament.save()
        }

    }
    def destroy = {
    }
}
