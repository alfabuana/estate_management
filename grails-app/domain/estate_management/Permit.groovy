package estate_management

import java.util.Date;

class Permit {
	String constructionType
	String code
	Vendor vendor
	Home home
	String description
	String numberIMB
	Integer estimateWorkDays
	Double amountDeposit
	Date startDate
	Boolean isConfirmed
	Date confirmationDate
	Boolean isCleared
	Date clearanceDate
	ShiroUser 	createdBy
	ShiroUser 	updatedBy
	ShiroUser 	confirmedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date		lastUpdated // Predefined names by Grails will be filled automatically

	static hasMany = [claims : Claim]
    static constraints = {
		description(nullable : true)
		numberIMB(nullable : true)
		clearanceDate(nullable : true)
		confirmationDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
		code(nullable:true)
    }
}
