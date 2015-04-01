package estate_management

import grails.transaction.Transactional

@Transactional
class ProjectService {
	ProjectValidationService projectValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Project.get(object)
	}
	def getList(){
		return Project.getAll()
	}
	def createObject(object){
		object.amountAgree = 0
		object.amountDisagree = 0
		object.isDeleted = false
		object.isConfirmed = false
		object = projectValidationService.createObjectValidation(object as Project)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Project.read(object.id)
		valObject.title = object.title
		valObject.description = object.description
		valObject = projectValidationService.updateObjectValidation(valObject)
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
		def newObject = Project.get(object.id)
		newObject = projectValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = Project.get(object.id)
		newObject = projectValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.save()
		}

	}
	def unConfirmObject(def object){
		def newObject = Project.get(object.id)
		newObject = projectValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}

	}
}
