package TabRunner

import grails.converters.JSON

class RoundController {

    def pairings() {
        Pairing[] pairings = Round.get(params.id).getPairings()
        render(contentType: "text/json") {
            items = array {
                for (pairing in pairings) {
                    teamP: pairing.getTeamP()
                    teamD: pairing.getTeamD()
                    judge1: "Judge 1"
                }
            }
        }
    }
}
