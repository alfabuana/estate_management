package estate_management

import grails.test.spock.IntegrationSpec

class ProjectVoteIntegrationSpec extends IntegrationSpec {
	def userService
	def projectService
	def projectVoteService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create new Project Vote"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:project,
			isAgree:true
			]

		when:'createObject is called'
		projectVote = projectVoteService.createObject(projectVote)

		then:'check has errors'
		projectVote.hasErrors() == false
		projectVote.isDeleted == false
	}
	void "Test Create Project Vote Validation username Not Null"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:null,
			project:project,
			isAgree:true
			]


		when:'createObject is called'
		projectVote = projectVoteService.createObject(projectVote)

		then:'check has errors'
		projectVote.hasErrors() == true
		println projectVote.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Vote Validation project Not Null"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:null,
			isAgree:true
			]


		when:'createObject is called'
		projectVote = projectVoteService.createObject(projectVote)

		then:'check has errors'
		projectVote.hasErrors() == true
		println projectVote.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Vote Validation is Agree Not Null"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:project,
			isAgree:null
			]


		when:'createObject is called'
		projectVote = projectVoteService.createObject(projectVote)

		then:'check has errors'
		projectVote.hasErrors() == true
		println projectVote.errors.getFieldError().defaultMessage
	}
	void "Test Update new Project Vote"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:project,
			isAgree:true
			]
		projectVote = projectVoteService.createObject(projectVote)
		
		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:shiroUser,
			project:project,
			isAgree:false
			]
		 
		when:'updateObject is called'
		projectVote = projectVoteService.updateObject(projectVote2)
		
		then:'check has errors'
		projectVote.hasErrors() == false
		projectVote.username == projectVote2.username
		projectVote.project == projectVote2.project
		projectVote.isAgree == projectVote2.isAgree
	}
	void "Test Update project Vote Validation username Not Null"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:project,
			isAgree:true
			]
		projectVote = projectVoteService.createObject(projectVote)
		
		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:null,
			project:project,
			isAgree:false
			]
		 
		when:'updateObject is called'
		projectVote = projectVoteService.updateObject(projectVote2)
		
		then:'check has errors'
		projectVote.hasErrors() == true
		println "Validasi sukses dengan error message : " + projectVote.errors.getFieldError().defaultMessage
	}
	void "Test Update project Vote Validation project Not Null"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:project,
			isAgree:true
			]
		projectVote = projectVoteService.createObject(projectVote)
		
		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:shiroUser,
			project:null,
			isAgree:false
			]
		 
		when:'updateObject is called'
		projectVote = projectVoteService.updateObject(projectVote2)
		
		then:'check has errors'
		projectVote.hasErrors() == true
		println "Validasi sukses dengan error message : " + projectVote.errors.getFieldError().defaultMessage
	}
	void "Test Update project Vote Validation is agree Not Null"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:project,
			isAgree:true
			]
		projectVote = projectVoteService.createObject(projectVote)
		
		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:shiroUser,
			project:project,
			isAgree:null
			]
		 
		when:'updateObject is called'
		projectVote = projectVoteService.updateObject(projectVote2)
		
		then:'check has errors'
		projectVote.hasErrors() == true
		println "Validasi sukses dengan error message : " + projectVote.errors.getFieldError().defaultMessage
	}
	void "Test SoftDelete Project"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)
		
		and:'setting new project'
		Project project = new Project()
		project.title = "newTitle"
		project.description = "newDescription"
		project.amountAgree = 1000
		project.amountDisagree = 2000
		project = projectService.createObject(project)
		
		and:'setting new Project Vote'
		def projectVote = [
			username:shiroUser,
			project:project,
			isAgree:true
			]
		projectVote = projectVoteService.createObject(projectVote)
		
		when:'sofDeleted is called'
		projectVote = projectVoteService.softDeletedObject(projectVote)
		
		then:'check has errors'
		projectVote.hasErrors() == false
		projectVote.isDeleted == true
	}
}
