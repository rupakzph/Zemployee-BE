package zemployee 

class Department{
    String name
    static hasMany = [teams: Team]

    static constraints = {
        name nullable: false
    }

}