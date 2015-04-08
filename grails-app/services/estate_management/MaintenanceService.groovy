package estate_management

import grails.transaction.Transactional

@Transactional
class MaintenanceService {
	MaintenanceValidationService maintenanceValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Maintenance.get(object)
	}
	def getList(){
		return Maintenance.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = maintenanceValidationService.createObjectValidation(object as Maintenance)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Maintenance.read(object.id)
		valObject.description = object.description
		valObject.amount = Double.parseDouble(object.amount)
		valObject.code = object.code
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = maintenanceValidationService.updateObjectValidation(valObject)
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
		def newObject = Maintenance.get(object.id)
		newObject = maintenanceValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject

	}
	def confirmObject(def object){
		def newObject = Maintenance.get(object.id)
		newObject = maintenanceValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			newObject.save()
		}
		return newObject

	}
	def unConfirmObject(def object){
		def newObject = Maintenance.get(object.id)
		newObject = maintenanceValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			newObject.save()
		}
		return newObject

	}
}
