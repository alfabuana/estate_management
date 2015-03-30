package estate_management

import grails.test.spock.IntegrationSpec

class MaintenanceIntegrationSpec extends IntegrationSpec {
	def maintenanceService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create new Maintenance"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = 1000
		maintenance.code = "newCode"

		when:'createObject is called'
		maintenance = maintenanceService.createObject(maintenance)

		then:'check has errors'
		maintenance.hasErrors() == false
		maintenance.isDeleted == false

	}
	void "Test Create Maintenance Validation description Not Null"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = ""
		maintenance.amount = 1000
		maintenance.code = "newCode"

		when:'createObject is called'
		maintenance = maintenanceService.createObject(maintenance)

		then:'check has errors'
		maintenance.hasErrors() == true
		println maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Create Maintenance Validation amount Not Null"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = null
		maintenance.code = "newCode"

		when:'createObject is called'
		maintenance = maintenanceService.createObject(maintenance)

		then:'check has errors'
		maintenance.hasErrors() == true
		println maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Create Maintenance Validation code Not Null"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = 1000
		maintenance.code = ""

		when:'createObject is called'
		maintenance = maintenanceService.createObject(maintenance)

		then:'check has errors'
		maintenance.hasErrors() == true
		println maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Update new Maintenance"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = 1000
		maintenance.code = "newCode"
		maintenance = maintenanceService.createObject(maintenance)
		
		and: 'setting data for Update'
		Maintenance maintenance2 = new Maintenance()
		maintenance2.id = maintenance.id
		maintenance2.description = "updateDescription"
		maintenance2.amount = 2000
		maintenance2.code = "updateCode"
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == false
		maintenance.description == maintenance2.description
		maintenance.amount == maintenance2.amount
		maintenance.code == maintenance2.code
	}
	void "Test Update Maintenance Validation description Not Null"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = 1000
		maintenance.code = "newCode"
		maintenance = maintenanceService.createObject(maintenance)
		
		and: 'setting data for Update'
		Maintenance maintenance2 = new Maintenance()
		maintenance2.id = maintenance.id
		maintenance2.description = ""
		maintenance2.amount = 2000
		maintenance2.code = "updateCode"
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Update Maintenance Validation amount Not Null"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = 1000
		maintenance.code = "newCode"
		maintenance = maintenanceService.createObject(maintenance)
		
		and: 'setting data for Update'
		Maintenance maintenance2 = new Maintenance()
		maintenance2.id = maintenance.id
		maintenance2.description = "updateDescription"
		maintenance2.amount = null
		maintenance2.code = "updateCode"
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Update Maintenance Validation code Not Null"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = 1000
		maintenance.code = "newCode"
		maintenance = maintenanceService.createObject(maintenance)
		
		and: 'setting data for Update'
		Maintenance maintenance2 = new Maintenance()
		maintenance2.id = maintenance.id
		maintenance2.description = "updateDescription"
		maintenance2.amount = 2000
		maintenance2.code = ""
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Maintenance"() {
		setup:'setting new maintenance'
		Maintenance maintenance = new Maintenance()
		maintenance.description = "newDescription"
		maintenance.amount = 1000
		maintenance.code = "newCode"
		maintenance = maintenanceService.createObject(maintenance)
		
		when:'createObject is called'
		maintenance = maintenanceService.softDeletedObject(maintenance)
		
		then:'check has errors'
		maintenance.hasErrors() == false
		maintenance.isDeleted == true
	}
}
