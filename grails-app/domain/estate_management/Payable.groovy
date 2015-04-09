package estate_management

import java.util.Date;

class Payable {
	ShiroUser 	user
	String		payableSource
	String 		payableSourceId
	Integer	 	payableSourceDetailId
	String 		code
	Date 		dueDate
	Double 		amount
	Double 		remainingAmount
	Double 		pendingClearanceAmount
	Boolean 	isCompleted
	Date 		completionDate
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
		completionDate (nullable : true)
		dueDate (nullable : true)
		user (nullable : true)
    }
}
