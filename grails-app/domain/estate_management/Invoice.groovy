package estate_management

import java.util.Date;

class Invoice {
	ShiroUser	username
	String		code
	Date		invoiceDate
	String		description
	Date		dueDate
	Double		totalAmount
	Boolean		isConfirmed
	Date		confirmationDate
	Boolean		isCleared
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

	static hasmany = [invoiceDetails : InvoiceDetail]
	static belongsTo = Invoice
    static constraints = {
		confirmationDate(nullable : true)
    }
}
