package estate_management

class HomeAssignment {
	Home home
	ShiroUser user
	Date assignDate
	Boolean isConfirmed
	Date confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		confirmationDate (nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
    }
}
