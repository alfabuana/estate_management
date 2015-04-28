package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class InvoicePaidIntegrationSpec extends IntegrationSpec {
	def userService
	def invoicePaidService
	def invoiceService
	
	@Shared
	def shiroUser
	def invoice

    def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0,
			]
		invoice = invoiceService.createObject(invoice)
    }

    def cleanup() {
    }

    void "Test Create New Invoice Paid"() {
		setup: 'setting new Invoice Paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date (2015,4,1)
		]

		when : 'createObject is called'
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		then: 'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.isDeleted == false
	}
	void "Test Create Invoice Paid Validation Invoice Not Null"(){
		setup: 'setting new Complaint'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:null,
			paidDate:new Date (2015,4,1)
		]

		when:'createObject is called'
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Paid Validation paid Date Not Null"(){
		setup: 'setting new Complaint'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:null
		]

		when:'createObject is called'
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage
	}
	void "Test Update New Invoice paid"(){
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date (2015,4,1)
		]
		invoicePaid= invoicePaidService.createObject(invoicePaid)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:shiroUser,
			invoice:invoice,
			paidDate:new Date (2015,4,2)
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.user == invoicePaid.user
		invoicePaid.invoice == invoicePaid.invoice
		invoicePaid.paidDate == invoicePaid.paidDate
	}
	void "Test Update Invoice Paid Validation Is Confirmed"(){
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date (2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def confirm = [
			id:invoicePaid.id,
			username:shiroUser
			]
		invoicePaid = invoicePaidService.confirmObject(confirm)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:shiroUser.username,
			invoice:null,
			paidDate:new Date (2015,4,2)
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getAllErrors().defaultMessage

	}
	void "Test Update Invoice Paid Validation Invoice Not Null"(){
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date (2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:shiroUser.username,
			invoice:null,
			paidDate:new Date (2015,4,2)
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Paid Validation paid Date Not Null"(){
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date (2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)

		and:'setting data for Update'
		def invoicePaid2 = [
			id: invoicePaid.id,
			username:shiroUser.username,
			invoice:invoice,
			paidDate:null
		]

		when:'updateObject is called'
		invoicePaid = invoicePaidService.updateObject(invoicePaid2)

		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Invoice Paid"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)

		when:'softDeleted is called'
		invoicePaid = invoicePaidService.softDeletedObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.isDeleted == true
	}
	void "Test SoftDelete Invoice Paid Validation Is Confirmed"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def confirm = [
			id:invoicePaid.id,
			username:shiroUser.username]
		invoicePaid = invoicePaidService.confirmObject(confirm)

		when:'softDeleted is called'
		invoicePaid = invoicePaidService.softDeletedObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Invoice Paid Validation Is Deleted"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		invoicePaid = invoicePaidService.softDeletedObject(invoicePaid)

		when:'softDeleted is called'
		invoicePaid = invoicePaidService.softDeletedObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getAllErrors().defaultMessage
	}
	void "Test Confirm Invoice Paid"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def confirm = [
			id:invoicePaid.id,
			username:shiroUser.username
			]

		when:'confirm is called'
		invoicePaid = invoicePaidService.confirmObject(confirm)
		
		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.isConfirmed == true
	}
	void "Test Confirm Invoice Paid Validation Is Confirmed"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def confirm = [
			id:invoicePaid.id,
			username:shiroUser.username
			]
		invoicePaid = invoicePaidService.confirmObject(confirm)
		when:'confirm is called'
		invoicePaid = invoicePaidService.confirmObject(confirm)
		
		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm Invoice Paid"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def confirm = [
			id:invoicePaid.id,
			username:shiroUser.username
			]
		invoicePaid = invoicePaidService.confirmObject(confirm)
		when:'confirm is called'
		invoicePaid = invoicePaidService.unConfirmObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoicePaid.isConfirmed == false
	}
	void "Test unConfirm Invoice Paid Validation Is Not Confirmed"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		when:'confirm is called'
		invoicePaid = invoicePaidService.unConfirmObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getAllErrors().defaultMessage
	}
	void "Test Clear Invoice Paid"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def clear = [
			id:invoicePaid.id,
			username:shiroUser.username
			]

		when:'clear is called'
		invoicePaid = invoicePaidService.clearObject(clear)
		
		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoice.isCleared == true
	}
	void "Test Clear Invoice Paid Validation Is Confirmed"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def clear = [
			id:invoicePaid.id,
			username:shiroUser.username
			]
		invoicePaid = invoicePaidService.clearObject(clear)
		when:'confirm is called'
		invoicePaid = invoicePaidService.clearObject(clear)
		
		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getAllErrors().defaultMessage
	}
	void "Test unClear Invoice Paid"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]

		invoicePaid= invoicePaidService.createObject(invoicePaid)
		def clear = [
			id:invoicePaid.id,
			username:shiroUser.username
			]
		invoicePaid = invoicePaidService.clearObject(clear)
		when:'confirm is called'
		invoicePaid = invoicePaidService.unClearObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == false
		invoice.isCleared == false
	}
	void "Test unClear Invoice Paid Validation Is Not Confirmed"() {
		setup: 'setting new Invoice paid'
		def invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date(2015,4,1)
		]
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		when:'confirm is called'
		invoicePaid = invoicePaidService.unClearObject(invoicePaid)
		
		then:'check has errors'
		invoicePaid.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaid.errors.getAllErrors().defaultMessage
	}
}
