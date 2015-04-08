package estate_management

class CashBankMutation {
	CashBank sourceCashBank
	CashBank targetCashBank
	Double amount
	String code
	Boolean isConfirmed
	Date confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		confirmationDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
    }
}
