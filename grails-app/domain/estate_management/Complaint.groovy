package estate_management

import java.util.Date;

class Complaint {
	ShiroUser	user
	Home		home
	String		description
	String		title
	Boolean		isConfirmed
	Date		confirmationDate
	Boolean		isCleared
	Date		clearDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasmany =[complaintDetails : ComplaintDetail]

    static constraints = {
		confirmationDate(nullable:true)
		clearDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
    }
}
