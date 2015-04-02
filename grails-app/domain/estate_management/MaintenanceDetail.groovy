package estate_management

import java.util.Date;

class MaintenanceDetail {
	Maintenance	maintenance
	ShiroUser	username
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
    }
}
