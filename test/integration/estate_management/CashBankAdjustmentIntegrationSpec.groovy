package estate_management

import grails.test.spock.IntegrationSpec

class CashBankAdjustmentIntegrationSpec extends IntegrationSpec {
	def cashBankService
	def cashBankAdjustmentService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create New Cash Bank Adjustment"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when: 'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then: 'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isDeleted == false

	}
	void "Test Create Cash Bank Adjustment Validation Cash Bank Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:"",
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Adjustment Validation Adjustment Date Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:null,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Adjustment Validation Amount Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:null,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}

	void "Test Create Cash Bank Adjustment Validation Code Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"",
			confirmationDate:new Date(2015,3,27)
		]

		when:'createObject is called'
		cashBankAdjustment = cashBankAdjustmentService.createObject(cashBankAdjustment)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Update Cash Bank Adjustment"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,27),
			amount:2000,
			code:"updateCode",
			confirmationDate:new Date(2015,3,28)
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
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:null,
			adjustmentDate:new Date (2015,3,27),
			amount:2000,
			code:"updateCode",
			confirmationDate:new Date(2015,3,28)
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Adjustment Validation Adjustment Date Not Null"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:null,
			amount:2000,
			code:"updateCode",
			confirmationDate:new Date(2015,3,28)
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Adjustment Validation Amount Not Null"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,27),
			amount:null,
			code:"updateCode",
			confirmationDate:new Date(2015,3,28)
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Adjustment Validation Code Not Null"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		and:'setting data for Update'
		def cashBankAdjustment2 = [
			id: cashBankAdjustment.id,
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,27),
			amount:2000,
			code:"",
			confirmationDate:new Date(2015,3,28)
		]

		when:'updateObject is called'
		cashBankAdjustment = cashBankAdjustmentService.updateObject(cashBankAdjustment2)

		then:'check has errors'
		cashBankAdjustment.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankAdjustment.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Home Assignment"() {
		setup: 'setting new Cash Bank Adjustment'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		when:'softDeleteObject is called'
		cashBankAdjustment = cashBankAdjustmentService.softDeletedObject(cashBankAdjustment)
		
		then:'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isDeleted == true
	}
	void "Test Confirm Home Assignment"() {
		setup: 'setting new Cash Bank Adjustment'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		when:'confirmObject is called'
		cashBankAdjustment = cashBankAdjustmentService.confirmObject(cashBankAdjustment)
		
		then:'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isConfirmed == true
	}
	void "Test unConfirmed Home Assignment"() {
		setup: 'setting new Cash Bank Adjustment'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Adjustment'
		def cashBankAdjustment = [
			cashBank:cashBank,
			adjustmentDate:new Date (2015,3,26),
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankAdjustment= cashBankAdjustmentService.createObject(cashBankAdjustment)

		when:'unConfirmObject is called'
		cashBankAdjustment = cashBankAdjustmentService.unConfirmObject(cashBankAdjustment)
		
		then:'check has errors'
		cashBankAdjustment.hasErrors() == false
		cashBankAdjustment.isConfirmed == false
	}



}
