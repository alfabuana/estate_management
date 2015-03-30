package estate_management

import grails.test.spock.IntegrationSpec

class PaymentRequestIntegrationSpec extends IntegrationSpec {
	def userService
	def paymentRequestService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create New Payment Request"() {
		setup:'setting new User'
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

		when : 'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then: 'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isDeleted == false
	}
	void "Test Create PaymentRequest Validation username Not Null"(){
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

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}
	void "Test Create PaymentRequest Validation description Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"",
			code:"newCode",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}
	void "Test Create PaymentRequest Validation code Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser,
			description:"newDescription",
			code:"",
			amount:1000,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}
	void "Test Create PaymentRequest Validation amount Not Null"(){
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
			amount:null,
			dueDate:new Date (2015,3,26),
			confirmationDate:new Date (2015,3,27),
			requestDate: new Date (2015,3,28)
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}

	void "Test Create PaymentRequest Validation request Date Not Null"(){
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
			requestDate: null
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}
	void "Test Update New Payment Request"(){
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser,
			description:"updateDescription",
			code:"updateCode",
			amount:2000,
			dueDate:new Date (2015,3,27),
			confirmationDate:new Date (2015,3,28),
			requestDate: new Date(2015,3,29)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.username == paymentRequest.username
		paymentRequest.description == paymentRequest.description
		paymentRequest.code == paymentRequest.code
		paymentRequest.amount == paymentRequest.amount
		paymentRequest.dueDate == paymentRequest.dueDate
		paymentRequest.confirmationDate == paymentRequest.confirmationDate
		paymentRequest.requestDate == paymentRequest.requestDate
	}
	void "Test Update Payment Request Validation username Not Null"(){
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:null,
			description:"updateDescription",
			code:"updateCode",
			amount:2000,
			dueDate:new Date (2015,3,27),
			confirmationDate:new Date (2015,3,28),
			requestDate: new Date(2015,3,29)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Request Validation description Not Null"(){
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser,
			description:"",
			code:"updateCode",
			amount:2000,
			dueDate:new Date (2015,3,27),
			confirmationDate:new Date (2015,3,28),
			requestDate: new Date(2015,3,29)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Request Validation code Not Null"(){
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser,
			description:"updateDescription",
			code:"",
			amount:2000,
			dueDate:new Date (2015,3,27),
			confirmationDate:new Date (2015,3,28),
			requestDate: new Date(2015,3,29)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}

	void "Test Update Payment Request Validation amount Not Null"(){
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser,
			description:"updateDescription",
			code:"updateCode",
			amount:null,
			dueDate:new Date (2015,3,27),
			confirmationDate:new Date (2015,3,28),
			requestDate: new Date(2015,3,29)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Request Validation request Date Not Null"(){
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser,
			description:"updateDescription",
			code:"updateCode",
			amount:2000,
			dueDate:new Date (2015,3,27),
			confirmationDate:new Date (2015,3,28),
			requestDate:null
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Payment Request"() {
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		when:'createObject is called'
		paymentRequest = paymentRequestService.softDeletedObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isDeleted == true
	}
	void "Test Confirm Payment Request"() {
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		when:'createObject is called'
		paymentRequest = paymentRequestService.confirmObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isConfirmed == true
	}
	void "Test unConfirm Payment Request"() {
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
			requestDate: new Date(2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		when:'createObject is called'
		paymentRequest = paymentRequestService.unConfirmObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isConfirmed == false
	}


}
