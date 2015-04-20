package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class HomeAssignmentIntegrationSpec extends IntegrationSpec {
	def homeService
	def userService
	def homeAssignmentService
	
	@Shared
	def shiroUser
	def home

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)

		home = [
			name:"newName",
			address:"newAddress",
			createBy:shiroUser
			]
		home = homeService.createObject(home)
	}

	def cleanup() {
	}

	void "Test Create New Home Assignment"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
			createdBy:shiroUser
		]

		when : 'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then: 'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isDeleted == false

	}
	void "Test Create Home Assignment Validation Home Not Null"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:null,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
			createdBy:shiroUser
		]

		when:'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Create Home Assignment Validation User Not Null"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:null,
			assignDate:new Date (2015,3,26),
			createdBy:shiroUser
		]

		when:'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Create Home Assignment Validation Assign Date Not Null"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:null,
			createdBy:shiroUser
		]

		when:'createObject is called'
		homeAssignment = homeAssignmentService.createObject(homeAssignment)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Assignment"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
			createdBy:shiroUser
		]

		homeAssignment = homeAssignmentService.createObject(homeAssignment)
		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:home,
			username:shiroUser.username,
			assignDate:new Date (2015,3,26),
			updatedBy:shiroUser
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.home == homeAssignment.home
		homeAssignment.user == homeAssignment.user
		homeAssignment.assignDate == homeAssignment.assignDate
	}
	void "Test Update Home Assignment Validation Is Confirmed"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)
		
		def confirm = [
			id:homeAssignment.id,
			username:shiroUser
			]
		homeAssignment = homeAssignmentService.confirmObject(confirm)
		
		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:null,
			username:shiroUser.username,
			assignDate:new Date (2015,3,28),
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getAllErrors().defaultMessage
	}
	void "Test Update Home Assignment Validation Home Not Null"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:null,
			username:shiroUser.username,
			assignDate:new Date (2015,3,28),
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Assignment Validation User Not Null"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:home,
			username:null,
			assignDate:new Date (2015,3,28),
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Assignment Validation Assign Date Not Null"(){
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		and:'setting data for Update'
		def homeAssignment2 = [
			id: homeAssignment.id,
			home:home,
			username:shiroUser.username,
			assignDate:null,
		]

		when:'updateObject is called'
		homeAssignment = homeAssignmentService.updateObject(homeAssignment2)

		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Home Assignment"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		when:'softDeleteObject is called'
		homeAssignment = homeAssignmentService.softDeletedObject(homeAssignment)
		
		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isDeleted == true
	}
	void "Test SoftDelete Home Assignment Validation Is Confirmed"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)
		def confirm = [
			id:homeAssignment.id,
			username:shiroUser
			]
		homeAssignment = homeAssignmentService.confirmObject(confirm)

		when:'softDeleteObject is called'
		homeAssignment = homeAssignmentService.softDeletedObject(homeAssignment)
		
		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Home Assignment Validation Is Deleted"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)
		homeAssignment = homeAssignmentService.softDeletedObject(homeAssignment)

		when:'softDeleteObject is called'
		homeAssignment = homeAssignmentService.softDeletedObject(homeAssignment)
		
		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getAllErrors().defaultMessage
	}
	void "Test Confirm Home Assignment"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		def confirm = [
			id:homeAssignment.id,
			username:shiroUser
			]
		
		when:'confirmObject is called'
		homeAssignment = homeAssignmentService.confirmObject(confirm)
		def homeDetail =[ HomeDetail.findAll{
					home == homeAssignment.home &&
					lastAssignDate == homeAssignment.assignDate &&
					user == homeAssignment.user
		}]
		
		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isConfirmed == true
		homeDetail.size() > 0
	}
	void "Test Confirm Home Assignment Validation Is Confirmed"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		def confirm = [
			id:homeAssignment.id,
			username:shiroUser
			]
		homeAssignment = homeAssignmentService.confirmObject(confirm)
		def homeDetail =[ HomeDetail.findAll{
					home == homeAssignment.home &&
					lastAssignDate == homeAssignment.assignDate &&
					user == homeAssignment.user
		}]
		when:'confirmObject is called'
		homeAssignment = homeAssignmentService.confirmObject(confirm)
		def homeDetail2 =[ HomeDetail.findAll{
					home == homeAssignment.home &&
					lastAssignDate == homeAssignment.assignDate &&
					user == homeAssignment.user
		}]
		
		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm Home Assignment"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)
		def confirm = [
			id:homeAssignment.id,
			username:shiroUser
			]
		homeAssignment = homeAssignmentService.confirmObject(confirm)
		when:'unconfirmObject is called'
		homeAssignment = homeAssignmentService.unConfirmObject(homeAssignment)
		def homeDetail = HomeDetail.findAll{
			home == homeAssignment.home &&
					lastAssignDate == homeAssignment.assignDate &&
					user == homeAssignment.user &&
					isDeleted == true
		}
		
		then:'check has errors'
		homeAssignment.hasErrors() == false
		homeAssignment.isConfirmed == false
	}
	void "Test unConfirm Home Assignment Validation Is Not Confirmed"() {
		setup: 'setting new Home Assignment'
		def homeAssignment = [
			home:home,
			user:shiroUser,
			assignDate:new Date (2015,3,26),
		]
		homeAssignment= homeAssignmentService.createObject(homeAssignment)

		when:'unconfirmObject is called'
		def confirm = [
			id:homeAssignment.id,
			username:shiroUser
			]
		homeAssignment = homeAssignmentService.unConfirmObject(homeAssignment)
		def homeDetail = HomeDetail.findAll{
			home == homeAssignment.home &&
					lastAssignDate == homeAssignment.assignDate &&
					user == homeAssignment.user &&
					isDeleted == true
		}
		
		then:'check has errors'
		homeAssignment.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeAssignment.errors.getAllErrors().defaultMessage
	}


}
