package tabrunner

import TabRunner.Round

class TournamentService {

    static transactional = false

    BallotService ballotService

    def gatherTeamDataFromPreviousRounds(Set<Round> rounds, HashMap<Integer, Object> teamData) {
        for (round in rounds) {
            for(pairing in round.getPairings()) {
                def teamNumberP = pairing.getTeamP().getTeamNumber()
                def teamNumberD = pairing.getTeamD().getTeamNumber()
                teamData.get(teamNumberP).teamConflicts.add(teamNumberD)
                teamData.get(teamNumberD).teamConflicts.add(teamNumberP)
                for(ballot in pairing.getBallots()) {
                    def judgeName = ballot.getJudge().getJudgeName()
                    teamData.get(teamNumberP).judgeConflicts.add(judgeName)
                    teamData.get(teamNumberD).judgeConflicts.add(judgeName)
                    def teamPScore = ballotService.sumBallotP(ballot)
                    def teamDScore = ballotService.sumBallotD(ballot)

                    if(teamPScore == teamDScore) {
                        teamData.get(teamNumberP).wins += 0.5
                        teamData.get(teamNumberD).wins += 0.5
                    }
                    else if (teamPScore > teamDScore) {
                        teamData.get(teamNumberP).wins ++
                    }
                    else {
                        teamData.get(teamNumberD).wins ++
                    }
                }
            }
        }
    }
}
