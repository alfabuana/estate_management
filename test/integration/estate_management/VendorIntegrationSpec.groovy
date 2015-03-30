package estate_management

import grails.test.spock.IntegrationSpec

class VendorIntegrationSpec extends IntegrationSpec {
	def vendorService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create new Vendor"() {
		setup:'setting new vendor'
		Vendor vendor = new Vendor()
		vendor.name = "newName"
		vendor.description = "newDescription"
		vendor.telephone = "087893213"
		vendor.fax = "0218309123"
		vendor.email = "email@gmail.com"
		
		when:'createObject is called'
		vendor = vendorService.createObject(vendor)
		
		then:'check has errors'
		vendor.hasErrors() == false
		vendor.isDeleted == false
		
	}
	void "Test Vendor Validation Name Not Null"() {
		setup:'setting new vendor'
		Vendor vendor = new Vendor()
		vendor.name = ""
		vendor.description = "newDescription"
		vendor.telephone = "087893213"
		vendor.fax = "0218309123"
		vendor.email = "email@gmail.com"
		 
		when:'createObject is called'
		vendor = vendorService.createObject(vendor)
		
		then:'check has errors'
		vendor.hasErrors() == true
		println vendor.errors.getFieldError().defaultMessage
	}
	void "Test Update new Vendor"() {
		setup:'setting new Vendor'
		Vendor vendor = new Vendor()
		vendor.name = "newName"
		vendor.description = "newDescription"
		vendor.telephone = "087893213"
		vendor.fax = "0218309123"
		vendor.email = "email@gmail.com"
		vendor = vendorService.createObject(vendor)
		
		and: 'setting data for Update'
		Vendor vendor2 = new Vendor()
		vendor2.id = vendor.id
		vendor2.name = "updateName"
		vendor2.description = "newDescription"
		vendor2.telephone = "087893213"
		vendor2.fax = "0218309123"
		vendor2.email = "email@gmail.com"
		 
		when:'updateObject is called'
		vendor = vendorService.updateObject(vendor2)
		
		then:'check has errors'
		vendor.hasErrors() == false
		vendor.name == vendor2.name
		vendor.description == vendor2.description
		vendor.telephone == vendor2.telephone
		vendor.fax == vendor2.fax
		vendor.email == vendor2.email
	}
	void "Test Update Vendor Validation nama Not Null"() {
		setup:'setting new Vendor'
		Vendor vendor = new Vendor()
		vendor.name = "newName"
		vendor.description = "newDescription"
		vendor.telephone = "087893213"
		vendor.fax = "0218309123"
		vendor.email = "email@gmail.com"
		vendor = vendorService.createObject(vendor)
		
		and: 'setting data for Update'
		Vendor vendor2 = new Vendor()
		vendor2.id = vendor.id
		vendor2.name = ""
		vendor2.description = "updateDescription"
		vendor2.telephone = "089739123"
		vendor2.fax = "021830921"
		vendor2.email = "updateEmail@gmail.com"
		 
		when:'updateObject is called'
		vendor = vendorService.updateObject(vendor2)
		
		then:'check has errors'
		vendor.hasErrors() == true
		println "Validasi sukses dengan error message : " + vendor.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Vendor"() {
		setup:'setting new Vendor'
		Vendor vendor = new Vendor()
		vendor.name = "newName"
		vendor.description = "newDescription"
		vendor.telephone = "087893213"
		vendor.fax = "0218309123"
		vendor.email = "email@gmail.com"
		vendor = vendorService.createObject(vendor)
		
		when:'createObject is called'
		vendor = vendorService.softDeletedObject(vendor)
		
		then:'check has errors'
		vendor.hasErrors() == false
		vendor.isDeleted == true
	}

}
