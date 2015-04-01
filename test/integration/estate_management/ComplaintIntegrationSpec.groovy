package estate_management

import grails.test.spock.IntegrationSpec

class ComplaintIntegrationSpec extends IntegrationSpec {
	def userService
	def homeService
	def complaintService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Complaint"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		when : 'createObject is called'
		complaint = complaintService.createObject(complaint)

		then: 'check has errors'
		complaint.hasErrors() == false
		complaint.isDeleted == false
	}
	void "Test Create Complaint Validation Username Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:null,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage
	}
	void "Test Create Complaint Validation Home Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:home,
			home:null,
			description:"newDescription",
			title:"newTitle"
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage
	}
	void "Test Create Complaint Validation description Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"",
			title:"newTitle"
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage
	}
	void "Test Create Complaint Validation title Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:""
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage
	}
	void "Test Update New Complaint"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]
		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.username == complaint.username
		complaint.home == complaint.home
		complaint.description == complaint.description
		complaint.title == complaint.title
	}
	void "Test Update Complaint Validation username Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:null,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage

	}
	void "Test Update Complaint Validation home Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:null,
			description:"newDescription",
			title:"newTitle"
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage

	}
	void "Test Update Complaint Validation description Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:home,
			description:"",
			title:"newTitle"
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage

	}
	void "Test Update Complaint Validation title Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:""
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Complaint"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			home:home,
			description:"newDescription",
			title:"newTitle"
		]

		complaint= complaintService.createObject(complaint)

		when:'softDeleted is called'
		complaint = complaintService.softDeletedObject(complaint)
		
		then:'check has errors'
		complaint.hasErrors() == false
		complaint.isDeleted == true
	}
}

