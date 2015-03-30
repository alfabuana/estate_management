package estate_management

import grails.test.spock.IntegrationSpec

class CashBankIntegrationSpec extends IntegrationSpec {
	def cashBankService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Cash Bank"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		
		when:'createObject is called'
		cashBank = cashBankService.createObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == false
		cashBank.isDeleted == false
    }
	void "Test Create Cash Bank Validation Name Not Null"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = ""
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		 
		when:'createObject is called'
		cashBank = cashBankService.createObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Validation Amount Not Null"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = null
		cashBank.isBank = true
		 
		when:'createObject is called'
		cashBank = cashBankService.createObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Create Cash Bank Validation isBank Not Null"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = null
		 
		when:'createObject is called'
		cashBank = cashBankService.createObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Update New Cash Bank"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		
		and: 'setting data for Update'
		CashBank cashBank2 = new CashBank()
		cashBank2.id = cashBank.id
		cashBank2.name = "updateName"
		cashBank2.description = "updateDescription"
		cashBank2.amount = 2000
		cashBank2.isBank = false
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == false
		cashBank.name == cashBank2.name
		cashBank.description == cashBank2.description
		cashBank.amount == cashBank2.amount
		cashBank.isBank == cashBank2.isBank
	}
	void "Test Update Cash Bank Validation Name Not Null"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
				
		and: 'setting data for Update'
		CashBank cashBank2 = new CashBank()
		cashBank2.id = cashBank.id
		cashBank2.name = ""
		cashBank2.description = "updateDescription"
		cashBank2.amount = 2000
		cashBank2.isBank = false
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Update Cash Bank Validation Amount Not Null"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
				
		and: 'setting data for Update'
		CashBank cashBank2 = new CashBank()
		cashBank2.id = cashBank.id
		cashBank2.name = ""
		cashBank2.description = "updateDescription"
		cashBank2.amount = null
		cashBank2.isBank = false
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getFieldError().defaultMessage
	}
	void "Test Update Cash Bank Validation isBank Not Null"() {
		setup:'setting new Cash Bank'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newDescription"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
				
		and: 'setting data for Update'
		CashBank cashBank2 = new CashBank()
		cashBank2.id = cashBank.id
		cashBank2.name = ""
		cashBank2.description = "updateDescription"
		cashBank2.amount = 2000
		cashBank2.isBank = null
		 
		when:'updateObject is called'
		cashBank = cashBankService.updateObject(cashBank2)
		
		then:'check has errors'
		cashBank.hasErrors() == true
		println "Validasi sukses dengan error message : " + cashBank.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete CashBank"() {
		setup:'setting new Home'
		CashBank cashBank = new CashBank()
		cashBank.name = "newName"
		cashBank.description = "newAddress"
		cashBank.amount = 1000
		cashBank.isBank = true
		cashBank = cashBankService.createObject(cashBank)
		
		when:'softDeleteObject is called'
		cashBank = cashBankService.softDeletedObject(cashBank)
		
		then:'check has errors'
		cashBank.hasErrors() == false
		cashBank.isDeleted == true
	}
	
}
