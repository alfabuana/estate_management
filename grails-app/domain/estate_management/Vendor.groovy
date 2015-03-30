package estate_management

class Vendor {
	String name
	String description
	String telephone
	String fax
	String email
	
	Boolean isDeleted
	Date dateCreated
	Date lastUpdated

    static constraints = {
		description (nullable : true)
		telephone (nullable : true)
		fax (nullable : true)
		email (nullable : true)
    }
}
