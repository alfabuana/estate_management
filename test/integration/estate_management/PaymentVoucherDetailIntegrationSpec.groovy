package estate_management

import grails.test.spock.IntegrationSpec

class PaymentVoucherDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def paymentVoucherService
	def payableService
	def paymentVoucherDetailService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Payment Voucher Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		when : 'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.createObject(paymentVoucherDetail)

		then: 'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isDeleted == false
	}
	void "Test Create Payment Voucher Detail Validation paymentVoucher Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:null,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherService.createObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Voucher Detail Validation payable Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:null,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherService.createObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage
	}
	
	void "Test Create Payment Voucher Detail Validation Code Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"",
			amount:1000,
			description:"newDescription"
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherService.createObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Voucher Detail Validation amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:null,
			description:"newDescription"
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherService.createObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Voucher Detail Validation description Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:""
			]
		
		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherService.createObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update New Payment Voucher Detail"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"updateCode",
			amount:2000,
			description:"updateDescription"
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.paymentVoucher == paymentVoucherDetail.paymentVoucher
		paymentVoucherDetail.payable == paymentVoucherDetail.payable
		paymentVoucherDetail.code == paymentVoucherDetail.code
		paymentVoucherDetail.amount == paymentVoucherDetail.amount
		paymentVoucherDetail.description == paymentVoucherDetail.description
	}
	void "Test Update Payment Voucher Detail Validation Payment Voucher Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucher:null,
			payable:payable,
			code:"updateCode",
			amount:2000,
			description:"updateDescription"
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Detail Validation Payable Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucher:paymentVoucher,
			payable:null,
			code:"updateCode",
			amount:2000,
			description:"updateDescription"
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Detail Validation Code Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"",
			amount:2000,
			description:"updateDescription"
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Detail Validation amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"updateCode",
			amount:null,
			description:"updateDescription"
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage

	}

	void "Test Update Payment Voucher Detail Validation Payment Description Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		and:'setting data for Update'
		def paymentVoucherDetail2 = [
			id: paymentVoucherDetail.id,
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"updateCode",
			amount:2000,
			description:""
			]

		when:'updateObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.updateObject(paymentVoucherDetail2)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucherDetail.errors.getFieldError().defaultMessage

	}

	void "Test SoftDelete Payment Voucher Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.softDeletedObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isDeleted == true
	}

	void "Test Confirm Payment Voucher Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.confirmObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isConfirmed == true
	}
	void "Test unConfirm Payment Voucher Detail"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Payment Voucher'
		def paymentVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable = payableService.createObject(payable)
		
		and: 'setting new Payment Voucher Detail'
		def paymentVoucherDetail = [
			paymentVoucher:paymentVoucher,
			payable:payable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		paymentVoucherDetail= paymentVoucherDetailService.createObject(paymentVoucherDetail)

		when:'createObject is called'
		paymentVoucherDetail = paymentVoucherDetailService.unConfirmObject(paymentVoucherDetail)

		then:'check has errors'
		paymentVoucherDetail.hasErrors() == false
		paymentVoucherDetail.isConfirmed == false
	}
}
