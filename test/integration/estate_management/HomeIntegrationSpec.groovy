package estate_management

import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class HomeIntegrationSpec extends IntegrationSpec {
	def homeService
	def userService
	
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

    void "Test Create New Home"() {
		setup:'setting new Home'
		def home = [
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser
			]
		
		when:'createObject is called'
		home = homeService.createObject(home)
		
		then:'check has errors'
		home.hasErrors() == false
		home.isDeleted == false
		
    }
	void "Test Home Validation Name Not Null"() {
		setup:'setting new Home'
		def home = [
			name:"",
			address:"newAddress",
			createdBy:shiroUser
			]
		 
		when:'createObject is called'
		home = homeService.createObject(home)
		
		then:'check has errors'
		home.hasErrors() == true
		println home.errors.getFieldError().defaultMessage
	}
	void "Test Home Validation Address Not Null"() {
		setup:'setting new Home'
		def home = [
			name:"newName",
			address:"",
			createdBy:shiroUser
			]
		 
		when:'createObject is called'
		home = homeService.createObject(home)
		
		then:'check has errors'
		home.hasErrors() == true
		println home.errors.getFieldError().defaultMessage
	}
	void "Test Update new Home"() {
		setup:'setting new Home'
		def home = [
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser
			]
		home = homeService.createObject(home)
		
		and: 'setting data for Update'
		def home2 = [
			id:home.id,
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser
			]
		 
		when:'updateObject is called'
		home = homeService.updateObject(home2)
		
		then:'check has errors'
		home.hasErrors() == false
		home.name == home2.name
		home.address == home2.address
	}
	void "Test Update Validation nama Not Null"() {
		setup:'setting new Home'
		def home = [
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser
			]
		home = homeService.createObject(home)
		
		and: 'setting data for Update'
		def home2 = [
			id:home.id,
			name:"",
			address:"newAddress",
			createdBy:shiroUser
			]
		 
		when:'updateObject is called'
		home = homeService.updateObject(home2)
		
		then:'check has errors'
		home.hasErrors() == true
		println "Validasi sukses dengan error message : " + home.errors.getFieldError().defaultMessage
	}
	void "Test Update Validation address Not Null"() {
		setup:'setting new Home'
		def home = [
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser
			]
		home = homeService.createObject(home)
		
		and: 'setting data for Update'
		def home2 = [
			id:home.id,
			name:"newName",
			address:"",
			createdBy:shiroUser
			]
		 
		when:'updateObject is called'
		home = homeService.updateObject(home2)
		
		then:'check has errors'
		home.hasErrors() == true
		println "Validasi sukses dengan error message : " + home.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Home"() {
		setup:'setting new Home'
		def home = [
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser
			]
		home = homeService.createObject(home)
		
		when:'createObject is called'
		home = homeService.softDeletedObject(home)
		
		then:'check has errors'
		home.hasErrors() == false
		home.isDeleted == true
	}
	void "Test SoftDelete Home Validation Is Deleted"() {
		setup:'setting new Home'
		def home = [
			name:"newName",
			address:"newAddress",
			createdBy:shiroUser
			]
		home = homeService.createObject(home)
		home = homeService.softDeletedObject(home)
		
		when:'createObject is called'
		home = homeService.softDeletedObject(home)
		
		then:'check has errors'
		home.hasErrors() == true
		println "Validasi sukses dengan error message : " + home.errors.getAllErrors().defaultMessage
	}


}
