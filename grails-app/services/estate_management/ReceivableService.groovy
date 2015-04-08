package estate_management

import grails.transaction.Transactional

@Transactional
class ReceivableService {
	ReceivableValidationService receivableValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Receivable.get(object)
	}
	def getList(){
		return Receivable.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isCompleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = receivableValidationService.createObjectValidation(object as Receivable)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Receivable.read(object.id)
		valObject.user = object.user
		valObject.receivableSource = object.receivableSource
		valObject.receivableSourceId = object.receivableSourceId
		valObject.receivableSourceDetailId = object.receivableSourceDetailId
		valObject.code = object.code
		valObject.dueDate = object.dueDate
		valObject.amount = Double.parseDouble(object.amount)
		valObject.remainingAmount = Double.parseDouble(object.remainingAmount)
		valObject.pendingClearanceAmount = Double.parseDouble(object.pendingClearanceAmount)
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = receivableValidationService.updateObjectValidation(valObject)
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
		def newObject = Receivable.get(object.id)
		newObject = receivableValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
}
