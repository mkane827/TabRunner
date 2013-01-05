package tabrunner

import TabRunner.Ballot

class BallotService {

    def randNum = Random.newInstance()

    def randomizeBallot(Ballot ballot) {
        ballot.setpOpening(randNum.nextInt(10) + 1)
        ballot.setpDirectAttorney1(randNum.nextInt(10) + 1)
        ballot.setpDirectAttorney2(randNum.nextInt(10) + 1)
        ballot.setpDirectAttorney3(randNum.nextInt(10) + 1)
        ballot.setpCrossAttorney1(randNum.nextInt(10) + 1)
        ballot.setpCrossAttorney2(randNum.nextInt(10) + 1)
        ballot.setpCrossAttorney3(randNum.nextInt(10) + 1)
        ballot.setpDirectWitness1(randNum.nextInt(10) + 1)
        ballot.setpDirectWitness2(randNum.nextInt(10) + 1)
        ballot.setpDirectWitness3(randNum.nextInt(10) + 1)
        ballot.setpCrossWitness1(randNum.nextInt(10) + 1)
        ballot.setpCrossWitness2(randNum.nextInt(10) + 1)
        ballot.setpCrossWitness3(randNum.nextInt(10) + 1)
        ballot.setpClosing(randNum.nextInt(10) + 1)

        ballot.setdOpening(randNum.nextInt(10) + 1)
        ballot.setdDirectAttorney1(randNum.nextInt(10) + 1)
        ballot.setdDirectAttorney2(randNum.nextInt(10) + 1)
        ballot.setdDirectAttorney3(randNum.nextInt(10) + 1)
        ballot.setdCrossAttorney1(randNum.nextInt(10) + 1)
        ballot.setdCrossAttorney2(randNum.nextInt(10) + 1)
        ballot.setdCrossAttorney3(randNum.nextInt(10) + 1)
        ballot.setdDirectWitness1(randNum.nextInt(10) + 1)
        ballot.setdDirectWitness2(randNum.nextInt(10) + 1)
        ballot.setdDirectWitness3(randNum.nextInt(10) + 1)
        ballot.setdCrossWitness1(randNum.nextInt(10) + 1)
        ballot.setdCrossWitness2(randNum.nextInt(10) + 1)
        ballot.setdCrossWitness3(randNum.nextInt(10) + 1)
        ballot.setdClosing(randNum.nextInt(10) + 1)
        ballot.save()
    }

    int sumBallotP(Ballot ballot) {
        int total = 0

        total += ballot.getpOpening()
        total += ballot.getpDirectAttorney1()
        total += ballot.getpDirectAttorney2()
        total += ballot.getpDirectAttorney3()
        total += ballot.getpCrossAttorney1()
        total += ballot.getpCrossAttorney2()
        total += ballot.getpCrossAttorney3()
        total += ballot.getpDirectWitness1()
        total += ballot.getpDirectWitness2()
        total += ballot.getpDirectWitness3()
        total += ballot.getpCrossWitness1()
        total += ballot.getpCrossWitness2()
        total += ballot.getpCrossWitness3()
        total += ballot.getpClosing()

        return total
    }

    int sumBallotD(Ballot ballot) {
        int total = 0

        total += ballot.getdOpening()
        total += ballot.getdDirectAttorney1()
        total += ballot.getdDirectAttorney2()
        total += ballot.getdDirectAttorney3()
        total += ballot.getdCrossAttorney1()
        total += ballot.getdCrossAttorney2()
        total += ballot.getdCrossAttorney3()
        total += ballot.getdDirectWitness1()
        total += ballot.getdDirectWitness2()
        total += ballot.getdDirectWitness3()
        total += ballot.getdCrossWitness1()
        total += ballot.getdCrossWitness2()
        total += ballot.getdCrossWitness3()
        total += ballot.getdClosing()

        return total
    }

    int ballotPointDifferential(Ballot ballot) {
        // Positive means P wins
        return sumBallotP(ballot)- sumBallotD(ballot)
    }
}
