package TabRunner

import org.junit.*

class TournamentControllerTests extends GroovyTestCase {

    @Before
    void setUp() {
        Tournament t1 = new Tournament(tournamentName: "Tournament 1")
        Tournament t2 = new Tournament(tournamentName: "Tournament 2")
        t1.save()
        t2.save()

        t1.addToTeams(new Team(teamNumber: 123, schoolName: "School 1", coachName: "Coach 1"))
        t1.addToTeams(new Team(teamNumber: 273, schoolName: "School 2", coachName: "Coach 2"))
        t1.addToTeams(new Team(teamNumber: 983, schoolName: "School 3", coachName: "Coach 3"))
        t1.addToTeams(new Team(teamNumber: 235, schoolName: "School 4", coachName: "Coach 4"))
        for (i in 1..4) {
            t1.addToJudges(new Judge(judgeName: "Judge " + i))
        }
        t1.save()

        t2.addToTeams(new Team(teamNumber: 435, schoolName: "School 5", coachName: "Coach 5"))
        t2.addToTeams(new Team(teamNumber: 734, schoolName: "School 6", coachName: "Coach 6"))
        t2.addToTeams(new Team(teamNumber: 397, schoolName: "School 7", coachName: "Coach 7"))
        t2.addToTeams(new Team(teamNumber: 582, schoolName: "School 8", coachName: "Coach 8"))
        for (i in 5..8) {
            t2.addToJudges(new Judge(judgeName: "Judge " + i))
        }
        t2.save()
    }

    @Test
    void testGenerateRounds() {
        def tournamentController = new TournamentController()
        tournamentController.params.id = 1
        tournamentController.params.roundName = "Round 1"
        tournamentController.generateRound()
        Round round = Tournament.get(1).getRounds().toArray()[0]
        def teams = Tournament.get(1).getTeams()
        assert round.getRoundName() == "Round 1"
        for (pairing in round.getPairings().toArray()) {
            assert teams.contains(pairing.teamP)
            assert teams.contains(pairing.teamP)
        }
    }
}
