package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class PaymentVoucherDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def paymentVoucherService
	def vendorService
	def projectService
	def payableService
	def paymentRequestService
	def paymentRequestDetailService
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
	def paymentVoucher

    def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		cashBank = [
			name:"newName",
			isBank: true,
			username:shiroUser.username]
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
		
		paymentVoucher = [
			username:shiroUser.username,
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,3,27)
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
    }

    def cleanup() {
    }

    void "Test Create New Payment Voucher Detail"() {
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			username:shiroUser.username,
			]
		when : 'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.createObject(paymentVoucherDetail)

		then: 'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isDeleted == false
	}
	void "Test Create Payment Voucher Detail Validation payable Not Null"(){
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:null
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.createObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage
	}
	
	void "Test Create Payment Voucher Detail Validation Is Confirmed"(){
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		paymentVoucherDetail = paymentVoucherDetailService.createObject(paymentVoucherDetail)
		
		def confirm = [
			id:paymentVoucher.id,
			username:shiroUser.username
			]
		paymentVoucher = paymentVoucherService.confirmObject(confirm)
		
		def paymentVoucherDetail2 = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.createObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getAllErrors().defaultMessage
	}
	void "Test Update New Payment Voucher Detail"(){
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.paymentVoucher == paymentVoucherDetail.paymentVoucher
		paymentVoucherDetail.payable == paymentVoucherDetail.payable
		paymentVoucherDetail.amount == paymentVoucherDetail.amount
	}
	void "Test Update Payment Voucher Detail Validation Payable Not Null"(){
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucherId:paymentVoucher.id,
			payable:null,
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage

	}
	
	void "Test Update Payment Voucher Detail Validation Is Confirmed"(){
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		paymentVoucherDetail = paymentVoucherDetailService.createObject(paymentVoucherDetail)
		
		def confirm = [
			id:paymentVoucher.id,
			username:shiroUser.username
			]
		paymentVoucher = paymentVoucherService.confirmObject(confirm)
		
		def paymentVoucherDetail2 = [
			id:paymentVoucherDetail.id,
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getAllErrors().defaultMessage
	}

	void "Test SoftDelete Payment Voucher Detail"() {
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.softDeletedObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isDeleted == true
	}

	void "Test Confirm Payment Voucher Detail"() {
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)
		
		def confirm = [
			id:paymentVoucherDetail.id,
			username:shiroUser.username
			]

		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.confirmObject(confirm)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isConfirmed == true
	}
	
	void "Test unConfirm Payment Voucher Detail"() {
		setup: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)
		
		def confirm = [
			id:paymentVoucherDetail.id,
			username:shiroUser.username
			]

		paymentVoucherDetail = paymentVoucherDetailService.confirmObject(confirm)

		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.unConfirmObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isConfirmed == false
	}
}
