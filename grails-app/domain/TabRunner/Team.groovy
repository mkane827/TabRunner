package TabRunner

class Team {

    int teamNumber
    String schoolName
    String coachName

    static hasMany = [competitors:Competitor]

    static belongsTo = [Tournament]

}
