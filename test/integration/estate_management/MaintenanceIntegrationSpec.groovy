package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class MaintenanceIntegrationSpec extends IntegrationSpec {
	def maintenanceService
	def userService
	def homeService
	
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

	void "Test Create new Maintenance"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]

		when:'createObject is called'
		maintenance = maintenanceService.createObject(maintenance)

		then:'check has errors'
		maintenance.hasErrors() == false
		maintenance.isDeleted == false

	}
	void "Test Create Maintenance Validation amount Not Null"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:null,
			maintenanceDate:new Date(2015,4,21)
			]

		when:'createObject is called'
		maintenance = maintenanceService.createObject(maintenance)

		then:'check has errors'
		maintenance.hasErrors() == true
		println maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Create Maintenance Validation Maintenance Date Not Null"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:null
			]

		when:'createObject is called'
		maintenance = maintenanceService.createObject(maintenance)

		then:'check has errors'
		maintenance.hasErrors() == true
		println maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Update new Maintenance"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
		
		and: 'setting data for Update'
		def maintenance2 = [
			id:maintenance.id,
			amount:"30000",
			maintenanceDate:new Date(2015,4,21)
			]
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == false
		maintenance.amount == maintenance.amount
		maintenance.maintenanceDate == maintenance.maintenanceDate
	}
	void "Test Update Maintenance Validation amount Not Null"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
		
		and: 'setting data for Update'
		def maintenance2 = [
			id:maintenance.id,
			amount:null,
			maintenanceDate:new Date(2015,4,21)
			]
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Update Maintenance Validation maintenanceDate Not Null"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
		
		and: 'setting data for Update'
		def maintenance2 = [
			id:maintenance.id,
			amount:"20000",
			maintenanceDate:null
			]
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getFieldError().defaultMessage
	}
	void "Test Update Maintenance Validation Is Confirmed"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
		def confirm = [
			id:maintenance.id,
			username:shiroUser.username
			]
		maintenance = maintenanceService.confirmObject(confirm)
		
		and: 'setting data for Update'
		def maintenance2 = [
			id:maintenance.id,
			amount:"20000",
			maintenanceDate:new Date (2015,4,21)
			]
		 
		when:'updateObject is called'
		maintenance = maintenanceService.updateObject(maintenance2)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Maintenance"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
		
		when:'createObject is called'
		maintenance = maintenanceService.softDeletedObject(maintenance)
		
		then:'check has errors'
		maintenance.hasErrors() == false
		maintenance.isDeleted == true
	}
	void "Test SoftDelete Maintenance Validation Is Confirmed"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
		def confirm = [
			id:maintenance.id,
			username:shiroUser.username
			]
		maintenance = maintenanceService.confirmObject(confirm)
		
		when:'createObject is called'
		maintenance = maintenanceService.softDeletedObject(maintenance)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Maintenance Validation Is Deleted"() {
		setup:'setting new maintenance'
		def maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
		maintenance = maintenanceService.softDeletedObject(maintenance)
		
		when:'createObject is called'
		maintenance = maintenanceService.softDeletedObject(maintenance)
		
		then:'check has errors'
		maintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + maintenance.errors.getAllErrors().defaultMessage
	}
	void "Test Confirm Maintenance"() {
		setup:'setting new maintenance'
		def newMaintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		newMaintenance = maintenanceService.createObject(newMaintenance)
		def home = [
			name:"home1",
			address:"12345"]
		home = homeService.createObject(home)
		
		def home2 = [
			name:"home2",
			address:"123456"]
		home = homeService.createObject(home2)
		
		def confirm = [
			id:newMaintenance.id,
			username:shiroUser.username
			]
		
		
		when:'confirm is called'
		newMaintenance= maintenanceService.confirmObject(confirm)
		def invoice =[ Invoice.findAll{
					maintenance.id == newMaintenance.id 
		}]
		
		then:'check has errors'
		newMaintenance.hasErrors() == false
		newMaintenance.isConfirmed == true
		invoice.size() > 0
	}
	void "Test Confirm Maintenance Validation Is Confirmed"() {
		setup:'setting new maintenance'
		def newMaintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		newMaintenance = maintenanceService.createObject(newMaintenance)
		def home = [
			name:"home1",
			address:"12345"]
		home = homeService.createObject(home)
		
		def home2 = [
			name:"home2",
			address:"123456"]
		home = homeService.createObject(home2)
		
		def confirm = [
			id:newMaintenance.id,
			username:shiroUser.username
			]
		newMaintenance= maintenanceService.confirmObject(confirm)
		def invoice =[ Invoice.findAll{
					maintenance.id == newMaintenance.id
		}]
		
		
		when:'confirm is called'
		newMaintenance= maintenanceService.confirmObject(confirm)
		
		
		then:'check has errors'
		newMaintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + newMaintenance.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm Maintenance"() {
		setup:'setting new maintenance'
		def newMaintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		newMaintenance = maintenanceService.createObject(newMaintenance)
		def home = [
			name:"home1",
			address:"12345"]
		home = homeService.createObject(home)
		
		def home2 = [
			name:"home2",
			address:"123456"]
		home = homeService.createObject(home2)
		
		def confirm = [
			id:newMaintenance.id,
			username:shiroUser.username
			]
		newMaintenance= maintenanceService.confirmObject(confirm)
		def invoice =[ Invoice.findAll{
					maintenance.id == newMaintenance.id
		}]
		
		when:'confirm is called'
		newMaintenance= maintenanceService.unConfirmObject(confirm)
		
		then:'check has errors'
		newMaintenance.hasErrors() == false
		newMaintenance.isConfirmed == false
	}
	void "Test unConfirm Maintenance validation Is Not Confirmed"() {
		setup:'setting new maintenance'
		def newMaintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		newMaintenance = maintenanceService.createObject(newMaintenance)
		def home = [
			name:"home1",
			address:"12345"]
		home = homeService.createObject(home)
		
		def home2 = [
			name:"home2",
			address:"123456"]
		home = homeService.createObject(home2)
		
		when:'confirm is called'
		newMaintenance= maintenanceService.unConfirmObject(newMaintenance)
		
		then:'check has errors'
		newMaintenance.hasErrors() == true
		println "Validasi sukses dengan error message : " + newMaintenance.errors.getAllErrors().defaultMessage
	}
}
