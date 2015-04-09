package estate_management

class CashBank {
	String name
	String description
	Double amount
	Boolean isBank
	ShiroUser createdBy
	ShiroUser updatedBy
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		description(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
    }
}
