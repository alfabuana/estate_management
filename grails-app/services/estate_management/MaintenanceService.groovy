package estate_management

import grails.transaction.Transactional

@Transactional
class MaintenanceService {
	MaintenanceValidationService maintenanceValidationService

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
		valObject.amount = object.amount
		valObject.code = object.code
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

	}
}
