package estate_management

class PaymentRequest {
	User user
	String description
	String code
	double amount
	Date dueDate
	Date confirmationDate
	boolean isConfirmed
	boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [ paymentRequestDetails: PaymentRequestDetail ]

    static constraints = {
		dueDate(nullable : true)
		confirmationDate (nullable : true)
    }
}
