package estate_management

class CashBankAdjusment {
	CashBank CashBank
	Date adjusmentDate
	double amount
	String code
	boolean isConfirmed
	Date confirmationDate
	boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		confirmationDate(nullable : true)
    }
}
