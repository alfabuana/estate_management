package estate_management

import java.util.Date;

class InvoicePaid {
	ShiroUser	username
	String		description
	Date		paidDate
	Boolean		isConfirmed
	Date		confirmationDate
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasmany = [invoicePaidDetails : InvoicePaidDetail]

    static constraints = {
		confirmationDate(nullable:true)
    }
}
