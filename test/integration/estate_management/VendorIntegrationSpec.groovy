package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class VendorIntegrationSpec extends IntegrationSpec {
	def vendorService
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

	void "Test Create new Vendor"() {
		setup:'setting new vendor'
		def vendor = [
			name:"vendor1",
			username:shiroUser.username
		]

		when:'createObject is called'
		vendor = vendorService.createObject(vendor)

		then:'check has errors'
		vendor.hasErrors() == false
		vendor.isDeleted == false

	}
	void "Test Vendor Validation Name Not Null"() {
		setup:'setting new vendor'
		def vendor = [
			name:"",
			username:shiroUser.username
		]


		when:'createObject is called'
		vendor = vendorService.createObject(vendor)

		then:'check has errors'
		vendor.hasErrors() == true
		println vendor.errors.getFieldError().defaultMessage
	}
	void "Test Update new Vendor"() {
		setup:'setting new Vendor'
		def vendor = [
			name:"vendor1",
			username:shiroUser.username
		]

		vendor = vendorService.createObject(vendor)

		and: 'setting data for Update'
		def vendor2 = [
			id:vendor.id,
			name:"vendor1",
			username:shiroUser.username
		]


		when:'updateObject is called'
		vendor = vendorService.updateObject(vendor2)

		then:'check has errors'
		vendor.hasErrors() == false
		vendor.name == vendor2.name
	}
	void "Test Update Vendor Validation nama Not Null"() {
		setup:'setting new Vendor'
		def vendor = [
			name:"vendor1",
			username:shiroUser.username
		]
		vendor = vendorService.createObject(vendor)

		and: 'setting data for Update'
		def vendor2 = [
			id:vendor.id,
			name:"",
			username:shiroUser.username
		]

		when:'updateObject is called'
		vendor = vendorService.updateObject(vendor2)

		then:'check has errors'
		vendor.hasErrors() == true
		println "Validasi sukses dengan error message : " + vendor.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Vendor"() {
		setup:'setting new Vendor'
		def vendor = [
			name:"vendor1",
			username:shiroUser.username
		]
		vendor = vendorService.createObject(vendor)

		when:'createObject is called'
		vendor = vendorService.softDeletedObject(vendor)

		then:'check has errors'
		vendor.hasErrors() == false
		vendor.isDeleted == true
	}

	void "Test SoftDelete Vendor Validation Is Deleted"() {
		setup:'setting new Vendor'
		def vendor = [
			name:"vendor1",
			username:shiroUser.username
		]
		vendor = vendorService.createObject(vendor)
		vendor = vendorService.softDeletedObject(vendor)

		when:'createObject is called'
		vendor = vendorService.softDeletedObject(vendor)

		then:'check has errors'
		vendor.hasErrors() == true
		println vendor.errors.getAllErrors().defaultMessage
	}

}
