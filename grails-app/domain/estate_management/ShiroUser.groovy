package estate_management

class ShiroUser {
    String username
    String passwordHash
    
    static hasMany = [ roles: ShiroRole, permissions: String ]

	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

	
    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }
}
