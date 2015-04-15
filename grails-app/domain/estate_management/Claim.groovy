package estate_management

import java.util.Date;

class Claim {
	Permit permit
	String description
	Double amount
	Date claimDate
	Boolean isConfirmed
	Date confirmationDate
	ShiroUser 	createdBy
	ShiroUser 	updatedBy
	ShiroUser 	confirmedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date		lastUpdated // Predefined names by Grails will be filled automatically
	
	static belongsTo = Permit

    static constraints = {
		description(nullable : true)
		confirmationDate(nullable : true)
    }
}
