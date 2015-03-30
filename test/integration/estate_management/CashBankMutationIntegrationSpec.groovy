package estate_management

import grails.test.spock.IntegrationSpec

class CashBankMutationIntegrationSpec extends IntegrationSpec {
	def cashBankService
	def cashBankMutationService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Cash Bank Mutation"() {
		setup:'setting new Cash Bank Mutation'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when: 'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then: 'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.isDeleted == false

    }
	void "Test Create Cash Bank Mutation Validation source Cash bank Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:null,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)		]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Mutation Validation target Cash bank Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:null,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)		]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Mutation Validation Amount Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:null,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)		]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Mutation Validation code Not Null"(){
		setup: 'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"",
			confirmationDate:new Date(2015,3,27)		
			]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Update Cash Bank Mutation"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.sourceCashBank == cashBankMutation.sourceCashBank
		cashBankMutation.targetCashBank == cashBankMutation.targetCashBank
		cashBankMutation.amount == cashBankMutation.amount
		cashBankMutation.code == cashBankMutation.code
		cashBankMutation.confirmationDate == cashBankMutation.confirmationDate
	}
	void "Test Update CashBank Mutation Validation source cash Bank Not Null"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:null,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Mutation Validation target cash Bank Not Null"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:null,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Mutation Validation Amount Not Null"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:null,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}

	void "Test Update CashBank Mutation Validation Code Not Null"(){
		setup: 'setting new CashBank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"",
			confirmationDate:new Date(2015,3,27)
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Cash Bank Mutation"() {
		setup: 'setting new Cash Bank Mutation'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		when:'softDeleteObject is called'
		cashBankMutation = cashBankMutationService.softDeletedObject(cashBankMutation)
		
		then:'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.isDeleted == true
	}

	void "Test Confirm Cash Bank Mutation"() {
		setup: 'setting new Cash Bank Mutation'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		when:'confirmObject is called'
		cashBankMutation = cashBankMutationService.confirmObject(cashBankMutation)
		
		then:'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.isConfirmed == true
	}
	void "Test unConfirm Cash Bank Mutation"() {
		setup: 'setting new Cash Bank Mutation'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		println cashBank.id

		and: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
			code:"newCode",
			confirmationDate:new Date(2015,3,27)
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		when:'softDeleteObject is called'
		cashBankMutation = cashBankMutationService.unConfirmObject(cashBankMutation)
		
		then:'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.isConfirmed == false
	}
}
