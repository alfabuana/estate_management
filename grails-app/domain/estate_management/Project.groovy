package estate_management

import java.util.Date;

class Project {
	String	title
	String	description
	Double	amountAgree
	Double	amountDisagree
	Boolean	isDeleted
	Boolean isConfirmed
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasmany = [projectVotes : ProjectVote , projectDetails : ProjectDetail]

    static constraints = {
		confirmationDate(nullable:true)
    }
}
