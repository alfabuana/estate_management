package estate_management

import grails.test.spock.IntegrationSpec

class PaymentVoucherIntegrationSpec extends IntegrationSpec {
	def userService
	def cashBankService
	def paymentVoucherService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create New Payment Voucher"() {
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

		when : 'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then: 'check has errors'
		paymentVoucher.hasErrors() == false
		paymentVoucher.isDeleted == false
	}
	void "Test Create Payment Request Validation username Not Null"(){
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
			username:null,
			cashBank:cashBank,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Request Validation cashBank Not Null"(){
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
			cashBank:null,
			code:"newCode",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Request Validation code Not Null"(){
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
			code:"",
			paymentDate:new Date(2015,3,27),
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Request Validation payment Date Not Null"(){
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
			paymentDate:null,
			isGBCH:true,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Request Validation isGCBH Not Null"(){
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
			isGBCH:null,
			dueDate:new Date(2015,3,27),
			totalAmount:5000
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Request Validation dueDate Not Null"(){
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
			dueDate:null,
			totalAmount:5000
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Create Payment Request Validation total Amount Not Null"(){
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
			totalAmount:null
		]

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage
	}
	void "Test Update New Payment Voucher"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			paymentDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000		
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == false
		paymentVoucher.username == paymentVoucher.username
		paymentVoucher.cashBank == paymentVoucher.cashBank
		paymentVoucher.code == paymentVoucher.code
		paymentVoucher.paymentDate == paymentVoucher.paymentDate
		paymentVoucher.isGBCH == paymentVoucher.isGBCH
		paymentVoucher.dueDate == paymentVoucher.dueDate
		paymentVoucher.isReconciled == paymentVoucher.isReconciled
		paymentVoucher.reconciliationDate == paymentVoucher.reconciliationDate
		paymentVoucher.totalAmount == paymentVoucher.totalAmount
	}
	void "Test Update Payment Voucher Validation Username Not Null"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:null,
			cashBank:cashBank,
			code:"updateCode",
			paymentDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000		
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation cash bank Not Null"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			cashBank:null,
			code:"updateCode",
			paymentDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation Code Not Null"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"",
			paymentDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation Payment Date Not Null"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			paymentDate:null,
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation isGBCH Not Null"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			paymentDate:new Date(2015,3,29),
			isGBCH:null,
			dueDate:new Date(2015,3,30),
			totalAmount:3000
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation due Date Not Null"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			paymentDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:null,
			totalAmount:3000
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
	void "Test Update Payment Voucher Validation Total Amount Not Null"(){
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		and:'setting data for Update'
		def paymentVoucher2 = [
			id: paymentVoucher.id,
			username:shiroUser,
			cashBank:cashBank,
			code:"updateCode",
			paymentDate:new Date(2015,3,29),
			isGBCH:false,
			dueDate:new Date(2015,3,30),
			totalAmount:null
			]

		when:'updateObject is called'
		paymentVoucher = paymentVoucherService.updateObject(paymentVoucher2)

		then:'check has errors'
		paymentVoucher.hasErrors() == true
		println "Validasi sukses dengan error message : " + paymentVoucher.errors.getFieldError().defaultMessage

	}
		void "Test SoftDelete Payment Voucher"() {
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
		paymentVoucher= paymentVoucherService.createObject(paymentVoucher)

		when:'createObject is called'
		paymentVoucher = paymentVoucherService.softDeletedObject(paymentVoucher)

		then:'check has errors'
		paymentVoucher.hasErrors() == false
		paymentVoucher.isDeleted == true
	}
		void "Test Confirm Payment Voucher"() {
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
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.confirmObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == false
			paymentVoucher.isConfirmed == true
		}
		void "Test unConfirm Payment Voucher"() {
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
			paymentVoucher= paymentVoucherService.createObject(paymentVoucher)
	
			when:'createObject is called'
			paymentVoucher = paymentVoucherService.unConfirmObject(paymentVoucher)
	
			then:'check has errors'
			paymentVoucher.hasErrors() == false
			paymentVoucher.isConfirmed == false
		}
}
