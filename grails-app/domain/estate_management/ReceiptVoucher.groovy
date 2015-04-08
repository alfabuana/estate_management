package estate_management

import java.util.Date;

class ReceiptVoucher {
	ShiroUser	user
	CashBank	cashBank
	String		code
	Date		receiptDate
	Boolean		isGBCH
	Date		dueDate
	Boolean		isReconciled
	Date		reconciliationDate
	Double		totalAmount
	Boolean		isConfirmed
	Date		confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

	static hasMany = [receiptVoucherDetails : ReceiptVoucherDetail]
	
    static constraints = {
		dueDate(nullable:true)
		confirmationDate(nullable : true)
		reconciliationDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
    }
}
