package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared
class ProjectVoteIntegrationSpec extends IntegrationSpec {
	def userService
	def projectService
	def projectVoteService

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

	void "Test Create new Project Vote"() {
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
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
		setup:'setting new Project Vote'
		def projectVote = [
			user:null,
			project:project,
			isAgree:true
		]


		when:'createObject is called'
		projectVote = projectVoteService.createObject(projectVote)
		println projectVote as JSON

		then:'check has errors'
		projectVote.hasErrors() == true
		println projectVote.errors.getFieldError().defaultMessage
	}
	void "Test Create Project Vote Validation project Not Null"() {
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
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
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
			project:project,
			isAgree:null
		]


		when:'createObject is called'
		projectVote = projectVoteService.createObject(projectVote)

		then:'check has errors'
		projectVote.hasErrors() == true
		println projectVote.errors.getFieldError().defaultMessage
	}

	void "Test Create Project Vote Validation Already Vote"() {
		setup:'setting new Project Vote'
		def projectVote = [
			user:shiroUser,
			project:project,
			isAgree:false
		]
		projectVote = projectVoteService.createObject(projectVote)

		def agree = [
			id:projectVote.id,
			username:shiroUser.username,
			projectid:project.id
		]

		when:'createObject is called'
		projectVote = projectVoteService.agreeObject(agree)

		then:'check has errors'
		projectVote.hasErrors() == true
		println projectVote.errors.getAllErrors().defaultMessage
	}
	void "Test Update new Project Vote"() {
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
			project:project,
			isAgree:true
		]
		projectVote = projectVoteService.createObject(projectVote)

		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:shiroUser.username,
			user:shiroUser,
			project:project,
			isAgree:false
		]

		when:'updateObject is called'
		projectVote = projectVoteService.updateObject(projectVote2)

		then:'check has errors'
		projectVote.hasErrors() == false
		projectVote.user == projectVote.user
		projectVote.project == projectVote.project
		projectVote.isAgree == projectVote.isAgree
	}
	void "Test Update project Vote Validation username Not Null"() {
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
			project:project,
			isAgree:true
		]
		projectVote = projectVoteService.createObject(projectVote)

		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:shiroUser.username,
			user:null,
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
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
			project:project,
			isAgree:true
		]
		projectVote = projectVoteService.createObject(projectVote)

		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:shiroUser.username,
			user:shiroUser,
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
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
			project:project,
			isAgree:true
		]
		projectVote = projectVoteService.createObject(projectVote)

		and: 'setting data for Update'
		def projectVote2 = [
			id:projectVote.id,
			username:shiroUser.username,
			user:shiroUser,
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
		setup:'setting new Project Vote'
		def projectVote = [
			username:shiroUser.username,
			user:shiroUser,
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
