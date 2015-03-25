package estate_management

class Home {
	String name
	String address
	boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [ homeDetails: HomeDetail ]

    static constraints = {
    }
}
