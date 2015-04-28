package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared
class ProjectIntegrationSpec extends IntegrationSpec {
	def projectService
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

    void "Test Create new Project"() {
		setup: 'setting new Payment Request'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]

		when : 'createObject is called'
		project = projectService.createObject(project)

		then: 'check has errors'
		project.hasErrors() == false
	}
	void "Test Create Project Validation title Not Null"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:null
		]

		when:'createObject is called'
		project = projectService.createObject(project)

		then:'check has errors'
		project.hasErrors() == true
		println "validasi sukses dengan error message : "+ project.errors.getFieldError().defaultMessage
	}
	void "Test Update new Project"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		
		and: 'setting data for Update'
		def project2 = [
			id:project.id,
			username:shiroUser.username,
			title:"newTitle"
		]
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == false
		project.title == project2.title
	}
	void "Test Update project Project title Not Null"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		
		and: 'setting data for Update'
		def project2 = [
			id:project.id,
			username:shiroUser.username,
			title:""
		]
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getFieldError().defaultMessage
	}
	
	void "Test Update project Validation Is Confirmed"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm = 
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)
		
		and: 'setting data for Update'
		def project2 = [
			id:project.id,
			username:shiroUser.username,
			title:""
		]
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Project"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		
		when:'sofDeleted is called'
		project = projectService.softDeletedObject(project)
		
		then:'check has errors'
		project.hasErrors() == false
		project.isDeleted == true
	}
	void "Test SoftDelete Project Validation Is Deleted"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		project = projectService.softDeletedObject(project)
		
		when:'sofDeleted is called'
		project = projectService.softDeletedObject(project)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	void "Test SoftDelete Project Validation Is Confirmed"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)

		
		when:'sofDeleted is called'
		project = projectService.softDeletedObject(project)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	void "Test Confirm Project"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		
		
		when:'sofDeleted is called'
		project = projectService.confirmObject(confirm)
		
		then:'check has errors'
		project.hasErrors() == false
		project.isConfirmed == true
	}
	void "Test Confirm Project Validation Is Confirmed"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)
		
		when:'sofDeleted is called'
		project = projectService.confirmObject(confirm)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm Project"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		
		project = projectService.confirmObject(confirm)
		when:'sofDeleted is called'
		project = projectService.unConfirmObject(project)
		
		then:'check has errors'
		project.hasErrors() == false
		project.isConfirmed == false
	}
	void "Test unConfirm Project Validation Is Not Confirmed"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		
		when:'sofDeleted is called'
		project = projectService.unConfirmObject(project)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	void "Test unConfirm Project Validation Is Finished"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)
		project = projectService.finishObject(project)
		when:'sofDeleted is called'
		project = projectService.unConfirmObject(project)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	
	void "Test Finish Project "() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)
		
		when:'sofDeleted is called'
		project = projectService.finishObject(project)
		
		then:'check has errors'
		project.hasErrors() == false
		project.isFinished == true
	}
	void "Test Finish Project Validation IsFinished"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)
		project = projectService.finishObject(project)
		
		when:'sofDeleted is called'
		project = projectService.finishObject(project)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	void "Test Finish Project Validation Is Not Confirmed"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		
		when:'sofDeleted is called'
		project = projectService.finishObject(project)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
	void "Test unFinish Project "() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)
		project = projectService.finishObject(project)
		
		when:'sofDeleted is called'
		project = projectService.unFinishObject(project)
		
		then:'check has errors'
		project.hasErrors() == false
		project.isFinished == false
	}
	void "Test unFinish Project Validation Is Not Finished"() {
		setup:'setting new project'
		def project = [
			username:shiroUser.username,
			title:"newTitle"
		]
		project = projectService.createObject(project)
		def confirm =
		[
			id:project.id,
			username:shiroUser.username
			]
		project = projectService.confirmObject(confirm)
		
		when:'sofDeleted is called'
		project = projectService.unFinishObject(project)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getAllErrors().defaultMessage
	}
}
