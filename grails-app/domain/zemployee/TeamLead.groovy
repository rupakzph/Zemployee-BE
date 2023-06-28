package zemployee 
class TeamLead extends Employee{
    static hasMany = [employee: Employee]
    static belongsTo = [employee:Employee]
    static constraints = {
    }

}
