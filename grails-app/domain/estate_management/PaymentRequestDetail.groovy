package estate_management

class PaymentRequestDetail {
	PaymentRequest paymentRequest
	String code
	Double amount
	boolean isConfirmed
	Date confirmationDate
	
	static belongsTo = PaymentRequest

    static constraints = {
    }
}
