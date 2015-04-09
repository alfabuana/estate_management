package estate_management

import java.util.Date;

class ReceiptVoucherDetail {
	ReceiptVoucher	receiptVoucher
	Receivable		receivable
	String			code
	Double			amount
	String			description
	Boolean			isConfirmed
	Date			confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean 		isDeleted
	Date 			dateCreated // Predefined names by Grails will be filled automatically
	Date 			lastUpdated // Predefined names by Grails will be filled automatically
	

    static constraints = {
		confirmationDate(nullable: true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
		code(nullable:true)
    }
}
