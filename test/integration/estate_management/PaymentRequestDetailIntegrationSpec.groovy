package estate_management

import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class PaymentRequestDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def paymentRequestService
	def paymentRequestDetailService
	def vendorService
	def projectService
	
	@Shared
	def shiroUser
	def vendor
	def project
	def paymentRequest

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		vendor = [
			name:"vendor1"]
		vendor = vendorService.createObject(vendor)
		
		project = [
			title:"project1"]
		project = projectService.createObject(project)
		
		paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
	}

	def cleanup() {
	}

	void "Test Create new payment Request Detail"() {
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		
		when:'create is called'
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)

		then: 'check has errors'
		paymentRequestDetails.hasErrors() == false
		paymentRequestDetails.isDeleted == false
	}
	
	void "Test Create new payment Request Detail Validation Is Confirmed"() {
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)
		
		def paymentRequestDetails2 = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		
		when:'create is called'
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails2)

		then: 'check has errors'
		paymentRequestDetails.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetails.errors.getAllErrors().defaultMessage
	} 
	void "Test Create PaymentRequest Detail Validation amount Not Null"(){
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:null]
		
		when:'create is called'
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)

		then:'check has errors'
		paymentRequestDetails.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetails.errors.getFieldError().defaultMessage
	}
	void "Test Update New Home Detail Request"(){
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		and:'setting data for Update'
		def paymentRequestDetail2 = [
			id: paymentRequestDetails.id,
			paymentRequestId:paymentRequest.id,
			amount:"60000"
		]

		when:'updateObject is called'
		paymentRequestDetails = paymentRequestDetailService.updateObject(paymentRequestDetail2)

		then:'check has errors'
		paymentRequestDetails.hasErrors() == false
		paymentRequestDetails.paymentRequest == paymentRequestDetails.paymentRequest
		paymentRequestDetails.amount == paymentRequestDetails.amount
	}
	void "Test Update Home Detail Request Validation is confirmed"(){
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)
		
		def paymentRequestDetails2 = [
			id:paymentRequestDetails.id,
			paymentRequestId:paymentRequest.id,
			amount:"50000"]
		
		when:'create is called'
		paymentRequestDetails = paymentRequestDetailService.updateObject(paymentRequestDetails2)

		then: 'check has errors'
		paymentRequestDetails.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetails.errors.getAllErrors().defaultMessage
	}
	void "Test Update Home Detail Request Validation amount not Null"(){
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		and:'setting data for Update'
		def paymentRequestDetail2 = [
			id: paymentRequestDetails.id,
			paymentRequestId:paymentRequest.id,
			amount:null
		]

		when:'updateObject is called'
		paymentRequestDetails = paymentRequestDetailService.updateObject(paymentRequestDetail2)

		then:'check has errors'
		paymentRequestDetails.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetails.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Payment Request Detail"() {
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)

		when:'createObject is called'
		paymentRequestDetails = paymentRequestDetailService.softDeletedObject(paymentRequestDetails)
		
		then:'check has errors'
		paymentRequestDetails.hasErrors() == false
		paymentRequestDetails.isDeleted == true
	}
	
	void "Test softdelete Home Detail Request Validation is confirmed"(){
		setup: 'setting new Payment Request Detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)
		
		
		when:'create is called'
		paymentRequestDetails = paymentRequestDetailService.softDeletedObject(paymentRequestDetails)

		then: 'check has errors'
		paymentRequestDetails.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetails.errors.getAllErrors().defaultMessage
	}

	void "Test Confirm Payment Request Detail"() {
		setup: 'setting new User'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		def confirm = [
			id:paymentRequestDetails.id,
			username:shiroUser.username
			]
		when:'createObject is called'
		paymentRequestDetails = paymentRequestDetailService.confirmObject(confirm)
		
		then:'check has errors'
		paymentRequestDetails.hasErrors() == false
		paymentRequestDetails.isConfirmed == true
	}
	void "Test unConfirm Payment Request Detail"() {
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		def confirm = [
			id:paymentRequestDetails.id,
			username:shiroUser.username
			]
		paymentRequestDetails = paymentRequestDetailService.confirmObject(confirm)

		when:'createObject is called'
		paymentRequestDetails = paymentRequestDetailService.unConfirmObject(paymentRequestDetails)
		
		then:'check has errors'
		paymentRequestDetails.hasErrors() == false
		paymentRequestDetails.isConfirmed == false
	}
}
