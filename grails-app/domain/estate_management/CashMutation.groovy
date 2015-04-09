package estate_management

import java.util.Date;

class CashMutation {
	CashBank	cashBank
	String		status
	String		sourceDocumentType
	String		sourceDocumentCode
	Integer		sourceDocumentId
	Double		amount
	Date		mutationDate
//	ShiroUser createdBy
//	ShiroUser updatedBy
	Boolean 	isDeleted
	Date 		dateCreated // Predefined names by Grails will be filled automatically
	Date 		lastUpdated // Predefined names by Grails will be filled automatically

    static constraints = {
//		createdBy(nullable : true)
//		updatedBy(nullable : true)
    }
}
