package estate_management

import grails.test.spock.IntegrationSpec
import spock.lang.Shared
class ReceiptVoucherIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def receiptVoucherService
	def invoiceService
	def invoiceDetailService
	def receivableService
	def receiptVoucherDetailService
	def cashBankAdjustmentService
	
	@Shared
	def shiroUser
	def cashBank
	def invoice
	def invoiceDetails
	def confirmInvoice
	def cashBankAdjustment
	def confirmCashBankAdjustment

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

    }

    def cleanup() {
    }

    void "Test Create New receipt Voucher"() {
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]

		when : 'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then: 'check has errors'
		receiptVoucher.hasErrors() == false
		receiptVoucher.isDeleted == false
	}
	void "Test Create receipt Request Validation username Not Null"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:null,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation cashBank Not Null"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:null,
			receiptDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation receipt Date Not Null"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:null
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Update New receipt Voucher"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,25)
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == false
		receiptVoucher.user == receiptVoucher.user
		receiptVoucher.cashBank == receiptVoucher.cashBank
		receiptVoucher.receiptDate == receiptVoucher.receiptDate
	}
	void "Test Update receipt Voucher Validation Username Not Null"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser.username,
			user:null,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)	
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation cash bank Not Null"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser.username,
			user:shiroUser,
			cashBank:null,
			receiptDate:new Date(2015,3,27)
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation receipt Date Not Null"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:null
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation Is Confirmed"(){
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
		
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

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,28)
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getAllErrors().defaultMessage

	}
		void "Test SoftDelete receipt Voucher"() {
		setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.softDeletedObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == false
		receiptVoucher.isDeleted == true
	}
		
		void "Test SoftDelete receipt Voucher validation is deleted"() {
			setup: 'setting new receipt Voucher'
			def receiptVoucher = [
				username:shiroUser.username,
				user:shiroUser,
				cashBank:cashBank,
				receiptDate:new Date(2015,3,27)
			]
			receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
			receiptVoucher = receiptVoucherService.softDeletedObject(receiptVoucher)
	
			when:'createObject is called'
			receiptVoucher = receiptVoucherService.softDeletedObject(receiptVoucher)
	
			then:'check has errors'
			receiptVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + receiptVoucher.errors.getAllErrors().defaultMessage
		}
		
		void "Test softdeleted receipt Voucher Validation Is Confirmed"(){
			setup: 'setting new receipt Voucher'
			def receiptVoucher = [
				username:shiroUser.username,
				user:shiroUser,
				cashBank:cashBank,
				receiptDate:new Date(2015,3,27)
			]
			receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
			
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
			
			when:'soft delete is called'
			receiptVoucher = receiptVoucherService.softDeletedObject(receiptVoucher)
	
			then:'check has errors'
			receiptVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + receiptVoucher.errors.getAllErrors().defaultMessage
	
		}
		void "Test Confirm receipt Voucher"() {
				setup: 'setting new receipt Voucher'
			def receiptVoucher = [
				username:shiroUser.username,
				user:shiroUser,
				cashBank:cashBank,
				receiptDate:new Date(2015,3,27)
			]
			receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
			
			def receiptVoucherDetails = [
				receiptVoucherId:receiptVoucher.id,
				receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id),
			]
			receiptVoucherDetails = receiptVoucherDetailService.createObject(receiptVoucherDetails)
			
			def  confirm = [
				id:receiptVoucher.id,
				username:shiroUser.username
				]
			
	
			when:'createObject is called'
			receiptVoucher = receiptVoucherService.confirmObject(confirm)
	
			then:'check has errors'
			receiptVoucher.hasErrors() == false
			receiptVoucher.isConfirmed == true
		}
		
		void "Test Confirm receipt Voucher validation is confirmed"() {
			setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
		
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
		receiptVoucher = receiptVoucherService.confirmObject(confirm)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getAllErrors().defaultMessage
	}
		
		void "Test Confirm receipt Voucher validation has detail"() {
			setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
		
		
		def  confirm = [
			id:receiptVoucher.id,
			username:shiroUser.username
			]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.confirmObject(confirm)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getAllErrors().defaultMessage
	}
		void "Test unConfirm receipt Voucher"() {
			setup: 'setting new receipt Voucher'
			def receiptVoucher = [
				username:shiroUser.username,
				user:shiroUser,
				cashBank:cashBank,
				receiptDate:new Date(2015,3,27)
			]
			receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
			
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
			receiptVoucher = receiptVoucherService.unConfirmObject(receiptVoucher)
	
			then:'check has errors'
			receiptVoucher.hasErrors() == false
			receiptVoucher.isConfirmed == false
		}
		
		void "Test unConfirm receipt Voucher validation is not confirmed"() {
			setup: 'setting new receipt Voucher'
			def receiptVoucher = [
				username:shiroUser.username,
				user:shiroUser,
				cashBank:cashBank,
				receiptDate:new Date(2015,3,27)
			]
			receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
			
			def receiptVoucherDetails = [
				receiptVoucherId:receiptVoucher.id,
				receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id),
			]
			receiptVoucherDetails = receiptVoucherDetailService.createObject(receiptVoucherDetails)
			
	
			when:'createObject is called'
			receiptVoucher = receiptVoucherService.unConfirmObject(receiptVoucher)
	
			then:'check has errors'
			receiptVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + receiptVoucher.errors.getAllErrors().defaultMessage
		}

		void "Test print receipt Voucher"() {
			setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
		
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
		receiptVoucher = receiptVoucherService.printObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == false
		receiptVoucher.isConfirmed == true
	}
		
		void "Test print receipt Voucher validation is not confirmed"() {
			setup: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,3,27)
		]
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
		
		def receiptVoucherDetails = [
			receiptVoucherId:receiptVoucher.id,
			receivable:receivableService.getObjectBySource("invoice", invoice.id, invoiceDetails.id),
		]
		receiptVoucherDetails = receiptVoucherDetailService.createObject(receiptVoucherDetails)
		
		def  confirm = [
			id:receiptVoucher.id,
			username:shiroUser.username
			]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.printObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() ==true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getAllErrors().defaultMessage
	}
	
}
