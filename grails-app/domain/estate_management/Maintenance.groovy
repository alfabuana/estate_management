package estate_management

import java.util.Date;

class Maintenance {
	String	description
	Double	amount
	String	code
	Boolean isDeleted
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
    }
}
