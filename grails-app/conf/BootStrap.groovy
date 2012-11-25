import TabRunner.Tournament
import TabRunner.Team
import TabRunner.Judge

class BootStrap {

    def init = { servletContext ->
        Tournament t1 = new Tournament(tournamentName: "Tournament 1")
        t1.save()
        Tournament t2 = new Tournament(tournamentName: "Tournament 2")
        t2.save()

        t1.addToTeams(new Team(teamNumber: 123, schoolName: "School 1", coachName: "Coach 1").save())
        t1.addToTeams(new Team(teamNumber: 273, schoolName: "School 2", coachName: "Coach 2").save())
        t1.addToTeams(new Team(teamNumber: 983, schoolName: "School 3", coachName: "Coach 3").save())
        t1.addToTeams(new Team(teamNumber: 235, schoolName: "School 4", coachName: "Coach 4").save())
        for (i in 1..4) {
            t1.addToJudges(new Judge(judgeName: "Judge " + i).save())
        }
        t1.save()

        t2.addToTeams(new Team(teamNumber: 435, schoolName: "School 5", coachName: "Coach 5").save())
        t2.addToTeams(new Team(teamNumber: 734, schoolName: "School 6", coachName: "Coach 6").save())
        t2.addToTeams(new Team(teamNumber: 397, schoolName: "School 7", coachName: "Coach 7").save())
        t2.addToTeams(new Team(teamNumber: 582, schoolName: "School 8", coachName: "Coach 8").save())
        for (i in 5..8) {
            t2.addToJudges(new Judge(judgeName: "Judge " + i).save())
        }
        t2.save()

    }
    def destroy = {
    }
}
