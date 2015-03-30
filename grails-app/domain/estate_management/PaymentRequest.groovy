package estate_management

class PaymentRequest {
	ShiroUser username
	String description
	String code
	Double amount
	Date dueDate
	Date confirmationDate
	Date requestDate
	Boolean isConfirmed
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [ paymentRequestDetails: PaymentRequestDetail ]

    static constraints = {
		dueDate(nullable : true)
		confirmationDate (nullable : true)
    }
}
