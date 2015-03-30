package estate_management

class CashBankAdjustment {
	CashBank cashBank
	Date adjustmentDate
	Double amount
	String code
	Boolean isConfirmed
	Date confirmationDate
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		confirmationDate(nullable : true)
    }
}
