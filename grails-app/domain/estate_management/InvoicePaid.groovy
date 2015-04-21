package estate_management

import java.util.Date;

class InvoicePaid {
	ShiroUser	user
	Invoice		invoice
	String		description
	Date		paidDate
	Boolean		isConfirmed
	Date		confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasmany = [invoicePaidDetails : InvoicePaidDetail]

    static constraints = {
		confirmationDate(nullable:true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
		description(nullable:true)
    }
}
