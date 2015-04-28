package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class ReceiptVoucherDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def receiptVoucherService
	def invoiceService
	def invoiceDetailService
	def receivableService
	def receiptVoucherDetailService
	def cashBankAdjustmentService
	def receiptVoucherDetailSercice

	def shiroUser
	def cashBank
	def invoice
	def invoiceDetails
	def confirmInvoice
	def cashBankAdjustment
	def confirmCashBankAdjustment
	def receiptVoucher

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)

		cashBank = [
			name:"newName",
			amount:100000,
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)

		cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode"
		]
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		confirmCashBankAdjustment = [
			id:cashBankAdjustment.id,
			username:shiroUser.username
		]
		cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirmCashBankAdjustment)

		invoice = [
			invoiceDate:new Date(2015,4,21)
		]
		invoice = invoiceService.createObject(invoice)

		invoiceDetails = [
			invoiceId:invoice.id,
			amount:500]
		invoiceDetails = invoiceDetailService.createObject(invoiceDetails)

		confirmInvoice = [
			id:invoice.id,
			username:shiroUser.username
		]
		invoice = invoiceService.confirmObject(confirmInvoice)

		receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
	}

	def cleanup() {
	}

	void "Test Create New receipt Voucher Detail"() {
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]

		when : 'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then: 'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isDeleted == false
	}
	void "Test Create receipt Request Detail Validation receivable Not Null"(){
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:null
		]

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Detail Validation is confirmed"(){
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetails = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id),
		]
		receiptVoucherDetails = receiptVoucherDetailService.createObject(receiptVoucherDetails)
		
		def  confirm = [
			id:receiptVoucher.id,
			username:shiroUser.username
			]
		receiptVoucher = receiptVoucherService.confirmObject(confirm)

		when:'createObject is called'
		def receiptVoucherDetail2 = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]
		receiptVoucherDetails = receiptVoucherDetailService.createObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetails.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetails.errors.getAllErrors().defaultMessage
	}

	void "Test Update New receipt Voucher Detail"(){
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.receiptVoucher == receiptVoucherDetail.receiptVoucher
		receiptVoucherDetail.receivable == receiptVoucherDetail.receivable
		receiptVoucherDetail.amount == receiptVoucherDetail.amount
	}
	void "Test Update receipt Voucher DetailValidation receivable Not Null"(){
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucherId:receiptVoucher.id,
			receivable:null
		]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher DetailValidation is confirmed"(){
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetails = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id),
		]
		receiptVoucherDetails = receiptVoucherDetailService.createObject(receiptVoucherDetails)
		
		def  confirm = [
			id:receiptVoucher.id,
			username:shiroUser.username
			]
		receiptVoucher = receiptVoucherService.confirmObject(confirm)

		when:'createObject is called'
		def receiptVoucherDetail2 = [
			id:receiptVoucherDetails.id,
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]
		receiptVoucherDetails = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetails.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetails.errors.getAllErrors().defaultMessage

	}
	void "Test SoftDelete receipt Voucher"() {
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.softDeletedObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isDeleted == true
	}
	void "Test Confirm receipt Voucher"() {
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		def confirm = [
			id:receiptVoucherDetail.id,
			username:shiroUser.username
			]
		
		when:'confirm is called'
		receiptVoucherDetail = receiptVoucherDetailService.confirmObject(confirm)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isConfirmed == true
	}
	void "Test unConfirm receipt Voucher"() {
		setup:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id)
		]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		def confirm = [
			id:receiptVoucherDetail.id,
			username:shiroUser.username
			]
		
		receiptVoucherDetail = receiptVoucherDetailService.confirmObject(confirm)


		when:'confirm is called'
		receiptVoucherDetail = receiptVoucherDetailService.unConfirmObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isConfirmed == false
	}

}
