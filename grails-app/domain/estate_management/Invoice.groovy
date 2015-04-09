package estate_management

import java.util.Date;

class Invoice {
	Home		home
	ShiroUser	user
	String		code
	Date		invoiceDate
	String		description
	Date		dueDate
	Double		totalAmount
	Boolean		isConfirmed
	Date		confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean		isCleared
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

	static hasMany = [invoiceDetails : InvoiceDetail]
	static belongsTo = Invoice
    static constraints = {
		user(nullable:true)
		home(nullable:true)
		dueDate(nullable:true)
		confirmationDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
		code(nullable:true)
    }
}
