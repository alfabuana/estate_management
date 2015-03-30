package estate_management

import grails.test.spock.IntegrationSpec

class HomeIntegrationSpec extends IntegrationSpec {
	def homeService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Home"() {
		setup:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		
		when:'createObject is called'
		home = homeService.createObject(home)
		
		then:'check has errors'
		home.hasErrors() == false
		home.isDeleted == false
		
    }
	void "Test Home Validation Name Not Null"() {
		setup:'setting new Home'
		Home home = new Home()
		home.name = ""
		home.address = "newAddress"
		 
		when:'createObject is called'
		home = homeService.createObject(home)
		
		then:'check has errors'
		home.hasErrors() == true
		println home.errors.getFieldError().defaultMessage
	}
	void "Test Home Validation Address Not Null"() {
		setup:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = ""
		 
		when:'createObject is called'
		home = homeService.createObject(home)
		
		then:'check has errors'
		home.hasErrors() == true
		println home.errors.getFieldError().defaultMessage
	}
	void "Test Update new Home"() {
		setup:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		
		and: 'setting data for Update'
		Home home2 = new Home()
		home2.id = home.id
		home2.name = "updateName"
		home2.address = "updateAddress"
		 
		when:'updateObject is called'
		home = homeService.updateObject(home2)
		
		then:'check has errors'
		home.hasErrors() == false
		home.name == home2.name
		home.address == home2.address
	}
	void "Test Update Validation nama Not Null"() {
		setup:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		
		and: 'setting data for Update'
		Home home2 = new Home()
		home2.id = home.id
		home2.name = ""
		home2.address = "updateAddress"
		 
		when:'updateObject is called'
		home = homeService.updateObject(home2)
		
		then:'check has errors'
		home.hasErrors() == true
		println "Validasi sukses dengan error message : " + home.errors.getFieldError().defaultMessage
	}
	void "Test Update Validation address Not Null"() {
		setup:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		
		and: 'setting data for Update'
		Home home2 = new Home()
		home2.id = home.id
		home2.name = "updateName"
		home2.address = ""
		 
		when:'updateObject is called'
		home = homeService.updateObject(home2)
		
		then:'check has errors'
		home.hasErrors() == true
		println "Validasi sukses dengan error message : " + home.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Home"() {
		setup:'setting new Home'
		Home home = new Home()
		home.name = "newName"
		home.address = "newAddress"
		home = homeService.createObject(home)
		
		when:'createObject is called'
		home = homeService.softDeletedObject(home)
		
		then:'check has errors'
		home.hasErrors() == false
		home.isDeleted == true
	}


}
