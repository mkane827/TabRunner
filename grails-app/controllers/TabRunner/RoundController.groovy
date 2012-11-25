package TabRunner

import grails.converters.JSON

class RoundController {

    def pairings() {
        Pairing[] pairings = Round.get(params.id).getPairings().toArray()
        def pairingJson = []
        for (pairing in pairings) {
            def ballots = pairing.getBallots().toArray()
            def teamP = pairing.getTeamP()
            def teamD = pairing.getTeamD()
            pairingJson.add([
                    id: pairing.id,
                    teamP: teamP.getTeamNumber() + ": " + teamP.getSchoolName(),
                    teamD: teamD.getTeamNumber() + ": " + teamD.getSchoolName(),
                    teamPId: teamP.getId(),
                    teamDId: teamD.getId(),
                    judge1: ballots[0].getJudge().getJudgeName(),
                    judge2: ballots[1].getJudge().getJudgeName(),
                    ballot1: ballots[0].getId(),
                    ballot2: ballots[1].getId()
            ])
        }
        render pairingJson as JSON
    }
}
