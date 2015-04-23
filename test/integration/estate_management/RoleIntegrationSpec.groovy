package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec

class RoleIntegrationSpec extends IntegrationSpec {
	def roleService

    def setup() {
    }

    def cleanup() {
    }

    void "test create role"() {
		setup:'setting new role'
		def shiroRole = [
			name:"administrator"
			]
		
		when:'create is called'
		shiroRole = roleService.createObject(shiroRole)
		
		then:'check has errors'
		shiroRole.hasErrors() == false
		shiroRole.isDeleted == false
    }
	
	void "test create role validation name not null"() {
		setup:'setting new role'
		def shiroRole = [
			name:""
			]
		
		when:'create is called'
		shiroRole = roleService.createObject(shiroRole)
		
		then:'check has errors'
		shiroRole.hasErrors()== true
		println shiroRole.errors.getFieldError().defaultMessage
	}
	
	void "test create role validation name must uniq"() {
		setup:'setting new role'
		def shiroRole = [
			name:"administrator"
			]
		
		shiroRole = roleService.createObject(shiroRole)
		def shiroRole2 = [
			name:"administrator"
			]
		
		when:'create is called'
		shiroRole = roleService.createObject(shiroRole2)
		
		then:'check has errors'
		shiroRole.hasErrors()== true
		println shiroRole.errors.getFieldError().defaultMessage
	}
	void "test update role"() {
		setup:'setting new role'
		def shiroRole = [
			name:"administrator"
			]
		shiroRole = roleService.createObject(shiroRole)
		def shiroRole2 = [
			id:shiroRole.id,
			name:"admin"]
		
		when:'create is called'
		shiroRole = roleService.createObject(shiroRole)
		
		then:'check has errors'
		shiroRole.hasErrors()== false
		shiroRole.isDeleted == false
		shiroRole.name == shiroRole.name
	}
	
	void "test update role validation name not null"() {
		setup:'setting new role'
		def shiroRole = [
			name:"administrator"
			]
		shiroRole = roleService.createObject(shiroRole)
		def shiroRole2 = [
			id:shiroRole.id,
			name:""]
		
		when:'create is called'
		shiroRole = roleService.updateObject(shiroRole2)
		
		then:'check has errors'
		shiroRole.hasErrors()== true
		println shiroRole.errors.getFieldError().defaultMessage
	}
	
	void "test update role validation name must uniq"() {
		setup:'setting new role'
		def shiroRole = [
			name:"admin"
			]
		
		shiroRole = roleService.createObject(shiroRole)
		def shiroRole2 = [
			name:"administrator"
			]
		shiroRole2 = roleService.createObject(shiroRole2)
		
		def shiroRole3 = [
			id:shiroRole.id,
			name:"administrator"]
		
		when:'update is called'
		shiroRole = roleService.updateObject(shiroRole3)
		
		then:'check has errors'
		shiroRole.hasErrors() == true
		println shiroRole.errors.getFieldError().defaultMessage
	}
	
	void "test soft delete role"() {
		setup:'setting new role'
		def shiroRole = [
			name:"administrator"
			]
		shiroRole = roleService.createObject(shiroRole)
		
		when:'delete is called'
		shiroRole = roleService.softDeleteObject(shiroRole)
		
		then:'check has errors'
		shiroRole.hasErrors()== false
		shiroRole.isDeleted == true
	}
}
