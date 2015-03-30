package estate_management

import java.util.Date;

class ShiroRole {
    String name
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static hasMany = [ users: ShiroUser, permissions: String ]
    static belongsTo = ShiroUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
}
