package estate_management

class HomeDetail {
	Home home
	ShiroUser username
	Date lastAssignDate
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = Home

    static constraints = {
    }
}
