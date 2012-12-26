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

    def save() {
        def ballot = Ballot.get(params.id)
        ballot.setpOpening(Integer.parseInt(params.pOpening))
        ballot.setpDirectAttorney1(Integer.parseInt(params.pDirectAttorney1))
        ballot.setpDirectAttorney2(Integer.parseInt(params.pDirectAttorney2))
        ballot.setpDirectAttorney3(Integer.parseInt(params.pDirectAttorney3))
        ballot.setpCrossAttorney1(Integer.parseInt(params.pCrossAttorney1))
        ballot.setpCrossAttorney2(Integer.parseInt(params.pCrossAttorney2))
        ballot.setpCrossAttorney3(Integer.parseInt(params.pCrossAttorney3))
        ballot.setpDirectWitness1(Integer.parseInt(params.pDirectWitness1))
        ballot.setpDirectWitness2(Integer.parseInt(params.pDirectWitness2))
        ballot.setpDirectWitness3(Integer.parseInt(params.pDirectWitness3))
        ballot.setpCrossWitness1(Integer.parseInt(params.pCrossWitness1))
        ballot.setpCrossWitness2(Integer.parseInt(params.pCrossWitness2))
        ballot.setpCrossWitness3(Integer.parseInt(params.pCrossWitness3))
        ballot.setpClosing(Integer.parseInt(params.pClosing))

        ballot.setdOpening(Integer.parseInt(params.dOpening))
        ballot.setdDirectAttorney1(Integer.parseInt(params.dDirectAttorney1))
        ballot.setdDirectAttorney2(Integer.parseInt(params.dDirectAttorney2))
        ballot.setdDirectAttorney3(Integer.parseInt(params.dDirectAttorney3))
        ballot.setdCrossAttorney1(Integer.parseInt(params.dCrossAttorney1))
        ballot.setdCrossAttorney2(Integer.parseInt(params.dCrossAttorney2))
        ballot.setdCrossAttorney3(Integer.parseInt(params.dCrossAttorney3))
        ballot.setdDirectWitness1(Integer.parseInt(params.dDirectWitness1))
        ballot.setdDirectWitness2(Integer.parseInt(params.dDirectWitness2))
        ballot.setdDirectWitness3(Integer.parseInt(params.dDirectWitness3))
        ballot.setdCrossWitness1(Integer.parseInt(params.dCrossWitness1))
        ballot.setdCrossWitness2(Integer.parseInt(params.dCrossWitness2))
        ballot.setdCrossWitness3(Integer.parseInt(params.dCrossWitness3))
        ballot.setdClosing(Integer.parseInt(params.dClosing))

        render ""
    }

}
