package estate_management

import grails.test.spock.IntegrationSpec

class HomeAssignmentIntegrationSpec extends IntegrationSpec {
	def homeService
	def userService
	def homeAssignmentService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create New Home Assignment"() {
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]

		when : 'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then: 'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isDeleted == false

	}
	void "Test Create Home Assignment Validation Home Not Null"(){
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:null,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]

		when:'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Create Home Assignment Validation User Not Null"(){
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:null,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]

		when:'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Create Home Assignment Validation Assign Date Not Null"(){
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:null,
			confirmationDate:new Date (2015,3,27)
		]

		when:'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Assignment"(){
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,28),
			confirmationDate:new Date (2015,3,29)
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.home == homeAssignment.home
		homeAssignment.username == homeAssignment.username
		homeAssignment.assignDate == homeAssignment.assignDate
		homeAssignment.confirmationDate == homeAssignment.confirmationDate
	}
	void "Test Update Home Assignment Validation Home Not Null"(){
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:null,
			username:shiroUser,
			assignDate:new Date (2015,3,28),
			confirmationDate:new Date (2015,3,29)
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Assignment Validation User Not Null"(){
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:home,
			username:null,
			assignDate:new Date (2015,3,28),
			confirmationDate:new Date (2015,3,29)
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Assignment Validation Assign Date Not Null"(){
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:home,
			username:shiroUser,
			assignDate:null,
			confirmationDate:new Date (2015,3,29)
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Home Assignment"() {
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		when:'softDeleteObject is called'
		homeAssignment = homeAssignmentService.softDeletedObject(homeAssignment)
		
		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isDeleted == true
	}
	void "Test Confirm Home Assignment"() {
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		when:'confirmObject is called'
		homeAssignment = homeAssignmentService.confirmObject(homeAssignment)
		
		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isConfirmed == true
	}
	void "Test unConfirm Home Assignment"() {
		setup: 'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		println home.id

		and: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			username:shiroUser,
			assignDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27)
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		when:'confirmObject is called'
		homeAssignment = homeAssignmentService.unConfirmObject(homeAssignment)
		
		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isConfirmed == false
	}



}
