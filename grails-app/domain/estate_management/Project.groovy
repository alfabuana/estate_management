package estate_management

import java.util.Date;

class Project {
	String	title
	String	description
	String code
	Double	amountAgree
	Double	amountDisagree
	Boolean	isDeleted
	Boolean isConfirmed
	Date	confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean	isFinished
	Date	finishDate
	Complaint	complaint
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	
	static hasMany = [projectVotes : ProjectVote , projectDetails : ProjectDetail]

    static constraints = {
		confirmationDate(nullable:true)
		finishDate(nullable:true)
		complaint(nullable:true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
		code(nullable:true)
    }
}
