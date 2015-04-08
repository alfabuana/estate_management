package estate_management

import java.util.Date;

class Maintenance {
	String	description
	Double	amount
	String	code
	Boolean isConfirmed
	Date	confirmationDate
	ShiroUser createdBy
	ShiroUser updatedBy
	ShiroUser confirmedBy
	Boolean isDeleted
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically

//	static hasMany = [maintenanceDetails : MaintenanceDetail]
    static constraints = {
		confirmationDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
    }
}
