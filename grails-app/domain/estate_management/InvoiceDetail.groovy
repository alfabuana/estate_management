package estate_management

import java.util.Date;

class InvoiceDetail {
	Invoice	invoice
	String	code
	Double	amount
	Boolean	isConfirmed
	Date	confirmationDate
	Boolean isDeleted
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
		confirmationDate(nullable : true)
    }
}
