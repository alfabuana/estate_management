package estate_management

import grails.test.spock.IntegrationSpec

class ReceiptVoucherIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def receiptVoucherService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New receipt Voucher"() {
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

		when : 'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then: 'check has errors'
		receiptVoucher.hasErrors() == false
		receiptVoucher.isDeleted == false
	}
	void "Test Create receipt Request Validation username Not Null"(){
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
			username:null,
			cashBank:cashBank,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation cashBank Not Null"(){
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
			cashBank:null,
			code:"newCode",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation code Not Null"(){
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
			code:"",
			receiptDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation receipt Date Not Null"(){
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
			receiptDate:null,
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation isGCBH Not Null"(){
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
			isGBCH:null,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation dueDate Not Null"(){
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
			dueDate:null,
			totalAmount:5000
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create receipt Request Validation total Amount Not Null"(){
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
			totalAmount:null
		]

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Update New receipt Voucher"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			receiptDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000		
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == false
		receiptVoucher.username == receiptVoucher.username
		receiptVoucher.cashBank == receiptVoucher.cashBank
		receiptVoucher.code == receiptVoucher.code
		receiptVoucher.receiptDate == receiptVoucher.receiptDate
		receiptVoucher.isGBCH == receiptVoucher.isGBCH
		receiptVoucher.dueDate == receiptVoucher.dueDate
		receiptVoucher.isReconciled == receiptVoucher.isReconciled
		receiptVoucher.reconciliationDate == receiptVoucher.reconciliationDate
		receiptVoucher.totalAmount == receiptVoucher.totalAmount
	}
	void "Test Update receipt Voucher Validation Username Not Null"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:null,
			cashBank:cashBank,
			code:"updateCode",
			receiptDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000		
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation cash bank Not Null"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser,
			cashBank:null,
			code:"updateCode",
			receiptDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation Code Not Null"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"",
			receiptDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation receipt Date Not Null"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			receiptDate:null,
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation isGBCH Not Null"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			receiptDate:new Date(2015,3,29),
			isGBCH:null,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation due Date Not Null"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			receiptDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:null,
			totalAmount:3000
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update receipt Voucher Validation Total Amount Not Null"(){
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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		and:'setting data for Update'
		def receiptVoucher2 = [
			id: receiptVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			receiptDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:null
			]

		when:'updateObject is called'
		receiptVoucher = receiptVoucherService.updateObject(receiptVoucher2)

		then:'check has errors'
		receiptVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + receiptVoucher.errors.getFieldError().defaultMessage

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
		receiptVoucher= receiptVoucherService.createObject(receiptVoucher)

		when:'createObject is called'
		receiptVoucher = receiptVoucherService.softDeletedObject(receiptVoucher)

		then:'check has errors'
		receiptVoucher.hasErrors() == false
		receiptVoucher.isDeleted == true
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
			receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
	
			when:'createObject is called'
			receiptVoucher = receiptVoucherService.confirmObject(receiptVoucher)
	
			then:'check has errors'
			receiptVoucher.hasErrors() == false
			receiptVoucher.isConfirmed == true
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
			receiptVoucher= receiptVoucherService.createObject(receiptVoucher)
	
			when:'createObject is called'
			receiptVoucher = receiptVoucherService.unConfirmObject(receiptVoucher)
	
			then:'check has errors'
			receiptVoucher.hasErrors() == false
			receiptVoucher.isConfirmed == false
		}


}
