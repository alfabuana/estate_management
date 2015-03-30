package estate_management

import grails.test.spock.IntegrationSpec

class ProjectIntegrationSpec extends IntegrationSpec {
	def projectService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create new Project"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000

		when:'createObject is called'
		project = projectService.createObject(project)

		then:'check has errors'
		project.hasErrors() == false
		project.isDeleted == false
	}
	void "Test Create Project Validation title Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = ""
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000

		when:'createObject is called'
		project = projectService.createObject(project)

		then:'check has errors'
		project.hasErrors() == true
		println project.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Validation description Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = ""
		project.amountAgree = 1000
		project.amountDisagree = 2000

		when:'createObject is called'
		project = projectService.createObject(project)

		then:'check has errors'
		project.hasErrors() == true
		println project.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Validation amount Agree Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = null
		project.amountDisagree = 2000

		when:'createObject is called'
		project = projectService.createObject(project)

		then:'check has errors'
		project.hasErrors() == true
		println project.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Validation amount disagree Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = null

		when:'createObject is called'
		project = projectService.createObject(project)

		then:'check has errors'
		project.hasErrors() == true
		println project.errors.getFieldError().defaultMessage
	}
	void "Test Update new Project"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and: 'setting data for Update'
		Project project2 = new Project()
		project2.id = project.id
		project2.title = "updateTitle"
		project2.description = "updateDescription"
		project2.amountAgree = 3000
		project2.amountDisagree = 4000
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == false
		project.title == project2.title
		project.description == project2.description
		project.amountAgree == project2.amountAgree
		project.amountDisagree == project2.amountDisagree
	}
	void "Test Update project Project title Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and: 'setting data for Update'
		Project project2 = new Project()
		project2.id= project.id
		project2.title = ""
		project2.description = "updateDescription"
		project2.amountAgree = 3000
		project2.amountDisagree = 4000
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getFieldError().defaultMessage
	}
	void "Test Update project Project description Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and: 'setting data for Update'
		Project project2 = new Project()
		project2.id = project.id
		project2.title = "newTitle"
		project2.description = ""
		project2.amountAgree = 3000
		project2.amountDisagree = 4000
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getFieldError().defaultMessage
	}
	void "Test Update project Project amount agree Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and: 'setting data for Update'
		Project project2 = new Project()
		project2.id = project.id
		project2.title = "newTitle"
		project2.description = "updateDescription"
		project2.amountAgree = null
		project2.amountDisagree = 4000
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getFieldError().defaultMessage
	}
	void "Test Update project Project amount disagree Not Null"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and: 'setting data for Update'
		Project project2 = new Project()
		project2.id = project.id
		project2.title = "newTitle"
		project2.description = "updateDescription"
		project2.amountAgree = 3000
		project2.amountDisagree = null
		 
		when:'updateObject is called'
		project = projectService.updateObject(project2)
		
		then:'check has errors'
		project.hasErrors() == true
		println "Validasi sukses dengan error message : " + project.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Project"() {
		setup:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		when:'sofDeleted is called'
		project = projectService.softDeletedObject(project)
		
		then:'check has errors'
		project.hasErrors() == false
		project.isDeleted == true
	}
}
