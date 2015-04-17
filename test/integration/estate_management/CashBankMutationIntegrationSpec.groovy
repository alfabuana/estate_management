package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class CashBankMutationIntegrationSpec extends IntegrationSpec {
	def cashBankService
	def cashBankMutationService
	def userService

	@Shared
	def shiroUser
	def cashBank
	def cashBank2

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)

		cashBank = [
			name: "newName",
			description: "newDescription",
			amount:1000000,
			isBank: true,
			createdBy:shiroUser
			]
		
		cashBank = cashBankService.createObject(cashBank)
		
		cashBank2 = [
			name: "updateName",
			description: "newDescription",
			amount:1000000,
			isBank: true,
			createdBy:shiroUser
			]
		cashBank2 = cashBankService.createObject(cashBank2)
	}

	def cleanup() {
	}

	void "Test Create New Cash Bank Mutation"() {
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:1000,
		]

		when: 'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then: 'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.id != null
		cashBankMutation.isDeleted == false

	}
	void "Test Create Cash Bank Mutation Validation source Cash bank Not Null"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:null,
			targetCashBank:cashBank,
			amount:1000,
		]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Mutation Validation target Cash bank Not Null"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:null,
			amount:1000,
		]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Mutation Validation Amount Not Null"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:null,
		]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Mutation Validation Source And Target Cannot Same"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
		]

		when:'createObject is called'
		cashBankMutation = cashBankMutationService.createObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Update Cash Bank Mutation"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:1000,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:"2000",
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.sourceCashBank == cashBankMutation.sourceCashBank
		cashBankMutation.targetCashBank == cashBankMutation.targetCashBank
		cashBankMutation.amount == cashBankMutation.amount
	}
	void "Test Update CashBank Mutation Validation source cash Bank Not Null"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:1000,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)
		println cashBankMutation as JSON

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:null,
			targetCashBank:cashBank,
			amount:"2000",
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Mutation Validation target cash Bank Not Null"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:1000,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:null,
			amount:"1000",
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test Update CashBank Mutation Validation Amount Not Null"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:1000,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:null,
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}

	void "Test Update CashBank Mutation Validation Source And Target Cannot Same"(){
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:1000,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		and:'setting data for Update'
		def cashBankMutation2 = [
			id: cashBankMutation.id,
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:"1000",
		]

		when:'updateObject is called'
		cashBankMutation = cashBankMutationService.updateObject(cashBankMutation2)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Cash Bank Mutation"() {
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:1000,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		when:'softDeleteObject is called'
		cashBankMutation = cashBankMutationService.softDeletedObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.isDeleted == true
	}
	void "Test SoftDelete Cash Bank Mutation Validation Is Confirmed"() {
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank2,
			amount:500000,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		def confirm = [
			id:cashBankMutation.id,
			username:shiroUser
			]
		cashBankMutation = cashBankMutationService.confirmObject(confirm)
		
		when:'softDeleteObject is called'
		cashBankMutation = cashBankMutationService.softDeletedObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBankMutation.errors.getAllErrors().defaultMessage
	}

	void "Test Confirm Cash Bank Mutation"() {
		setup: 'setting new Cash Bank Mutation'
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:1000,
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
		def cashBankMutation = [
			sourceCashBank:cashBank,
			targetCashBank:cashBank,
			amount:500,
		]
		cashBankMutation= cashBankMutationService.createObject(cashBankMutation)

		when:'softDeleteObject is called'
		cashBankMutation = cashBankMutationService.unConfirmObject(cashBankMutation)

		then:'check has errors'
		cashBankMutation.hasErrors() == false
		cashBankMutation.isConfirmed == false
	}
}
