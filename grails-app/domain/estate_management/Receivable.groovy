package estate_management

class Receivable {
	ShiroUser	user
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
	ShiroUser createdBy
	ShiroUser updatedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

	static constraints = {
		completionDate (nullable : true)
		dueDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
	}
}
