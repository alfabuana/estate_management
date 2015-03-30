package estate_management

class Receivable {
	ShiroUser	username
	String 		receivableSource
	Integer 	receivableSourceId
	Integer 	receivableSourceDetailId
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
	}
}
