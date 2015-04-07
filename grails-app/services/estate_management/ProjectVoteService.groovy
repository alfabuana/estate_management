package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class ProjectVoteService {
	ProjectVoteValidationService projectVoteValidationService
	ProjectService projectService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ProjectVote.get(object)
	}
	def getList(){
		return ProjectVote.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object = projectVoteValidationService.createObjectValidation(object as ProjectVote)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = ProjectVote.read(object.id)
		valObject.username = object.username
		valObject.project = object.project
		valObject.isAgree = object.isAgree
		valObject = projectVoteValidationService.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
			valObject.save()
		}
		else
		{
			valObject.discard()
		}
		return valObject
	}
	def softDeletedObject(def object){
		def newObject = ProjectVote.get(object.id)
		newObject = projectVoteValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
	
	def agreeObject(def object){
		object.username = ShiroUser.find{
			username == object.username
		}
		object.project = Project.find{
			id == object.projectid
		}
		object.isAgree = true
		object.isDeleted = false
		object = projectVoteValidationService.createObjectValidation(object as ProjectVote)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
			projectService.calculateTotal(object.project.id)
		}
		return object
	}
	
	def disagreeObject(def object){
		object.username = ShiroUser.find{
			username == object.username
		}
		object.project = Project.find{
			id == object.projectid
		}
		object.isAgree = false
		object.isDeleted = false
		object = projectVoteValidationService.createObjectValidation(object as ProjectVote)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
			projectService.calculateTotal(object.project.id)
		}
		return object
	}
}
