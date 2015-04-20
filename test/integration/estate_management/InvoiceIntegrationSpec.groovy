package estate_management

import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class InvoiceIntegrationSpec extends IntegrationSpec {
	def userService
	def invoiceService
	
	@Shared
	def shiroUser

    def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
    }

    def cleanup() {
    }

    void "Test Create New Invoice"() {
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0,
			]
		
		when : 'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then: 'check has errors'
		invoice.hasErrors() == false
		invoice.isDeleted == false
	}
	void "Test Create Invoice Validation invoice Date Not Null"(){
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:null,
			totalAmount:0,
			]
		when:'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage
	}
	

	void "Test Update Invoice"(){
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			invoiceDate:new Date (2015,3,31),
			totalAmount:0
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == false
		invoice.invoiceDate == invoice2.invoiceDate
		invoice.totalAmount == invoice2.totalAmount
	}
	void "Test Update Invoice Validation invoice Date Not Null"(){
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			invoiceDate:null,
			totalAmount:0
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage

	}

	void "Test SoftDelete Invoice"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and:'setting new Invoice'
		def invoice = [
			username:shiroUser,
			code:"newCode",
			invoiceDate:new Date (2015,3,30),
			description:"newDescription",
			dueDate:new Date (2015,3,31),
			totalAmount:10000
			]
		invoice = invoiceService.createObject(invoice)

		when:'softDelete is called'
		invoice = invoiceService.softDeletedObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isDeleted == true
	}
	void "Test Confirm Invoice"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and:'setting new Invoice'
		def invoice = [
			username:shiroUser,
			code:"newCode",
			invoiceDate:new Date (2015,3,30),
			description:"newDescription",
			dueDate:new Date (2015,3,31),
			totalAmount:10000
			]
		invoice = invoiceService.createObject(invoice)

		when:'Confirm is called'
		invoice = invoiceService.confirmObject(invoice)
		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isConfirmed == true
		
	}
	void "Test unConfirm invoice"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and:'setting new Invoice'
		def invoice = [
			username:shiroUser,
			code:"newCode",
			invoiceDate:new Date (2015,3,30),
			description:"newDescription",
			dueDate:new Date (2015,3,31),
			totalAmount:10000
			]
		invoice = invoiceService.createObject(invoice)

		when:'Confirm is called'
		invoice = invoiceService.confirmObject(invoice)
		invoice = invoiceService.unConfirmObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isConfirmed == false
	}
}
