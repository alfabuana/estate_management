package estate_management

class HomeDetail {
	Home home
	User user
	boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = Home

    static constraints = {
    }
}
