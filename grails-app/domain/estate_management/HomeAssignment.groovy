package estate_management

class HomeAssignment {
	Home home
	ShiroUser username
	Date assignDate
	Boolean isConfirmed
	Date confirmationDate
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		confirmationDate (nullable : true)
    }
}
