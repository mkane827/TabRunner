package TabRunner

class Ballot {

    int pOpening
    int pDirectAttorney1
    int pDirectAttorney2
    int pDirectAttorney3
    int pCrossAttorney1
    int pCrossAttorney2
    int pCrossAttorney3
    int pDirectWitness1
    int pDirectWitness2
    int pDirectWitness3
    int pCrossWitness1
    int pCrossWitness2
    int pCrossWitness3
    int pClosing

    int dOpening
    int dDirectAttorney1
    int dDirectAttorney2
    int dDirectAttorney3
    int dCrossAttorney1
    int dCrossAttorney2
    int dCrossAttorney3
    int dDirectWitness1
    int dDirectWitness2
    int dDirectWitness3
    int dCrossWitness1
    int dCrossWitness2
    int dCrossWitness3
    int dClosing

    Competitor rank1Attorney
    Competitor rank2Attorney
    Competitor rank3Attorney
    Competitor rank4Attorney

    Competitor rank1Witness
    Competitor rank2Witness
    Competitor rank3Witness
    Competitor rank4Witness

    Judge judge

    static belongsTo = [pairing: Pairing]

    static constraints = {
        rank1Attorney(nullable: true)
        rank2Attorney(nullable: true)
        rank3Attorney(nullable: true)
        rank4Attorney(nullable: true)
        rank1Witness(nullable: true)
        rank2Witness(nullable: true)
        rank3Witness(nullable: true)
        rank4Witness(nullable: true)
    }

}
