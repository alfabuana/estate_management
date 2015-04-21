package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class InvoiceIntegrationSpec extends IntegrationSpec {
	def userService
	def invoiceService
	def invoiceDetailService
	def receiptVoucherService
	def receiptVoucherDetailService
	def cashBankService
	def receivableService
	
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
	void "Test Update Invoice Validation Is Confirmed"(){
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		
		and:'setting new Invoice Detail'
		def invoiceDetails = [
			invoiceId:invoice.id,
			amount:50000]
		invoiceDetails = invoiceDetailService.createObject(invoiceDetails)
		def confirm = [
			id:invoice.id,
			username:shiroUser
			]
		invoice = invoiceService.confirmObject(confirm)
		
		
		and:'setting data for Update'
		def invoice2 = [
			id: invoice.id,
			invoiceDate:new Date (2015,4,21),
			totalAmount:0
			]

		when:'updateObject is called'
		invoice = invoiceService.updateObject(invoice2)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage

	}

	void "Test SoftDelete Invoice"() {
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)

		when:'softDelete is called'
		invoice = invoiceService.softDeletedObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isDeleted == true
	}
	void "Test SoftDelete Invoice Validation Is Confirmed"() {
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetails = [
			invoiceId:invoice.id,
			amount:50000]
		invoiceDetails = invoiceDetailService.createObject(invoiceDetails)
		
		def confirm = [
			id:invoice.id,
			username:shiroUser
			]
		invoice = invoiceService.confirmObject(confirm)
		
		when:'softDelete is called'
		invoice = invoiceService.softDeletedObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Invoice Validation Is Deleted"() {
		setup:'setting new Invoice'
		def invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		invoice = invoiceService.softDeletedObject(invoice)
		
		when:'softDelete is called'
		invoice = invoiceService.softDeletedObject(invoice)

		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage
	}


	void "Test Confirm Invoice"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:50000,
			username:shiroUser]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		
		def confirm = [
			id:invoice.id,
			username:shiroUser
			]

		when:'Confirm is called'
		invoice = invoiceService.confirmObject(confirm)
		def receivable =[ Receivable.findAll{
			user == invoice.user &&
					receivableSource == "invoice" &&
					receivableSourceId == invoice.id &&
					receivableSourceDetailId == invoiceDetail.id &&
					amount == invoiceDetail.amount &&
					remainingAmount == invoiceDetail.amount
		}]
		
		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isConfirmed == true
		receivable.size() > 0
		
	}
	void "Test Confirm Invoice Validation Is Confirmed"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:50000,
			username:shiroUser]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		
		def confirm = [
			id:invoice.id,
			username:shiroUser
			]
		invoice = invoiceService.confirmObject(confirm)
		when:'Confirm is called'
		invoice = invoiceService.confirmObject(confirm)
		
		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage
		
	}
	void "Test Confirm Invoice Validation Has Detail"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		
		def confirm = [
			id:invoice.id,
			username:shiroUser
			]
		
		when:'Confirm is called'
		invoice = invoiceService.confirmObject(confirm)
		
		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage
		
	}
	void "Test unConfirm invoice"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:50000,
			username:shiroUser]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		
		def confirm = [
			id:invoice.id,
			username:shiroUser
			]

		when:'unConfirm is called'
		invoice = invoiceService.confirmObject(confirm)
		def receivable =[ Receivable.findAll{
			user == invoice.user &&
					receivableSource == "invoice" &&
					receivableSourceId == invoice.id &&
					receivableSourceDetailId == invoiceDetail.id &&
					amount == invoiceDetail.amount &&
					remainingAmount == invoiceDetail.amount
		}]
		invoice = invoiceService.unConfirmObject(confirm)
		
		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isConfirmed == false
	}
	void "Test unConfirm invoice Validation Is Not Confirmed"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:50000,
			username:shiroUser]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		

		when:'unConfirm is called'
		invoice = invoiceService.unConfirmObject(invoice)
		
		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm invoice Validation recevable Not Associate With Receipt Voucher"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:50000,
			username:shiroUser]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		
		def confirm = [
			id:invoice.id,
			username:shiroUser]
		invoice = invoiceService.confirmObject(confirm)
		
		def cashBank = [
			name:"newName",
			description:"newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]

		cashBank = cashBankService.createObject(cashBank)
		def receiptVoucher = [
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,4,21),
			totalAmount:0
			]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetail.id),
			username:shiroUser.username
			]
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		when:'unConfirm is called'
		invoice = invoiceService.unConfirmObject(invoice)
		
		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage
	}
	void "Test Print Invoice"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:50000,
			username:shiroUser]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		
		def confirm = [
			id:invoice.id,
			username:shiroUser
			]
		invoice = invoiceService.confirmObject(confirm)

		when:'Print is called'
		invoice = invoiceService.printObject(invoice)
		
		then:'check has errors'
		invoice.hasErrors() == false
		invoice.isConfirmed == true
		
	}
	void "Test Print Invoice Validation Is Not Confirmed"() {
		setup:'setting new Invoice'
		def invoice = [
			user:shiroUser,
			invoiceDate:new Date (2015,3,30),
			totalAmount:0
			]
		invoice = invoiceService.createObject(invoice)
		and:'setting new Invoice Detail'
		def invoiceDetail = [
			invoiceId:invoice.id,
			amount:50000,
			username:shiroUser]
		invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
		

		when:'Print is called'
		invoice = invoiceService.printObject(invoice)
		
		then:'check has errors'
		invoice.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoice.errors.getAllErrors().defaultMessage
	}
}

