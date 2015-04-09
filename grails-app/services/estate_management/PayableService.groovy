package estate_management

import grails.transaction.Transactional

@Transactional
class PayableService {
	PayableValidationService payableValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Payable.get(object)
	}
	
	def getList(){
		return Payable.findAll{isDeleted==false}
	}
	
	def createObject(object){
		object.isDeleted = false
		object.isCompleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = payableValidationService.createObjectValidation(object as Payable)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Payable.read(object.id)
		valObject.user = object.user
		valObject.payableSource = object.payableSource
		valObject.payableSourceId = object.payableSourceId
		valObject.payableSourceDetailId = object.payableSourceDetailId
		valObject.code = object.code
		valObject.dueDate = object.dueDate
		valObject.amount = Double.parseDouble(object.amount)
		valObject.remainingAmount = Double.parseDouble(object.remainingAmount)
		valObject.pendingClearanceAmount = Double.parseDouble(object.pendingClearanceAmount)
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = payableValidationService.updateObjectValidation(valObject)
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
		def newObject = Payable.get(object.id)
		newObject = payableValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
}
