package TabRunner

class Round {

    String roundName

    static hasMany = [pairings: Pairing]
}
