package estate_management

import grails.test.spock.IntegrationSpec

class InvoicePaidIntegrationSpec extends IntegrationSpec {
	def userService
	def invoicePaidService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Invoice Paid"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Invoice Paid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]

		when : 'createObject is called'
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		then: 'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.isDeleted == false
	}
	void "Test Create Invoice Paid Validation Username Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def invoicePaid = [
			username:null,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]

		when:'createObject is called'
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Paid Validation Description Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def invoicePaid = [
			username:shiroUser,
			description:"",
			paidDate:new Date (2015,4,1)
		]

		when:'createObject is called'
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Paid Validation paid Date Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:null
		]

		when:'createObject is called'
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage
	}
	void "Test Update New Invoice paid"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid= invoicePaidService.createObject(invoicePaid)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.username == invoicePaid.username
		invoicePaid.description == invoicePaid.description
		invoicePaid.paidDate == invoicePaid.paidDate
	}
	void "Test Update Invoice Paid Validation username Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:null,
			description:"updateDescription",
			paidDate:new Date (2015,4,2)
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Paid Validation description Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:shiroUser,
			description:"",
			paidDate:new Date (2015,4,2)
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Paid Validation paid Date Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:shiroUser,
			description:"updateDescription",
			paidDate:null
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Invoice Paid"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)

		when:'softDeleted is called'
		invoicePaid = invoicePaidService.softDeletedObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.isDeleted == true
	}
}
