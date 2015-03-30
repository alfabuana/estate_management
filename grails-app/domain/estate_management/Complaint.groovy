package estate_management

import java.util.Date;

class Complaint {
	ShiroUser	username
	String		description
	String		title
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasmany =[complaintDetails : ComplaintDetail]

    static constraints = {
    }
}
