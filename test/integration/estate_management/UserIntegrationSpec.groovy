package estate_management

import grails.test.spock.IntegrationSpec
import spock.lang.Shared
class UserIntegrationSpec extends IntegrationSpec {
	def userService
	def roleService
	@Shared
	def shiroRole

    def setup() {
		shiroRole = [
			name:"administrator"
			]
		shiroRole = roleService.createObject(shiroRole)
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
	
	void "test update password"() {
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
		shiroUser = userService.updatePassword(shiroUser2)
		
		then:'check error'
		shiroUser.hasErrors() == false
		shiroUser.isDeleted == false
		shiroUser.username == shiroUser.username
		shiroUser.passwordHash == shiroUser.passwordHash
	}
	
	void "test update password validation password not null"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		shiroUser = userService.createObject(shiroUser)
		
		def shiroUser2 = [
			id:shiroUser.id,
			username:"name1",
			passwordHash:""
			]
		
		when:'create is called'
		shiroUser = userService.updatePassword(shiroUser2)
		
		then:'check error'
		shiroUser.hasErrors() == true
		println shiroUser.errors.getFieldError().defaultMessage
	}
	
	void "test update roles"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234",
			]
		shiroUser = userService.createObject(shiroUser)
		
		def shiroUser2 = [
			id:shiroUser.id,
			username:"name1",
			roles:shiroRole,
			]
		
		when:'create is called'
		shiroUser = userService.updateRoles(shiroUser2)
		
		then:'check error'
		shiroUser.hasErrors() == false
		shiroUser.isDeleted == false
		shiroUser.username == shiroUser.username
		shiroUser.roles == shiroUser.roles
	}
	
	void "test update password validation ConfirmPassCorrect"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		shiroUser = userService.createObject(shiroUser)
		
		def shiroUser2 = [
			id:shiroUser.id,
			username:"name1",
			passwordHash:"a12345"]
		
		when:'create is called'
		shiroUser = userService.updatePasswordObject(shiroUser2, "a1234", "a43210")
		
		then:'check error'
		shiroUser.hasErrors() == true
		println shiroUser.errors.getAllErrors().defaultMessage
	}
	
	void "test delete username"() {
		setup:'setting new shiroUser'
		def shiroUser = [
			username:"name1",
			passwordHash:"a1234"
			]
		shiroUser = userService.createObject(shiroUser)
		
		when:'create is called'
		shiroUser = userService.softDeleteObject(shiroUser)
		
		then:'check error'
		shiroUser.hasErrors() == false
		shiroUser.isDeleted == true
	}
	
	
}
