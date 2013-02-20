package tabrunner

import TabRunner.Round
import TabRunner.Judge

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

                    teamData.get(teamNumberP).sideConstraint ++
                    teamData.get(teamNumberD).sideConstraint --

                    def teamPScore = ballotService.sumBallotP(ballot)
                    def teamDScore = ballotService.sumBallotD(ballot)

                    if(teamPScore == teamDScore) {
                        teamData.get(teamNumberP).wins += 0.5
                        teamData.get(teamNumberD).wins += 0.5
                    }
                    else if (teamPScore > teamDScore) {
                        teamData.get(teamNumberP).wins ++
                        def difference = teamPScore - teamDScore
                        teamData.get(teamNumberP).pointDifferential += (difference)
                        teamData.get(teamNumberD).pointDifferential -= (difference)
                    }
                    else {
                        teamData.get(teamNumberD).wins ++
                        def difference = teamDScore - teamPScore
                        teamData.get(teamNumberD).pointDifferential += (difference)
                        teamData.get(teamNumberP).pointDifferential -= (difference)
                    }
                }
            }
        }
    }

    def pairTeams(Object[] unpaired, HashMap<Integer, Object> teamData) {
        if(unpaired.length <= 0) {
            return []
        }
        int teamPIndex = 0
        int teamDIndex = 0

        // Find the top ranked P team
        while(teamData.get(unpaired[teamPIndex]).sideConstraint > 0) {
            teamPIndex++;
        }
        def teamPNumber = unpaired[teamPIndex]
        def teamPData = teamData.get(teamPNumber)
        while (teamDIndex < unpaired.length) {
            def teamDNumber = unpaired[teamDIndex]

            // Find the top ranked D team that does not conflict
            if(!teamPData.teamConflicts.contains(teamDNumber) && // not constrained against each other
                    teamPIndex != teamDIndex && // not the same team
                    teamData.get(teamDNumber).sideConstraint >= 0 // Side constrained to defense or not at all
            ) {
                def pairs = pairTeams(unpaired.minus([teamPNumber, teamDNumber]), teamData);
                if(pairs != null) {
                    def pairsList = pairs.toList()
                    pairsList.add([
                        teamP: teamPNumber,
                        teamD: teamDNumber,
                        judge1: null,
                        judge2: null
                    ])
                    return pairsList.toArray()
                }
            }
            teamDIndex++
        }
        return null
    }

    def assignJudgesToPairings(Judge[] unassigned, Object[] pairings, HashMap<Integer, Object> teamData) {
        def allMatched = true;
        for (pairing in pairings) {
            if(pairing.judge1 == null || pairing.judge2 == null) {
                allMatched = false;
                break;
            }
        }

        if(unassigned.length <= 0 || allMatched) {
            return true
        }

        def judgeName
        def teamPJudgeConflicts
        def teamDJudgeConflicts
        def assignmentWorked

        def judgeIndex = 0
        while(judgeIndex < unassigned.length) {
            judgeName = unassigned[judgeIndex].judgeName
            for(pairing in pairings) {
                teamPJudgeConflicts = teamData.get(pairing.teamP).judgeConflicts
                teamDJudgeConflicts = teamData.get(pairing.teamD).judgeConflicts
                if(!teamPJudgeConflicts.contains(judgeName) &&
                        !teamDJudgeConflicts.contains(judgeName) &&
                        (pairing.judge1 == null || pairing.judge2 == null)
                ) {
                    if(pairing.judge1 == null) {
                        pairing.judge1 = unassigned[judgeIndex]
                        Judge[] testUnassigned = unassigned.minus([unassigned[judgeIndex]])
                        assignmentWorked = assignJudgesToPairings(testUnassigned, pairings, teamData)
                        if(assignmentWorked) {
                            return true
                        }
                        else {
                            pairing.judge1 = null
                        }
                    }
                    else {
                        pairing.judge2 = unassigned[judgeIndex]
                        Judge[] testUnassigned = unassigned.minus([unassigned[judgeIndex]])
                        assignmentWorked = assignJudgesToPairings(testUnassigned, pairings, teamData)
                        if(assignmentWorked) {
                            return true
                        }
                        else {
                            pairing.judge2 = null
                        }
                    }
                }
            }
            judgeIndex++
        }
        return false
    }
}