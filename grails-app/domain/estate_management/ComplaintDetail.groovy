package estate_management

import java.util.Date;

class ComplaintDetail {
	Complaint	complaint
	String		attachmentUrl
	ShiroUser createdBy
	ShiroUser updatedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
		createdBy(nullable : true)
		updatedBy(nullable : true)
		complaint(nullable:true)
    }
}
