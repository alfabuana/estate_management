package estate_management

class ShiroRole {
    String name
//	ShiroUser createdBy
//	ShiroUser updatedBy

	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

    static hasMany = [ users: ShiroUser, permissions: String ]
    static belongsTo = ShiroUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
//		createdBy(nullable : true)
//		updatedBy(nullable : true)
    }
}
