package estate_management

import grails.test.spock.IntegrationSpec

class ReceiptVoucherDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def receiptVoucherService
	def receivableService
	def receiptVoucherDetailService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New receipt Voucher Detail"() {
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		
		when : 'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then: 'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isDeleted == false
	}
	void "Test Create receipt Request Detail Validation receipt Voucher Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:null,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Detail Validation receivable Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:null,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Detail Validation Code Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"",
			amount:1000,
			description:"newDescription"
			]

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Detail Validation amount Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:null,
			description:"newDescription"
			]

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Detail Validation description Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:""
			]

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage
	}

	void "Test Update New receipt Voucher Detail"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail = receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"updateCode",
			amount:2000,
			description:"updateDescription"	
			]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.receiptVoucher == receiptVoucherDetail2.receiptVoucher
		receiptVoucherDetail.receivable == receiptVoucherDetail2.receivable
		receiptVoucherDetail.code == receiptVoucherDetail2.code
		receiptVoucherDetail.amount == receiptVoucherDetail2.amount
		receiptVoucherDetail.description == receiptVoucherDetail2.description
	}
	void "Test Update receipt Voucher DetailValidation receipt Voucher Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucher:null,
			receivable:receivable,
			code:"updateCode",
			amount:2000,
			description:"updateDescription"
			]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher DetailValidation receivable Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucher:receiptVoucher,
			receivable:null,
			code:"updateCode",
			amount:2000,
			description:"updateDescription"
			]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher DetailValidation code Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"",
			amount:2000,
			description:"updateDescription"
			]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher DetailValidation amount Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucher:null,
			receivable:receivable,
			code:"updateCode",
			amount:null,
			description:"updateDescription"
			]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher DetailValidation description Not Null"(){
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		and:'setting data for Update'
		def receiptVoucherDetail2 = [
			id: receiptVoucherDetail.id,
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:'updateCode',
			amount:2000,
			description:""
			]

		when:'updateObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.updateObject(receiptVoucherDetail2)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucherDetail.errors.getFieldError().defaultMessage

	}

	void "Test SoftDelete receipt Voucher"() {
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		when:'createObject is called'
		receiptVoucherDetail = receiptVoucherDetailService.softDeletedObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isDeleted == true
	}
	void "Test Confirm receipt Voucher"() {
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		when:'confirm is called'
		receiptVoucherDetail = receiptVoucherDetailService.confirmObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isConfirmed == true
	}
	void "Test unConfirm receipt Voucher"() {
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

		and: 'setting new receipt Voucher'
		def receiptVoucher = [
			username:shiroUser,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Receivable'
		def receivable= [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
			]
		receivable = receivableService.createObject(receivable)
		
		and:'setting new receipt Voucher Detail'
		def receiptVoucherDetail = [
			receiptVoucher:receiptVoucher,
			receivable:receivable,
			code:"newCode",
			amount:1000,
			description:"newDescription"
			]
		receiptVoucherDetail= receiptVoucherDetailService.createObject(receiptVoucherDetail)

		when:'confirm is called'
		receiptVoucherDetail = receiptVoucherDetailService.unConfirmObject(receiptVoucherDetail)

		then:'check has errors'
		receiptVoucherDetail.hasErrors() == false
		receiptVoucherDetail.isConfirmed == false
	}

}
