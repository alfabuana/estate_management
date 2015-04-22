package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class ParkingRegistrationIntegrationSpec extends IntegrationSpec {
	def homeService
	def userService
	def parkingRegistrationService

	@Shared
	def shiroUser
	def home

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)

		home = [
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser.username
		]
		home = homeService.createObject(home)
	}

	def cleanup() {
	}

	void "test create parking registration"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]

		when:'createObject is called'
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == false
		parkingRegistration.isDeleted == false
	}

	void "test create parking registration validation home not null"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:null,
			carNumber:"b1111sad",
			username:shiroUser.username
		]

		when:'createObject is called'
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getFieldError().defaultMessage
	}

	void "test create parking registration validation car number not null"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"",
			username:shiroUser.username
		]

		when:'createObject is called'
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getFieldError().defaultMessage
	}

	void "test update parking registration"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def parkingRegistration2 = [
			id:parkingRegistration.id,
			home:home,
			carNumber:"b2222o",
			username:shiroUser.username
		]

		when:'updateObject is called'
		parkingRegistration = parkingRegistrationService.updateObject(parkingRegistration2)

		then:'check error'
		parkingRegistration.hasErrors() == false
		parkingRegistration.isDeleted == false
		parkingRegistration.home == parkingRegistration.home
		parkingRegistration.carNumber == parkingRegistration.carNumber
	}

	void "test update parking registration validation home not null"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def parkingRegistration2 = [
			id:parkingRegistration.id,
			home:null,
			carNumber:"b2222o",
			username:shiroUser.username
		]

		when:'updateObject is called'
		parkingRegistration = parkingRegistrationService.updateObject(parkingRegistration2)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getFieldError().defaultMessage
	}

	void "test update parking registration validation car number not null"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def parkingRegistration2 = [
			id:parkingRegistration.id,
			home:home,
			carNumber:"",
			username:shiroUser.username
		]

		when:'updateObject is called'
		parkingRegistration = parkingRegistrationService.updateObject(parkingRegistration2)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getFieldError().defaultMessage
	}

	void "test update parking registration validation is confirmed"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def confirm = [
			id:parkingRegistration.id,
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.confirmObject(confirm)

		def parkingRegistration2 = [
			id:parkingRegistration.id,
			home:home,
			carNumber:"b22220",
			username:shiroUser.username
		]

		when:'updateObject is called'
		parkingRegistration = parkingRegistrationService.updateObject(parkingRegistration2)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getAllErrors().defaultMessage
	}

	void "test softdelete parking registration"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		when:'softDeletedObject is called'
		parkingRegistration = parkingRegistrationService.softDeletedObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == false
		parkingRegistration.isDeleted == true
	}

	void "test softdelete parking registration validation is deleted"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)
		parkingRegistration = parkingRegistrationService.softDeletedObject(parkingRegistration)

		when:'softDeletedObject is called'
		parkingRegistration = parkingRegistrationService.softDeletedObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getAllErrors().defaultMessage
	}

	void "test softdelete parking registration validation is confirmed"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def confirm = [
			id:parkingRegistration.id,
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.confirmObject(confirm)

		when:'softDeletedObject is called'
		parkingRegistration = parkingRegistrationService.softDeletedObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getAllErrors().defaultMessage
	}

	void "test confirm parking registration"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def confirm = [
			id:parkingRegistration.id,
			username:shiroUser.username
		]

		when:'confirmObject is called'
		parkingRegistration = parkingRegistrationService.confirmObject(confirm)

		then:'check error'
		parkingRegistration.hasErrors() == false
		parkingRegistration.isConfirmed == true
	}

	void "test confirm parking registration validation is confirmed"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def confirm = [
			id:parkingRegistration.id,
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.confirmObject(confirm)

		when:'confirmObject is called'
		parkingRegistration = parkingRegistrationService.confirmObject(confirm)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getAllErrors().defaultMessage
	}

	void "test unconfirm parking registration"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def confirm = [
			id:parkingRegistration.id,
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.confirmObject(confirm)

		when:'confirmObject is called'
		parkingRegistration = parkingRegistrationService.unConfirmObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == false
		parkingRegistration.isConfirmed == false
	}

	void "test unconfirm parking registration validation is not confirmed"() {
		setup:'setting new parking registration'
		def parkingRegistration = [
			home:home.id,
			carNumber:"b1111sad",
			username:shiroUser.username
		]
		parkingRegistration = parkingRegistrationService.createObject(parkingRegistration)

		def confirm = [
			id:parkingRegistration.id,
			username:shiroUser.username
		]

		when:'unConfirmObject is called'
		parkingRegistration = parkingRegistrationService.unConfirmObject(parkingRegistration)

		then:'check error'
		parkingRegistration.hasErrors() == true
		println "validasi sukses dengan error message :" + parkingRegistration.errors.getAllErrors().defaultMessage
	}
}
