package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class InvoiceDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def invoiceService
	def invoiceDetailService

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

	void "Test Create New Invoice Detail"() {
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000
		]
		when : 'createObject is called'
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		then: 'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isDeleted == false
	}
	void "Test Create Invoice Detail Validation Is Confirmed"(){
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000,
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		def confirm = [
			id:invoice.id,
			username:shiroUser.username
			]
		invoice = invoiceService.confirmObject(confirm)

		when:'create is called'
		def invoiceDetail2 = [
			invoiceId:invoice.id,
			amount:1000,
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail2)
		
		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getAllErrors().defaultMessage
	}

	void "Test Create Invoice Detail Validation amount Not Null"(){
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:null
		]

		when:'createObject is called'
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update Invoice Detail"(){
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:"1000"
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		and:'setting data for Update'
		def invoiceDetail2 = [
			id: invoiceDetail.id,
			invoiceId:invoice.id,
			amount:"2000"
		]

		when:'updateObject is called'
		invoiceDetail = invoiceDetailService.updateObject(invoiceDetail2)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.invoice == invoiceDetail.invoice
		invoiceDetail.amount == invoiceDetail.amount
	}
	void "Test Update Invoice Detail Validation Is Confirmed"(){
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:"1000"
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		def confirm = [
			id:invoice.id,
			username:shiroUser.username
			]
		invoice = invoiceService.confirmObject(confirm)

		and:'setting data for Update'
		def invoiceDetail2 = [
			id: invoiceDetail.id,
			invoiceId:invoice.id,
			amount:"2000"
		]

		when:'updateObject is called'
		invoiceDetail = invoiceDetailService.updateObject(invoiceDetail2)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getAllErrors().defaultMessage
	}
	void "Test Update Invoice Detail Validation amount Not Null"(){
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		and:'setting data for Update'
		def invoiceDetail2 = [
			id:invoiceDetail.id,
			invoiceId:invoice.id,
			amount:null
		]

		when:'updateObject is called'
		invoiceDetail = invoiceDetailService.updateObject(invoiceDetail2)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getFieldError().defaultMessage

	}

	void "Test SoftDelete Invoice Detail"() {
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		when:'softDelete is called'
		invoiceDetail = invoiceDetailService.softDeletedObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isDeleted == true
	}
	void "Test SoftDelete Invoice Detail Validation Is Confirmed"() {
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		def confirm = [
			id:invoice.id,
			username:shiroUser.username
			]
		invoice = invoiceService.confirmObject(confirm)

		when:'softDelete is called'
		invoiceDetail = invoiceDetailService.softDeletedObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Invoice Detail Validation Is Deleted"() {
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		invoiceDetail = invoiceDetailService.softDeletedObject(invoiceDetail)

		when:'softDelete is called'
		invoiceDetail = invoiceDetailService.softDeletedObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoiceDetail.errors.getAllErrors().defaultMessage
	}


	void "Test Confirm Invoice Detail"() {
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)

		def confirm = [
			id:invoiceDetail.id,
			username:shiroUser.username]
		when:'confirm is called'
		invoiceDetail = invoiceDetailService.confirmObject(confirm)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isConfirmed == true
	}
	void "Test unConfirm Invoice Detail"() {
		setup:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:1000
		]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		def confirm = [
			id:invoiceDetail.id,
			username:shiroUser.username]
		invoiceDetail = invoiceDetailService.confirmObject(confirm)

		when:'confirm is called'
		invoiceDetail = invoiceDetailService.unConfirmObject(invoiceDetail)

		then:'check has errors'
		invoiceDetail.hasErrors() == false
		invoiceDetail.isConfirmed == false
	}
}
