package estate_management

class CashBank {
	String name
	String description
	double amount
	boolean isBank
	boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		description(nullable : true)
    }
}
