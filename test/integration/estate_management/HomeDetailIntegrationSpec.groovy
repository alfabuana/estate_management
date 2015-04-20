package estate_management

import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class HomeDetailIntegrationSpec extends IntegrationSpec {
	def homeService
	def userService
	def homeDetailService
	
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
			createdBy:shiroUser
			]
		home = homeService.createObject(home)
	}

	def cleanup() {
	}

	void "Test Create New Home Detail"() {
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:home,
			username:shiroUser
		]

		when : 'createObject is called'
		homeDetail = homeDetailService.createObject(homeDetail)

		then: 'check has errors'
		homeDetail.hasErrors() == false
		homeDetail.isDeleted == false
	}
	void "Test Create Home Detail Validation Home Not Null"(){
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:"",
			username:shiroUser
		]

		when:'createObject is called'
		homeDetail = homeDetailService.createObject(homeDetail)

		then:'check has errors'
		homeDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Home Detail Validation Username Not Null"(){
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:home,
			username:""
		]

		when:'createObject is called'
		homeDetail = homeDetailService.createObject(homeDetail)

		then:'check has errors'
		homeDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update New Home Detail"(){
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:home,
			username:shiroUser
		]
		homeDetail= homeDetailService.createObject(homeDetail)

		and:'setting data for Update'
		def homeDetail2 = [
			id: homeDetail.id,
			home:home,
			username:shiroUser
		]

		when:'updateObject is called'
		homeDetail = homeDetailService.updateObject(homeDetail2)

		then:'check has errors'
		homeDetail.hasErrors() == false
		homeDetail.home == homeDetail.home
		homeDetail.username == homeDetail.username
	}
	void "Test Update Validation Home Not Null"(){
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:home,
			username:shiroUser
		]
		homeDetail= homeDetailService.createObject(homeDetail)

		and:'setting data for Update'
		def homeDetail2 = [
			id: homeDetail.id,
			home:null,
			username:shiroUser
		]

		when:'updateObject is called'
		homeDetail = homeDetailService.updateObject(homeDetail2)

		then:'check has errors'
		homeDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update Validation Username Not Null"(){
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:home,
			username:shiroUser
		]
		homeDetail= homeDetailService.createObject(homeDetail)

		and:'setting data for Update'
		def homeDetail2 = [
			id: homeDetail.id,
			home:home,
			username:null
		]

		when:'updateObject is called'
		homeDetail = homeDetailService.updateObject(homeDetail2)

		then:'check has errors'
		homeDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeDetail.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Home Detail"() {
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:home,
			username:shiroUser
		]
		homeDetail = homeDetailService.createObject(homeDetail)

		when:'createObject is called'
		homeDetail = homeDetailService.softDeletedObject(homeDetail)
		
		then:'check has errors'
		homeDetail.hasErrors() == false
		homeDetail.isDeleted == true
	}
	void "Test SoftDelete Home Detail Validation Is Deleted"() {
		setup: 'setting new Home Detail'
		def homeDetail = [
			home:home,
			username:shiroUser
		]
		homeDetail = homeDetailService.createObject(homeDetail)
		homeDetail = homeDetailService.softDeletedObject(homeDetail)

		when:'createObject is called'
		homeDetail = homeDetailService.softDeletedObject(homeDetail)
		
		then:'check has errors'
		homeDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + homeDetail.errors.getAllErrors().defaultMessage
	}




}
