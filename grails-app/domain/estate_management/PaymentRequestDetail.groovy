package estate_management

import java.util.Date;

class PaymentRequestDetail {
	PaymentRequest paymentRequest
	String code
	String description
	Double amount
	Boolean isConfirmed
	Date confirmationDate
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = PaymentRequest

    static constraints = {
		confirmationDate(nullable : true)
    }
}
