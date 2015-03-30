package estate_management

import grails.test.spock.IntegrationSpec

class ProjectDetailIntegrationSpec extends IntegrationSpec {
	def projectService
	def projectDetailService

	def setup() {
	}

	def cleanup() {
	}

	void "Test Create new Project Detail"() {
		setup:'setting new Project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)

		and:'setting new Project Detail'
		def projectDetail = [
			project:project,
			attachmentUrl:"newAttachmentUrl"
		]

		when:'createObject is called'
		projectDetail = projectDetailService.createObject(projectDetail)

		then:'check has errors'
		projectDetail.hasErrors() == false
		projectDetail.isDeleted == false
	}
	void "Test Create Project Detail Validation project Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = ""
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)

		and:'setting new Project Detail'
		def projectDetail = [
			project:null,
			attachmentUrl:"newAttachmentUrl"
		]

		when:'createObject is called'
		projectDetail = projectDetailService.createObject(projectDetail)

		then:'check has errors'
		projectDetail.hasErrors() == true
		println projectDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Detail Validation attachment Url Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = ""
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)

		and:'setting new Project Detail'
		def projectDetail = [
			project:project,
			attachmentUrl:""
		]


		when:'createObject is called'
		projectDetail = projectDetailService.createObject(projectDetail)

		then:'check has errors'
		projectDetail.hasErrors() == true
		println projectDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update new Project Detail"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)

		and:'setting new Project Detail'
		def projectDetail = [
			project:project,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)

		and: 'setting data for Update'
		def projectDetail2 = [
			id:projectDetail.id,
			project:project,
			attachmentUrl:"updateAttachmentUrl"
		]

		when:'updateObject is called'
		projectDetail = projectDetailService.updateObject(projectDetail2)

		then:'check has errors'
		projectDetail.hasErrors() == false
		projectDetail.project == projectDetail2.project
		projectDetail.attachmentUrl == projectDetail2.attachmentUrl
	}
	void "Test Update Project Detail Validation Project Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)

		and:'setting new Project Detail'
		def projectDetail = [
			project:project,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)

		and: 'setting data for Update'
		def projectDetail2 = [
			id:projectDetail.id,
			project:null,
			attachmentUrl:"newAttachmentUrl"
		]

		when:'updateObject is called'
		projectDetail = projectDetailService.updateObject(projectDetail2)

		then:'check has errors'
		projectDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + projectDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update Project Detail Validation attachment Url Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)

		and:'setting new Project Detail'
		def projectDetail = [
			project:project,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)

		and: 'setting data for Update'
		def projectDetail2 = [
			id:projectDetail.id,
			project:project,
			attachmentUrl:""
		]

		when:'updateObject is called'
		projectDetail = projectDetailService.updateObject(projectDetail2)

		then:'check has errors'
		projectDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + projectDetail.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Project"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)

		and:'setting new Project Detail'
		def projectDetail = [
			project:project,
			attachmentUrl:"newAttachmentUrl"
		]
		projectDetail = projectDetailService.createObject(projectDetail)

		
		when:'sofDeleted is called'
		projectDetail = projectDetailService.softDeletedObject(projectDetail)
		
		then:'check has errors'
		projectDetail.hasErrors() == false
		projectDetail.isDeleted == true
	}
}
