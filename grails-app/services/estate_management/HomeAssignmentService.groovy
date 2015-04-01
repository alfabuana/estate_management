package estate_management

import grails.transaction.Transactional

@Transactional
class HomeAssignmentService {
	HomeAssignmentValidationService homeAssignmentValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return HomeAssignment.get(object)
	}
	def getList(){
		return HomeAssignment.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object = homeAssignmentValidationService.createObjectValidation(object as HomeAssignment)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = HomeAssignment.read(object.id)
		valObject.home = object.home
		valObject.username = object.username
		valObject.assignDate = object.assignDate
		valObject = homeAssignmentValidationService.updateObjectValidation(valObject)
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
		def newObject = HomeAssignment.get(object.id)
		newObject = homeAssignmentValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
	def confirmObject(def object){
		def newObject = HomeAssignment.get(object.id)
		newObject = homeAssignmentValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = HomeAssignment.get(object.id)
		newObject = homeAssignmentValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}


}
