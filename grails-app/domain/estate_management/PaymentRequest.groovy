package estate_management

class PaymentRequest {
	Vendor	vendor
	Project	project
	ShiroUser user
	String description
	String code
	Double amount
	Date dueDate
	Date confirmationDate
	Date requestDate
	Boolean isConfirmed
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [ paymentRequestDetails: PaymentRequestDetail ]

    static constraints = {
		user(nullable:true)
		dueDate(nullable : true)
		confirmationDate (nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
		code(nullable:true)
    }
}
