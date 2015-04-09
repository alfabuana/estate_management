package estate_management

class Home {
	String name
	String address
	ShiroUser createdBy
	ShiroUser updatedBy
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [ homeDetails: HomeDetail ]

    static constraints = {
		createdBy(nullable : true)
		updatedBy(nullable : true)
    }
}
