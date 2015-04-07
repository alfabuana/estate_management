package estate_management

import java.util.Date;

class Complaint {
	ShiroUser	username
	Home		home
	String		description
	String		title
	Boolean		isConfirmed
	Date		confirmationDate
	Boolean		isCleared
	Date		clearDate
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasmany =[complaintDetails : ComplaintDetail]

    static constraints = {
		confirmationDate(nullable:true)
		clearDate(nullable : true)
    }
}
