package TabRunner

import grails.converters.JSON

class BallotController {

    def getBallot() {
        def ballot = Ballot.get(params.id)
        def pairing = ballot.getPairing()
        def teamP = pairing.getTeamP()
        def teamD = pairing.getTeamD()
        def judge = ballot.getJudge()
        def competitors = ["c1", "c2", "c3"]
        def ballotJson = [
                id: ballot.id,
                teamP: teamP.getTeamNumber() + ": " + teamP.getSchoolName(),
                teamD: teamD.getTeamNumber() + ": " + teamD.getSchoolName(),
                competitors: competitors,
                judge: judge.getJudgeName(),
                round: pairing.getRound().getRoundName(),

                pOpening: ballot.pOpening,
                pDirectAttorney1: ballot.pDirectAttorney1,
                pDirectAttorney2: ballot.pDirectAttorney2,
                pDirectAttorney3: ballot.pDirectAttorney3,
                pCrossAttorney1: ballot.pCrossAttorney1,
                pCrossAttorney2: ballot.pCrossAttorney2,
                pCrossAttorney3: ballot.pCrossAttorney3,
                pDirectWitness1: ballot.pDirectWitness1,
                pDirectWitness2: ballot.pDirectWitness2,
                pDirectWitness3: ballot.pDirectWitness3,
                pCrossWitness1: ballot.pCrossWitness1,
                pCrossWitness2: ballot.pCrossWitness2,
                pCrossWitness3: ballot.pCrossWitness3,
                pClosing: ballot.pClosing,

                dOpening: ballot.dOpening,
                dDirectAttorney1: ballot.dDirectAttorney1,
                dDirectAttorney2: ballot.dDirectAttorney2,
                dDirectAttorney3: ballot.dDirectAttorney3,
                dCrossAttorney1: ballot.dCrossAttorney1,
                dCrossAttorney2: ballot.dCrossAttorney2,
                dCrossAttorney3: ballot.dCrossAttorney3,
                dDirectWitness1: ballot.dDirectWitness1,
                dDirectWitness2: ballot.dDirectWitness2,
                dDirectWitness3: ballot.dDirectWitness3,
                dCrossWitness1: ballot.dCrossWitness1,
                dCrossWitness2: ballot.dCrossWitness2,
                dCrossWitness3: ballot.dCrossWitness3,
                dClosing: ballot.dClosing
        ]
        render ballotJson as JSON

    }
}
