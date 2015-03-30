package estate_management

import java.util.Date;

class PaymentVoucherDetail {
	PaymentVoucher 	paymentVoucher
	Payable 		payable
	String 			code
	Double 			amount
	String 			description
	Boolean			isConfirmed
	Date 			confirmationDate
	Boolean 		isDeleted
	Date 			dateCreated // Predefined names by Grails will be filled automatically
	Date 			lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
		confirmationDate(nullable : true)
    }
}
