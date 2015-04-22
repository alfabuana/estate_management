package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class MaintenanceDetailIntegrationSpec extends IntegrationSpec {
	def maintenanceService
	def userService
	def maintenanceDetailService
	
	@Shared
	def shiroUser
	def home
	def maintenance


    def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		maintenance = [
			amount:20000,
			maintenanceDate:new Date(2015,4,21)
			]
		maintenance = maintenanceService.createObject(maintenance)
    }

    def cleanup() {
    }

    void "test create maintenance detail"() {
		setup:'setting new maintenance detail'
		def maintenanceDetail = [
			maintenanceId:maintenance.id,
			username:shiroUser.username,
			user:shiroUser
			]
		
		when:'create is called'
		maintenanceDetail = maintenanceDetailService.createObject(maintenanceDetail)
		println maintenanceDetail as JSON
		
		then:'check error'
		maintenanceDetail.hasErrors() == false
		maintenanceDetail.isDeleted == false
    }
	
	void "test create maintenance detail validation username not null"() {
		setup:'setting new maintenance detail'
		def maintenanceDetail = [
			maintenanceId:maintenance.id,
			username:shiroUser.username,
			user:null
			]
		
		when:'create is called'
		maintenanceDetail = maintenanceDetailService.createObject(maintenanceDetail)
		
		then:'check error'
		maintenanceDetail.hasErrors() == true
		println "validasi sukses dengan error message :" + maintenanceDetail.errors.getFieldError().defaultMessage
	}
	
	void "test update maintenance detail"() {
		setup:'setting new maintenance detail'
		def maintenanceDetail = [
			maintenanceId:maintenance.id,
			username:shiroUser.username,
			user:shiroUser
			]
		maintenanceDetail = maintenanceDetailService.createObject(maintenanceDetail)
		
		def maintenanceDetail2 = [
			id:maintenanceDetail.id,
			maintenanceId:maintenance.id,
			username:shiroUser.username,
			user:shiroUser
			]
		when:'update is called'
		maintenanceDetail = maintenanceDetailService.updateObject(maintenanceDetail2)
		
		then:'check error'
		maintenanceDetail.hasErrors() == false
		maintenanceDetail.isDeleted == false
		maintenanceDetail.maintenanceId == maintenanceDetail.maintenanceId
		maintenanceDetail.user == maintenanceDetail.user
	}
	
	void "test update maintenance detail validation username not null"() {
		setup:'setting new maintenance detail'
		def maintenanceDetail = [
			maintenanceId:maintenance.id,
			username:shiroUser.username,
			user:shiroUser
			]
		maintenanceDetail = maintenanceDetailService.createObject(maintenanceDetail)
		
		def maintenanceDetail2 = [
			id:maintenanceDetail.id,
			maintenanceId:maintenance.id,
			username:shiroUser.username,
			user:null
			]
		when:'update is called'
		maintenanceDetail = maintenanceDetailService.updateObject(maintenanceDetail2)
		
		then:'check error'
		maintenanceDetail.hasErrors() == true
		println "validasi sukses dengan error message :" + maintenanceDetail.errors.getFieldError().defaultMessage
	}
	
	void "test soft delete maintenance detail"() {
		setup:'setting new maintenance detail'
		def maintenanceDetail = [
			maintenanceId:maintenance.id,
			username:shiroUser.username,
			user:shiroUser
			]
		maintenanceDetail = maintenanceDetailService.createObject(maintenanceDetail)
		
		when:'soft delete is called'
		maintenanceDetail = maintenanceDetailService.softDeletedObject(maintenanceDetail)
		
		then:'check error'
		maintenanceDetail.hasErrors() == false
		maintenanceDetail.isDeleted == true
	}
}
