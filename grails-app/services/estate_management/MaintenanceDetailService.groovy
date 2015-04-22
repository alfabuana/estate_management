package estate_management

import grails.transaction.Transactional

@Transactional
class MaintenanceDetailService {
	MaintenanceDetailValidationService maintenanceDetailValidationService
	UserService userService

    def serviceMethod() {

    }
	def getObjectById(def object){
		return MaintenanceDetail.get(object)
	}
	def getList(){
		return MaintenanceDetail.getAll()
	}
	def getList(object){
		def a = object.toLong()
		return MaintenanceDetail.findAll("from MaintenanceDetail as b where b.maintenance.id=? and b.isDeleted =false",[a])
	}
	def createObject(object){
		object.maintenance = Maintenance.get(object.maintenanceId)
		object.isDeleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = maintenanceDetailValidationService.createObjectValidation(object as MaintenanceDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = MaintenanceDetail.read(object.id)
//		valObject.complaint = object.complaint
		valObject.user = object.user
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = maintenanceDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = MaintenanceDetail.get(object.id)
		newObject = maintenanceDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
}
