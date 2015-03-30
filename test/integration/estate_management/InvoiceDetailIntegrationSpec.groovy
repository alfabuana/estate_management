package estate_management

import grails.test.spock.IntegrationSpec

class InvoiceDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def invoiceService
	def invoiceDetailService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create New Invoice Detail"() {
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		when : 'createObject is called'
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		then: 'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isDeleted == false
	}
	void "Test Create Invoice Detail Validation invoice Not Null"(){
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:null,
			code:"newCode",
			amount:1000
		]

		when:'createObject is called'
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Detail Validation code Not Null"(){
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"",
			amount:1000
		]

		when:'createObject is called'
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Invoice Detail Validation amount Not Null"(){
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:null
		]

		when:'createObject is called'
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update Invoice Detail"(){
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		and:'setting data for Update'
		def invoiceDetail2 = [
			id: invoiceDetail.id,
			invoice:invoice,
			code:"updateCode",
			amount:2000
		]

		when:'updateObject is called'
		invoiceDetail = invoiceDetailService.updateObject(invoiceDetail2)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.invoice == invoiceDetail2.invoice
		invoiceDetail.code == invoiceDetail2.code
		invoiceDetail.amount == invoiceDetail2.amount
	}
	void "Test Update Invoice Detail Validation invoice Not Null"(){
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		and:'setting data for Update'
		def invoiceDetail2 = [
			id:invoiceDetail.id,
			invoice:null,
			code:"updateCode",
			amount:2000
		]

		when:'updateObject is called'
		invoiceDetail = invoiceDetailService.updateObject(invoiceDetail2)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Detail Validation code Not Null"(){
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		and:'setting data for Update'
		def invoiceDetail2 = [
			id:invoiceDetail.id,
			invoice:invoice,
			code:"",
			amount:1000
		]

		when:'updateObject is called'
		invoiceDetail = invoiceDetailService.updateObject(invoiceDetail2)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update Invoice Detail Validation amount Not Null"(){
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		and:'setting data for Update'
		def invoiceDetail2 = [
			id:invoiceDetail.id,
			invoice:invoice,
			code:"updateCode",
			amount:null
		]

		when:'updateObject is called'
		invoiceDetail = invoiceDetailService.updateObject(invoiceDetail2)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage

	}

	void "Test SoftDelete Invoice Detail"() {
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		when:'softDelete is called'
		invoiceDetail = invoiceDetailService.softDeletedObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isDeleted == true
	}

	void "Test Confirm Invoice Detail"() {
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		when:'confirm is called'
		invoiceDetail = invoiceDetailService.confirmObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isConfirmed == true
	}
	void "Test unConfirm Invoice Detail"() {
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
		invoice = invoiceService.createObject(invoice)

		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoice:invoice,
			code:"newCode",
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		when:'confirm is called'
		invoiceDetail = invoiceDetailService.unConfirmObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isConfirmed == false
	}
}
