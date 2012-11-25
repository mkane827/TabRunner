package TabRunner

class Pairing {

    Team teamP
    Team teamD

    static hasMany = [ballots:Ballot]

    static belongsTo = [Round]

//    static mapping = {
//        teamP fetch: 'join'
//        teamD fetch: 'join'
//        ballots fetch: 'join'
//    }

}
