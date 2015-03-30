package estate_management

import java.util.Date;

class ShiroUser {
    String username
    String passwordHash
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated
    
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }
}
