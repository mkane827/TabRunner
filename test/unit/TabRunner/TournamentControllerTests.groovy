package TabRunner

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import grails.converters.JSON

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(TournamentController)
@Mock([Tournament, Team, Round, Judge, Pairing])
class TournamentControllerTests {

    void setUp() {
        new Tournament(tournamentName: "Tournament 1")
                .addToTeams(new Team(teamNumber: 1, schoolName: "s1", coachName: "c1"))
                .addToTeams(new Team(teamNumber: 2, schoolName: "s2", coachName: "c2"))
            .save()
    }

    void tearDown() {
    }

    void testList() {
        controller.list()
        assert response.text == (Tournament.list() as JSON).toString()
    }

    void testGenerateRound() {
        Tournament t = Tournament.get(1)
        params.id = 1
        controller.generateRound()

        Round[] rounds = t.getRounds().toArray()
        assert rounds.size() == 1
        Pairing[] pairings = rounds[0].getPairings()
        assert pairings.size() == 1
        Pairing pairing = pairings[0]
        assert pairing.getBallots().size() == 2
        for (team in [pairing.getTeamD(), pairing.getTeamP()]) {
            assert t.getTeams().contains(team)
        }
    }
}
