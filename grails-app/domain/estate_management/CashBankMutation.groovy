package estate_management

class CashBankMutation {
	CashBank sourceCashBank
	CashBank targetCashBank
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
