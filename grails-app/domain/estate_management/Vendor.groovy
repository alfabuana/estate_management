package estate_management

class Vendor {
	String name
	String description
	String telephone
	String fax
	String email
	boolean isDeleted
	Date dateCreate
	Date lastUpdated

    static constraints = {
		name (nullable : true)
		description (nullable : true)
		telephone (nullable : true)
		fax (nullable : true)
		email (nullable : true)
    }
}
