package estate_management

import grails.test.spock.IntegrationSpec

class UserIntegrationSpec extends IntegrationSpec {
	def userService

    def setup() {
    }

    def cleanup() {
    }

    void "test create username"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		
		when:'create is called'
		shiroUser = userService.createObject(shiroUser)
		
		then:'check error'
		shiroUser.hasErrors() == false
		shiroUser.isDeleted == false
    }
	
	void "test create username validation username not null"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"",
			passwordHash:"a1234"
			]
		
		when:'create is called'
		shiroUser = userService.createObject(shiroUser)
		
		then:'check error'
		shiroUser.hasErrors() == true
		println shiroUser.errors.getFieldError().defaultMessage
	}
	
	void "test create username validation passwordhash not null"() {
	setup:'setting new shiroUser'
	def shiroUser = [
		username:"name1",
		passwordHash:""
		]
	
	when:'create is called'
	shiroUser = userService.createObject(shiroUser)
	
	then:'check error'
	shiroUser.hasErrors() == true
	println shiroUser.errors.getFieldError().defaultMessage
	}
	
	void "test update username"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		shiroUser = userService.createObject(shiroUser)
		
		def shiroUser2 = [
			id:shiroUser.id,
			username:"name2",
			passwordHash:"a1234"
			]
		
		when:'create is called'
		shiroUser = userService.updateObject(shiroUser2)
		
		then:'check error'
		shiroUser.hasErrors() == false
		shiroUser.isDeleted == false
		shiroUser.username == shiroUser.username
		shiroUser.passwordHash == shiroUser.passwordHash
	}
	
	void "test update username validation username not null"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		shiroUser = userService.createObject(shiroUser)
		
		def shiroUser2 = [
			id:shiroUser.id,
			username:"",
			passwordHash:"a1234"
			]
		
		when:'create is called'
		shiroUser = userService.updateObject(shiroUser2)
		
		then:'check error'
		shiroUser.hasErrors() == true
		println shiroUser.errors.getFieldError().defaultMessage
	}
	
	void "test update username validation passwordhash not null"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		shiroUser = userService.createObject(shiroUser)
		
		def shiroUser2 = [
			id:shiroUser.id,
			username:"name2",
			passwordHash:""
			]
		
		when:'create is called'
		shiroUser = userService.updateObject(shiroUser2)
		
		then:'check error'
		shiroUser.hasErrors() == true
		println shiroUser.errors.getFieldError().defaultMessage
	}
	
	void "test update username validation ConfirmPassCorrect"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		shiroUser = userService.createObject(shiroUser)
		
		when:'create is called'
		shiroUser = userService.updatePasswordObject(shiroUser.id, "a4321", "a43210")
		
		then:'check error'
		shiroUser.hasErrors() == true
		println shiroUser.errors.getAllErrors().defaultMessage
	}
}
