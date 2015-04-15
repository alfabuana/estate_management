package estate_management

import java.util.Date;

class ParkingRegistration {
	Home		home
	String		carNumber
	Boolean		isConfirmed
	Date		confirmationDate
	ShiroUser 	createdBy
	ShiroUser 	updatedBy
	ShiroUser 	confirmedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date		lastUpdated // Predefined names by Grails will be filled automatically

	static belongsTo = Home
    static constraints = {
		confirmationDate(nullable : true)
		createdBy(nullable : true)
		updatedBy(nullable : true)
		confirmedBy(nullable : true)
    }
}
