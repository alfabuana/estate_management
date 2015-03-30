package estate_management

import java.util.Date;

class ReceiptVoucher {
	ShiroUser	username
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
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

	static hasmany = [receiptVoucherDetails : ReceiptVoucherDetail]
	
    static constraints = {
		confirmationDate(nullable : true)
		reconciliationDate(nullable : true)
    }
}
