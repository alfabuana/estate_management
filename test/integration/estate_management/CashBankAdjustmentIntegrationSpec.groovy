package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class CashBankAdjustmentIntegrationSpec extends IntegrationSpec {
	def cashBankService
	def cashBankAdjustmentService
	def cashMutationService
	def userService


	@Shared
	def shiroUser

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)

	}

	def setupSpec()
	{

	}

	def cleanup() {
	}

	void "Test Create New Cash Bank Adjustment"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:shiroUser]

		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode"
		]

		when: 'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then: 'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isDeleted == false

	}
	void "Test Create Cash Bank Adjustment Validation Cash Bank Not Null"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:"",
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode"
		]

		when:'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Adjustment Validation Adjustment Date Not Null"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:null,
			amount:1000,
			code:"newCode"
		]

		when:'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Adjustment Validation Amount Not Null"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:null,
			code:"newCode"
		]

		when:'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}

	void "Test Update Cash Bank Adjustment"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode"
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,27),
			amount:"2000",
			code:"updateCode"
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.cashBank == cashBankAdjustment.cashBank
		cashBankAdjustment.adjustmentDate == cashBankAdjustment.adjustmentDate
		cashBankAdjustment.amount == cashBankAdjustment.amount
		cashBankAdjustment.code == cashBankAdjustment.code
		cashBankAdjustment.confirmationDate == cashBankAdjustment.confirmationDate
	}
	void "Test Update CashBank Adjustment Validation cash Bank Not Null"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:null,
			adjustmentDate:new Date (2015,3,27),
			amount:"2000",
			code:"updateCode",
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Adjustment Validation Adjustment Date Not Null"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:null,
			amount:"2000",
			code:"updateCode",
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Adjustment Validation Amount Not Null"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)
		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,27),
			amount: null,
			code:"updateCode",
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Adjustment Validation Is Confirmed"(){
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",

		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)
		def confirm = [
			id:cashBankAdjustment.id,
			username:shiroUser
		]

		cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirm)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,27),
			amount:"1000",
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getAllErrors().defaultMessage
	}

	void "Test SoftDelete Cash Bank Adjustment"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:1000,
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		when:'softDeleteObject is called'
		cashBankAdjustment = cashBankAdjustmentService.softDeletedObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isDeleted == true
	}
	void "Test SoftDelete CashBank Adjustment Validation Is Confirmed"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",

		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)
		def confirm = [
			id:cashBankAdjustment.id,
			username:shiroUser
		]

		cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirm)
		when:'softDeleteObject is called'
		cashBankAdjustment = cashBankAdjustmentService.softDeletedObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getAllErrors().defaultMessage
	}

	void "Test SoftDelete CashBank Adjustment Validation Is Deleted"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",

		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)
		cashBankAdjustment = cashBankAdjustmentService.softDeletedObject(cashBankAdjustment)
		
		when:'softDeleteObject is called'
		cashBankAdjustment = cashBankAdjustmentService.softDeletedObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getAllErrors().defaultMessage
	}

	void "Test Confirm CashBank Adjustment"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",

		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		when:'confirmObject is called'
		def confirm = [
			id:cashBankAdjustment.id,
			username:shiroUser
		]
		cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirm)
		def cashMutation =[ CashMutation.findAll{
			status == "plus" &&
					sourceDocumentType == "cashBankAdjustment" &&
					sourceDocumentCode == cashBankAdjustment.code &&
					sourceDocumentId == cashBankAdjustment.id &&
					amount == cashBankAdjustment.amount &&
					mutationDate == cashBankAdjustment.confirmationDate
		}]
		then:'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isConfirmed == true
		cashMutation.size() > 0
	}
	
	void "Test Confirm CashBank Adjustment Validation Is Confirmed"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",

		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)
		def confirm = [
			id:cashBankAdjustment.id,
			username:shiroUser
		]
		cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirm)
		def cashMutation =[ CashMutation.findAll{
			status == "plus" &&
					sourceDocumentType == "cashBankAdjustment" &&
					sourceDocumentCode == cashBankAdjustment.code &&
					sourceDocumentId == cashBankAdjustment.id &&
					amount == cashBankAdjustment.amount &&
					mutationDate == cashBankAdjustment.confirmationDate
		}]
		when:'confirmObject is called'
		def confirm2 = [
			id:cashBankAdjustment.id,
			username:shiroUser
		]
		cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirm2)
		def cashMutation2 =[ CashMutation.findAll{
			status == "plus" &&
					sourceDocumentType == "cashBankAdjustment" &&
					sourceDocumentCode == cashBankAdjustment.code &&
					sourceDocumentId == cashBankAdjustment.id &&
					amount == cashBankAdjustment.amount &&
					mutationDate == cashBankAdjustment.confirmationDate
		}]
		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getAllErrors().defaultMessage
	}

	
	void "Test unConfirmed CashBank Adjustment"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",

		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		when:'unConfirmObject is called'
		def confirm = [
			id:cashBankAdjustment.id,
			username:shiroUser
		]
		cashBankAdjustment = cashBankAdjustmentService.confirmObject(confirm)
		cashBankAdjustment = cashBankAdjustmentService.unConfirmObject(cashBankAdjustment)
		def cashMutation = CashMutation.findAll{
			status == "plus" &&
					sourceDocumentType == "cashBankAdjustment" &&
					sourceDocumentCode == cashBankAdjustment.code &&
					sourceDocumentId == cashBankAdjustment.id &&
					amount == cashBankAdjustment.amount &&
					mutationDate == cashBankAdjustment.confirmationDate &&
					isDeleted == true
		}


		then:'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isConfirmed == false
	}
	void "Test unConfirmed CashBank Adjustment Validation IsNotConfirmed"() {
		setup:'setting new Cash Bank'
		def cashBank = [name: "newName",
			description: "newDescription",
			amount:"1000",
			isBank: true,
			createdBy:ShiroUser]
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:"1000",
			code:"newCode",

		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		when:'unConfirmObject is called'
		cashBankAdjustment = cashBankAdjustmentService.unConfirmObject(cashBankAdjustment)
		def cashMutation = CashMutation.findAll{
			status == "plus" &&
					sourceDocumentType == "cashBankAdjustment" &&
					sourceDocumentCode == cashBankAdjustment.code &&
					sourceDocumentId == cashBankAdjustment.id &&
					amount == cashBankAdjustment.amount &&
					mutationDate == cashBankAdjustment.confirmationDate &&
					isDeleted == true
		}


		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getAllErrors().defaultMessage
	}


}
