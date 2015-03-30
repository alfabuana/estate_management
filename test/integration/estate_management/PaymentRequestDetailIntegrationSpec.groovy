package estate_management

import grails.test.spock.IntegrationSpec

class PaymentRequestDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def paymentRequestService
	def paymentRequestDetailService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create new payment Request Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		println shiroUser.id

		and: 'setting new payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]

		when : 'createObject is called'
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)

		then: 'check has errors'
		paymentRequestDetail.hasErrors() == false
		paymentRequestDetail.isDeleted == false
	}
	void "Test Create PaymentRequest Detail Validation payment Request Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:null,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:null,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]

		when:'createObject is called'
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)

		then:'check has errors'
		paymentRequestDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create PaymentRequest Detail Validation code Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:null,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]

		when:'createObject is called'
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)

		then:'check has errors'
		paymentRequestDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create PaymentRequest Detail Validation amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:null,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:null,
			confirmationDate:new Date (2015,3,27)
		]

		when:'createObject is called'
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)

		then:'check has errors'
		paymentRequestDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update New Home Detail Request"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)
		
		and:'setting data for Update'
		def paymentRequestDetail2 = [
			id: paymentRequestDetail.id,
			paymentRequest:paymentRequest,
			code:"updateCode",
			amount:2000,
			confirmationDate:new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequestDetail = paymentRequestDetailService.updateObject(paymentRequestDetail2)

		then:'check has errors'
		paymentRequestDetail.hasErrors() == false
		paymentRequestDetail.paymentRequest == paymentRequestDetail.paymentRequest
		paymentRequestDetail.code == paymentRequestDetail.code
		paymentRequestDetail.amount == paymentRequestDetail.amount
		paymentRequestDetail.confirmationDate == paymentRequestDetail.confirmationDate
	}
	void "Test Update Home Detail Request Validation payment Request not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)
		
		and:'setting data for Update'
		def paymentRequestDetail2 = [
			id: paymentRequestDetail.id,
			paymentRequest:null,
			code:"updateCode",
			amount:2000,
			confirmationDate:new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequestDetail = paymentRequestDetailService.updateObject(paymentRequestDetail2)

		then:'check has errors'
		paymentRequestDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Detail Request Validation code not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)
		
		and:'setting data for Update'
		def paymentRequestDetail2 = [
			id: paymentRequestDetail.id,
			paymentRequest:paymentRequest,
			code:"",
			amount:2000,
			confirmationDate:new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequestDetail = paymentRequestDetailService.updateObject(paymentRequestDetail2)

		then:'check has errors'
		paymentRequestDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update Home Detail Request Validation amount not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)
		
		and:'setting data for Update'
		def paymentRequestDetail2 = [
			id: paymentRequestDetail.id,
			paymentRequest:paymentRequest,
			code:"updateCode",
			amount:null,
			confirmationDate:new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequestDetail = paymentRequestDetailService.updateObject(paymentRequestDetail2)

		then:'check has errors'
		paymentRequestDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequestDetail.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Payment Request Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)

		when:'createObject is called'
		paymentRequestDetail = paymentRequestDetailService.softDeletedObject(paymentRequestDetail)
		
		then:'check has errors'
		paymentRequestDetail.hasErrors() == false
		paymentRequestDetail.isDeleted == true
	}
	void "Test Confirm Payment Request Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)

		when:'createObject is called'
		paymentRequestDetail = paymentRequestDetailService.confirmObject(paymentRequestDetail)
		
		then:'check has errors'
		paymentRequestDetail.hasErrors() == false
		paymentRequestDetail.isConfirmed == true
	}
	void "Test unConfirm Payment Request Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		and: 'setting new Payment Request Detail'
		def paymentRequestDetail = [
			paymentRequest:paymentRequest,
			code:"newCode",
			amount:1000,
			confirmationDate:new Date (2015,3,27)
		]
		paymentRequestDetail = paymentRequestDetailService.createObject(paymentRequestDetail)

		when:'createObject is called'
		paymentRequestDetail = paymentRequestDetailService.unConfirmObject(paymentRequestDetail)
		
		then:'check has errors'
		paymentRequestDetail.hasErrors() == false
		paymentRequestDetail.isConfirmed == false
	}
}
