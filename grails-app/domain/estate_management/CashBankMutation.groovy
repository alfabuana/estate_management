package estate_management

class CashBankMutation {
	CashBank sourceCashBank
	CashBank targetCashBank
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
