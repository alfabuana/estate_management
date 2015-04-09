package estate_management

import java.util.Date;

class ProjectDetail {
	Project	project
	String	attachmentUrl
	ShiroUser createdBy
	ShiroUser updatedBy
	Boolean	isDeleted
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
		createdBy(nullable : true)
		updatedBy(nullable : true)
    }
}
