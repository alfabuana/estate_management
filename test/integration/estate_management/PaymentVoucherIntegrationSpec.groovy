package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class PaymentVoucherIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def paymentVoucherService
	def vendorService
	def projectService
	def paymentRequestService
	def paymentRequestDetailService
	def payableService
	def paymentVoucherDetailService
	def cashBankAdjustmentService
	
	@Shared
	def shiroUser
	def cashBank
	def vendor
	def project
	def paymentRequest
	def paymentRequestDetails
	def confirmPaymentRequest
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
		
		vendor = [
			name:"vendor1"]
		vendor = vendorService.createObject(vendor)
		
		project = [
			title:"project1"]
		project = projectService.createObject(project)
		
		paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest = paymentRequestService.createObject(paymentRequest)
		
		paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:500]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		
		confirmPaymentRequest = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirmPaymentRequest)

	}

	def cleanup() {
	}

	void "Test Create New Payment Voucher"() {
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]

		when : 'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then: 'check has errors'
		paymentVoucher.hasErrors() == false
		paymentVoucher.isDeleted == false
	}
	void "test create payment voucher Validation username Not Null"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser.username,
			user:null,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "test create payment voucher Validation cashBank Not Null"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			user:shiroUser,
			cashBank:null,
			paymentDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "test create payment voucher Validation payment Date Not Null"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:null
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Update New Payment Voucher"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,28)
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == false
		paymentVoucher.user== paymentVoucher.user
		paymentVoucher.cashBank == paymentVoucher.cashBank
		paymentVoucher.paymentDate == paymentVoucher.paymentDate
	}
	void "Test Update Payment Voucher Validation Username Not Null"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			user:shiroUser,
			paymentDate:new Date(2015,3,27)
		]
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser.username,
			user:null,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation cash bank Not Null"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			user:shiroUser,
			cashBank:null,
			paymentDate:new Date(2015,3,27)
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation Payment Date Not Null"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:null
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	
	void "Test Update Payment Voucher Validation Is Confirmed"(){
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
		
		def paymentVoucherDetails = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
		]
		paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
		
		def  confirm = [
			id:paymentVoucher.id,
			username:shiroUser.username
			]
		paymentVoucher = paymentVoucherService.confirmObject(confirm)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,4,21)
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage

	}
		void "Test SoftDelete Payment Voucher"() {
		setup: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.softDeletedObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == false
		paymentVoucher.isDeleted == true
	}
		
		void "Test SoftDelete Payment Voucher Validation Is Deleted"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			paymentVoucher = paymentVoucherService.softDeletedObject(paymentVoucher)
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.softDeletedObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage
		}
		
		void "Test soft delete Payment Voucher Validation Is Confirmed"(){
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
	
			when:'updateObject is called'
			paymentVoucher = paymentVoucherService.softDeletedObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage
	
		}
		void "Test Confirm Payment Voucher"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == false
			paymentVoucher.isConfirmed == true
		}
		
		void "Test Confirm Payment Voucher Validation Is Confirmed"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
			
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage
		}
		
		void "Test Confirm Payment Voucher Validation CashBank Amount Lower Than Total Amount"() {
			setup: 'setting new Payment Voucher'
			def cashBank2 = [
				name:"newName",
				amount:0,
				isBank: true,
				createdBy:shiroUser]
			cashBank2 = cashBankService.createObject(cashBank2)
			
			cashBankAdjustment = [
				cashBank:cashBank2,
				adjustmentDate:new Date (2015,3,26),
				amount:10,
				code:"newCode"
			]
			cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)
			
			confirmCashBankAdjustment = [
				id:cashBankAdjustment.id,
				username:shiroUser.username
				]
			cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirmCashBankAdjustment)
			
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank2,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage
		}
		
		void "Test Confirm Payment Voucher Validation Has detail"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage
		}
		void "Test unConfirm Payment Voucher"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.unConfirmObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == false
			paymentVoucher.isConfirmed == false
		}
		
		void "Test unConfirm Payment Voucher Validation Is Not Confirmed"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.unConfirmObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage
		}
		
		void "Test Print Payment Voucher"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			paymentVoucher = paymentVoucherService.confirmObject(confirm)
			
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.printObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == false
			paymentVoucher.isConfirmed == true
		}
		
		void "Test Print Payment Voucher Validation Is Not Confirmed"() {
			setup: 'setting new Payment Voucher'
			def paymentVoucher = [
				username:shiroUser,
				user:shiroUser,
				cashBank:cashBank,
				paymentDate:new Date(2015,3,27)
			]
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
			
			def paymentVoucherDetails = [
				paymentVoucherId:paymentVoucher.id,
				payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
			paymentVoucherDetails = paymentVoucherDetailService.createObject(paymentVoucherDetails)
			
			def  confirm = [
				id:paymentVoucher.id,
				username:shiroUser.username
				]
			
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.printObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == true
			println "Validasi sukses dengan error message : " + paymentVoucher.errors.getAllErrors().defaultMessage
		}
}
