package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared
class CashBankIntegrationSpec extends IntegrationSpec {
	def cashBankService
	def userService
	def paymentVoucherService
	def receiptVoucherService
	def cashBankMutationService
	def cashBankAdjustmentService

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

    void "Test Create New Cash Bank"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]

		when:'createObject is called'
		cashBank = cashBankService.createObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == false
		cashBank.isDeleted == false
    }
	void "Test Create Cash Bank Validation Name Not Null"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser] 
		when:'createObject is called'
		cashBank = cashBankService.createObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println cashBank.errors.getFieldError().defaultMessage
	}
	
	void "Test Create Cash Bank Validation isBank Not Null"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: null,
			createdBy:shiroUser]
		 
		when:'createObject is called'
		cashBank = cashBankService.createObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Update New Cash Bank"() {
		setup:'setting new Cash Bank'
		def cashBank = [name:"newName",
			description:"newDescription",
			amount:"1000",
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)
		
		and: 'setting data for Update'
		def cashBank2 = [
			id:cashBank.id,
			name:"updateName",
			description:"updateDescription",
			amount:"2000",
			isBank: true,
			createdBy:shiroUser]
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == false
		cashBank.name == cashBank.name
		cashBank.description == cashBank.description
		cashBank.amount == cashBank.amount
		cashBank.isBank == cashBank.isBank
	}
	void "Test Update Cash Bank Validation Name Not Null"() {
		setup:'setting new Cash Bank'
		def cashBank = [name:"newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)
		
		and: 'setting data for Update'
		def cashBank2 = [
			id:cashBank.id,
			name:"",
			description: "updateDescription",
			amount:"2000",
			isBank: true,
			createdBy:shiroUser]
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Update Cash Bank Validation Amount Not Null"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)
		
		and: 'setting data for Update'
		def cashBank2 = [
			id:cashBank.id,
			name: "updateName",
			description: "updateDescription",
			amount:null,
			isBank: true,
			createdBy:shiroUser]
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Update Cash Bank Validation isBank Not Null"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)
		
		and: 'setting data for Update'
		def cashBank2 = [id:cashBank.id,
			name: "updateName",
			description: "updateDescription",
			amount:"2000",
			isBank: null,
			createdBy:shiroUser]
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete CashBank"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)
		
		when:'softDeleteObject is called'
		cashBank = cashBankService.softDeletedObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == false
		cashBank.isDeleted == true
	}
	void "Test SoftDelete CashBank Validation Is Deleted"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)
		cashBank = cashBankService.softDeletedObject(cashBank)
		when:'softDeleteObject is called'
		cashBank = cashBankService.softDeletedObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete CashBank Validation CashBank Is used"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]
		cashBank = cashBankService.createObject(cashBank)
		
		and:'setting new Payment Voucher'
		def paymentVoucher = [
			user:shiroUser,
			cashBank:cashBank,
			paymentDate:new Date(2015,4,17),
			isGBCH:true,
			totalAmount:100000
			]
		paymentVoucher = paymentVoucherService.createObject(paymentVoucher)
		
		and:'setting new Receipt Voucher'
		def receiptVoucher = [
			user:shiroUser,
			cashBank:cashBank,
			receiptDate:new Date(2015,4,17),
			isGBCH:true,
			totalAmount:100000
			]
		receiptVoucher = receiptVoucherService.createObject(receiptVoucher)
		
		and:'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:500000
			]
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)
		
		and:'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode"
			]
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)
		
		when:'softDeleteObject is called'
		cashBank = cashBankService.softDeletedObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getAllErrors().defaultMessage
	}
}
