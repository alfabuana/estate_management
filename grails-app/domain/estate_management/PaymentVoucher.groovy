package estate_management

import java.util.Date;

class PaymentVoucher {
	ShiroUser	user
	CashBank	cashBank
	String		code
	Date		paymentDate
	Boolean		isGBCH
	Date		dueDate
	Boolean		isReconciled
	Date		reconciliationDate
	Double		totalAmount
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean		isConfirmed
	Date		confirmationDate
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasMany = [paymentVoucherDetails : PaymentVoucherDetail]

    static constraints = {
		dueDate(nullable:true)
		confirmationDate (nullable : true)
		reconciliationDate (nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
		code(nullable:true)
		isGBCH(nullable:true)
    }
}
