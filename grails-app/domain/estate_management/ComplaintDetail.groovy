package estate_management

import java.util.Date;

class ComplaintDetail {
	Complaint	complaint
	String		attachmentUrl
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
    }
}
