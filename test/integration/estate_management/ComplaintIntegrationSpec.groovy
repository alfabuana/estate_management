package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared
class ComplaintIntegrationSpec extends IntegrationSpec {
	def userService
	def homeService
	def complaintService

	@Shared
	def shiroUser
	def home

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)

		home = [
			name:"Home1",
			address:"12345",
			createdBy:shiroUser
		]
		home = homeService.createObject(home)
	}

	def cleanup() {
	}

	void "Test Create New Complaint"() {
		setup:'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.isDeleted == false
		complaint.id != null
	}

	void "Test Create Complaint Validation Username Not Null"(){
		setup: 'setting new Complaint'
		def complaint = [
			username:null,
			home:home,
			title:"newTitle"
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage
	}
	void "Test Create Complaint Validation Home Not Null"(){
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:null,
			title:"newTitle"
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage
	}
	void "Test Create Complaint Validation title Not Null"(){
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:""
		]

		when:'createObject is called'
		complaint = complaintService.createObject(complaint)
		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage
	}
	void "Test Update New Complaint"(){
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:home,
			title:"updateTitle",
			updatedBy:shiroUser.username
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.user == complaint.user
		complaint.home == complaint.home
		complaint.title == complaint.title
	}
	
	void "Test Update New Complaint Validation Is Confirmed"(){
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]

		complaint = complaintService.confirmObject(confirm)
		
		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:home,
			title:"updateTitle",
			updatedBy:shiroUser.username
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test Update Complaint Validation home Not Null"(){
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]

		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:null,
			title:"updateTitle"
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage

	}
	void "Test Update Complaint Validation title Not Null"(){
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]

		complaint= complaintService.createObject(complaint)

		and:'setting data for Update'
		def complaint2 = [
			id: complaint.id,
			username:shiroUser,
			home:home,
			title:""
		]

		when:'updateObject is called'
		complaint = complaintService.updateObject(complaint2)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Complaint"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]

		complaint= complaintService.createObject(complaint)

		when:'softDeleted is called'
		complaint = complaintService.softDeletedObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.isDeleted == true
	}
	void "Test SoftDelete Complaint Validation Is Confirmed"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]

		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		complaint = complaintService.confirmObject(confirm)

		when:'softDeleted is called'
		complaint = complaintService.softDeletedObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Complaint Validation Is Deleted"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]

		complaint= complaintService.createObject(complaint)
		
		complaint = complaintService.softDeletedObject(complaint)

		when:'softDeleted is called'
		complaint = complaintService.softDeletedObject(complaint)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test Confirm Complaint"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]

		when:'confirm is called'
		complaint = complaintService.confirmObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.isConfirmed == true
	}
	
	void "Test Confirm Complaint Validation Is Confirmed"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		complaint = complaintService.confirmObject(confirm)
		when:'confirm is called'
		complaint = complaintService.confirmObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test UnConfirm Complaint"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		complaint = complaintService.confirmObject(confirm)
		when:'confirm is called'
		complaint = complaintService.unConfirmObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.isConfirmed == false
	}
	void "Test UnConfirm Complaint Validation Is Cleared"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		complaint = complaintService.confirmObject(confirm)
		complaint = complaintService.clearObject(complaint)
		when:'confirm is called'
		complaint = complaintService.unConfirmObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test UnConfirm Complaint Validation Is Confirmed"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		when:'confirm is called'
		complaint = complaintService.unConfirmObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test Clear Complaint"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		complaint = complaintService.confirmObject(confirm)
		when:'confirm is called'
		complaint = complaintService.clearObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.isCleared == true
	}
	void "Test Clear Complaint Validation Is Not Confirmed"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		when:'confirm is called'
		complaint = complaintService.clearObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test UnClear Complaint"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		complaint = complaintService.confirmObject(confirm)
		complaint = complaintService.clearObject(confirm)
		when:'confirm is called'
		complaint = complaintService.unClearObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == false
		complaint.isCleared == false
	}
	void "Test UnClear Complaint Validation Is Confirmed"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		when:'confirm is called'
		complaint = complaintService.unClearObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
	void "Test UnClear Complaint Validation Is Cleared"() {
		setup: 'setting new Complaint'
		def complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle",
		]
		complaint= complaintService.createObject(complaint)
		
		def confirm = [
			id:complaint.id,
			username:shiroUser
			]
		complaint = complaintService.confirmObject(confirm)
		when:'confirm is called'
		complaint = complaintService.unClearObject(confirm)

		then:'check has errors'
		complaint.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaint.errors.getAllErrors().defaultMessage
	}
}

