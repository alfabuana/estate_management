package estate_management

import java.util.Date;

class InvoicePaidDetail {
	InvoicePaid invoicePaid
	String 		attachmentUrl
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
    }
}
