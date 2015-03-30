package estate_management

import grails.test.spock.IntegrationSpec

class InvoiceIntegrationSpec extends IntegrationSpec {
	def userService
	def invoiceService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Invoice"() {
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
			totalAmount:10000,
			]
		
		when : 'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then: 'check has errors'
		invoice.hasErrors() == false
		invoice.isDeleted == false
	}
	void "Test Create Invoice Validation username Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and:'setting new Invoice'
		def invoice = [
			username:null,
			code:"newCode",
			invoiceDate:new Date (2015,3,30),
			description:"newDescription",
			dueDate:new Date (2015,3,31),
			totalAmount:10000,
			]
		when:'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Validation code Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and:'setting new Invoice'
		def invoice = [
			username:shiroUser,
			code:"",
			invoiceDate:new Date (2015,3,30),
			description:"newDescription",
			dueDate:new Date (2015,3,31),
			totalAmount:10000,
			]
		when:'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Validation invoice Date Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and:'setting new Invoice'
		def invoice = [
			username:shiroUser,
			code:"newCode",
			invoiceDate:null,
			description:"newDescription",
			dueDate:new Date (2015,3,31),
			totalAmount:10000,
			]
		when:'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Validation description Not Null"(){
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
			description:"",
			dueDate:new Date (2015,3,31),
			totalAmount:10000,
			]
		when:'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Validation due Date Not Null"(){
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
			dueDate:null,
			totalAmount:10000,
			]
		when:'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Validation total Amount Not Null"(){
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
			totalAmount:null,
			]
		when:'createObject is called'
		invoice = invoiceService.createObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage
	}

	void "Test Update Invoice"(){
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

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			username:shiroUser,
			code:"updateCode",
			invoiceDate:new Date (2015,3,31),
			description:"updateDescription",
			dueDate:new Date (2015,4,1),
			totalAmount:20000
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == false
		invoice.username == invoice2.username
		invoice.code == invoice2.code
		invoice.invoiceDate == invoice2.invoiceDate
		invoice.description == invoice2.description
		invoice.dueDate == invoice2.dueDate
		invoice.totalAmount == invoice2.totalAmount
	}
	void "Test Update Invoice Validation username Not Null"(){
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

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			username:null,
			code:"updateCode",
			invoiceDate:new Date (2015,3,31),
			description:"updateDescription",
			dueDate:new Date (2015,4,1),
			totalAmount:20000
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Validation code Not Null"(){
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

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			username:shiroUser,
			code:"",
			invoiceDate:new Date (2015,3,31),
			description:"updateDescription",
			dueDate:new Date (2015,4,1),
			totalAmount:20000
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Validation invoice Date Not Null"(){
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

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			username:shiroUser,
			code:"updateCode",
			invoiceDate:null,
			description:"updateDescription",
			dueDate:new Date (2015,4,1),
			totalAmount:20000
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Validation description Not Null"(){
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

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			username:shiroUser,
			code:"updateCode",
			invoiceDate:new Date (2015,3,31),
			description:"",
			dueDate:new Date (2015,4,1),
			totalAmount:20000
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Validation due Date Not Null"(){
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

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			username:shiroUser,
			code:"updateCode",
			invoiceDate:new Date (2015,3,31),
			description:"updateDescription",
			dueDate:null,
			totalAmount:20000
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Validation total Amount Not Null"(){
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

		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			username:shiroUser,
			code:"updateCode",
			invoiceDate:new Date (2015,3,31),
			description:"updateDescription",
			dueDate:new Date (2015,4,1),
			totalAmount:null
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
		invoice = invoiceService.unConfirmObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isConfirmed == false
	}
}
