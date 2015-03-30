package estate_management

class CashBank {
	String name
	String description
	Double amount
	Boolean isBank
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		description(nullable : true)
    }
}
