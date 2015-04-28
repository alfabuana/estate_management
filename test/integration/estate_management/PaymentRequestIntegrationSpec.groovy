package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class PaymentRequestIntegrationSpec extends IntegrationSpec {
	def userService
	def paymentRequestService
	def vendorService
	def projectService
	def paymentRequestDetailService
	def cashBankService
	def paymentVoucherService
	def payableService
	def paymentVoucherDetailService
	
	@Shared
	def shiroUser
	def vendor
	def project

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		vendor = [
			name:"vendor1"]
		vendor = vendorService.createObject(vendor)
		
		project = [
			title:"project1"]
		project = projectService.createObject(project)
	}

	def cleanup() {
	}

	void "Test Create New Payment Request"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]

		when : 'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then: 'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isDeleted == false
	}
	void "Test Create PaymentRequest Validation vendor Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:null,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}
	void "Test Create PaymentRequest Validation project Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:null,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}

	void "Test Create PaymentRequest Validation request Date Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate:null
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}
	void "Test Create PaymentRequest Validation Due Date Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:null,
			requestDate:new Date (2015,3,26)
		]

		when:'createObject is called'
		paymentRequest = paymentRequestService.createObject(paymentRequest)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage
	}
	void "Test Update New Payment Request"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser.username,
			vendor:vendor,
			project:project,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.user == paymentRequest.user
		paymentRequest.vendor == paymentRequest.vendor
		paymentRequest.project == paymentRequest.project
		paymentRequest.dueDate == paymentRequest.dueDate
		paymentRequest.requestDate == paymentRequest.requestDate
	}
	void "Test Update New Payment Request Validation Is Confirmed"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		and:'setting new payment request detail'
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:50000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser.username,
			vendor:vendor,
			project:project,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi Sukses dengan error message" + paymentRequest.errors.getAllErrors().defaultMessage
	}
	void "Test Update Payment Request Validation vendor Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser.username,
			vendor:null,
			project:project,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Request Validation project Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser.username,
			vendor:vendor,
			project:null,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}

	void "Test Update Payment Request Validation due date Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser.username,
			vendor:vendor,
			project:project,
			dueDate:null,
			requestDate: new Date (2015,3,28)
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Request Validation request Date Not Null"(){
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		and:'setting data for Update'
		def paymentRequest2 = [
			id: paymentRequest.id,
			username:shiroUser.username,
			vendor:vendor,
			project:project,
			dueDate:new Date (2015,3,26),
			requestDate: null
		]

		when:'updateObject is called'
		paymentRequest = paymentRequestService.updateObject(paymentRequest2)

		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Payment Request"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)

		when:'createObject is called'
		paymentRequest = paymentRequestService.softDeletedObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isDeleted == true
	}
	void "Test SoftDelete Payment Request Validation Is Deleted"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		paymentRequest = paymentRequestService.softDeletedObject(paymentRequest)

		when:'createObject is called'
		paymentRequest = paymentRequestService.softDeletedObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi Sukses dengan error message :" + paymentRequest.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Payment Request Validation Is Confirmed"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)
		
		
		when:'createObject is called'
		paymentRequest = paymentRequestService.softDeletedObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi Sukses dengan error message :" + paymentRequest.errors.getAllErrors().defaultMessage
	}

	void "Test Confirm Payment Request"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]

		when:'createObject is called'
		paymentRequest = paymentRequestService.confirmObject(confirm)
		def payable =[ Payable.findAll{
			user == paymentRequest.user &&
					payableSource == "paymentRequest" &&
					payableSourceId == paymentRequest.id &&
					payableSourceDetailId == paymentRequestDetails.id &&
					amount == paymentRequestDetails.amount &&
					remainingAmount == paymentRequestDetails.amount
		}]
		
		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isConfirmed == true
		payable.size() > 0
	} 
	void "Test Confirm Payment Request Validation Is Confirmed"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)
		
		when:'createObject is called'
		paymentRequest = paymentRequestService.confirmObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi Sukses dengan error message :" + paymentRequest.errors.getAllErrors().defaultMessage
	}
	void "Test Confirm Payment Request Validation Has Detail"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		
		when:'createObject is called'
		paymentRequest = paymentRequestService.confirmObject(confirm)
		
		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi Sukses dengan error message :" + paymentRequest.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm Payment Request"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)
		when:'createObject is called'
		paymentRequest = paymentRequestService.unConfirmObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isConfirmed == false
	}

	void "Test unConfirm Payment Request Validation Is Not Confirmed"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		
		when:'createObject is called'
		paymentRequest = paymentRequestService.unConfirmObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi Sukses dengan error message :" + paymentRequest.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm PaymentRequest Validation payable Not Associate With payment Voucher"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)
		
		def cashBank = [
			name:"newName",
			description:"newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]

		cashBank = cashBankService.createObject(cashBank)
		def paymentVoucher = [
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,4,21),
			totalAmount:0
			]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		def paymentVoucherDetail = [
			paymentVoucherId:paymentVoucher.id,
			payable:payableService.getObjectBySource("paymentRequest", paymentRequest.id, paymentRequestDetails.id),
			username:shiroUser.username
			]
		paymentVoucherDetail = paymentVoucherDetailService.createObject(paymentVoucherDetail)

		when:'unConfirm is called'
		paymentRequest = paymentRequestService.unConfirmObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getAllErrors().defaultMessage
	}
	void "Test Print Payment Request"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]
		paymentRequest = paymentRequestService.confirmObject(confirm)

		when:'print is called'
		paymentRequest = paymentRequestService.printObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == false
		paymentRequest.isConfirmed == true
	}
	void "Test Print Payment Request Validation Is Not Confirmed"() {
		setup: 'setting new Payment Request'
		def paymentRequest = [
			username:shiroUser.username,
			vendor:vendor.id,
			project:project.id,
			dueDate:new Date (2015,3,26),
			requestDate: new Date (2015,3,28)
		]
		paymentRequest= paymentRequestService.createObject(paymentRequest)
		def paymentRequestDetails = [
			paymentRequestId:paymentRequest.id,
			amount:40000]
		paymentRequestDetails = paymentRequestDetailService.createObject(paymentRequestDetails)
		def confirm = [
			id:paymentRequest.id,
			username:shiroUser.username
			]

		when:'print is called'
		paymentRequest = paymentRequestService.printObject(paymentRequest)
		
		then:'check has errors'
		paymentRequest.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentRequest.errors.getAllErrors().defaultMessage
	}
}
