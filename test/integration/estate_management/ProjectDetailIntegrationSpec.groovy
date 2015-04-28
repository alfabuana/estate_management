package estate_management

import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class ProjectDetailIntegrationSpec extends IntegrationSpec {
	def projectService
	def userService
	def projectDetailService
	
	@Shared
	def shiroUser
	def project

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		project = [
			title:"project1"
			]
		project = projectService.createObject(project)
	}

	def cleanup() {
	}

	void "Test Create new Project Detail"() {
		setup:'setting new Project Detail'
		def projectDetail = [
			projectId:project.id,
			attachmentUrl:"newAttachmentUrl"
		]

		when:'createObject is called'
		projectDetail = projectDetailService.createObject(projectDetail)

		then:'check has errors'
		projectDetail.hasErrors() == false
		projectDetail.isDeleted == false
	}
	void "Test Create Project Detail Validation project Not Null"() {
		setup:'setting new Project Detail'
		def projectDetail = [
			projectId:null,
			attachmentUrl:"newAttachmentUrl"
		]

		when:'createObject is called'
		projectDetail = projectDetailService.createObject(projectDetail)

		then:'check has errors'
		projectDetail.hasErrors() == true
		println projectDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Detail Validation attachment Url Not Null"() {
		setup:'setting new Project Detail'
		def projectDetail = [
			projectId:project.id,
			attachmentUrl:""
		]


		when:'createObject is called'
		projectDetail = projectDetailService.createObject(projectDetail)

		then:'check has errors'
		projectDetail.hasErrors() == true
		println projectDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update new Project Detail"() {
		setup:'setting new Project Detail'
		def projectDetail = [
			projectId:project.id,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)

		and: 'setting data for Update'
		def projectDetail2 = [
			id:projectDetail.id,
			projectId:project.id,
			attachmentUrl:"updateAttachmentUrl"
		]

		when:'updateObject is called'
		projectDetail = projectDetailService.updateObject(projectDetail2)

		then:'check has errors'
		projectDetail.hasErrors() == false
		projectDetail.project == projectDetail.project
		projectDetail.attachmentUrl == projectDetail.attachmentUrl
	}
	void "Test Update Project Detail Validation attachment Url Not Null"() {
		setup:'setting new Project Detail'
		def projectDetail = [
			projectId:project.id,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)

		and: 'setting data for Update'
		def projectDetail2 = [
			id:projectDetail.id,
			projectId:project.id,
			attachmentUrl:""
		]

		when:'updateObject is called'
		projectDetail = projectDetailService.updateObject(projectDetail2)

		then:'check has errors'
		projectDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + projectDetail.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Project"() {
		setup:'setting new Project Detail'
		def projectDetail = [
			projectId:project.id,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)

		
		when:'sofDeleted is called'
		projectDetail = projectDetailService.softDeletedObject(projectDetail)
		
		then:'check has errors'
		projectDetail.hasErrors() == false
		projectDetail.isDeleted == true
	}
	void "Test SoftDelete Project Validation Is deleted"() {
		setup:'setting new Project Detail'
		def projectDetail = [
			projectId:project.id,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)
		projectDetail = projectDetailService.softDeletedObject(projectDetail)

		
		when:'sofDeleted is called'
		projectDetail = projectDetailService.softDeletedObject(projectDetail)
		
		then:'check has errors'
		println "Validasi sukses dengan error message : " + projectDetail.errors.getAllErrors().defaultMessage
	}
}
