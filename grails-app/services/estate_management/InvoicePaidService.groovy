package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidService {
	InvoicePaidValidationService invoicePaidValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return InvoicePaid.get(object)
	}
	def getList(){
		return InvoicePaid.findAll([sort: "id", order: "desc"]){}
	}
	def getListForClearance(){
		return InvoicePaid.findAll([sort: "id", order: "desc"]){
			isConfirmed == true && 
			isDeleted == false 
		}
	}
	def createObject(object){
		object.user = userService.getObjectByUserName(object.username)
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = invoicePaidValidationService.createObjectValidation(object as InvoicePaid)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = InvoicePaid.read(object.id)
		valObject.invoice = object.invoice
		valObject.paidDate = object.paidDate
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = invoicePaidValidationService.updateObjectValidation(valObject)
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
		def newObject = InvoicePaid.get(object.id)
		newObject = invoicePaidValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject

	}
	def confirmObject(def object){
		def newObject = InvoicePaid.get(object.id)
		newObject = invoicePaidValidationService.confirmObjectValidation(newObject)
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
		def newObject = InvoicePaid.get(object.id)
		newObject = invoicePaidValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			newObject.save()
		}
		return newObject
	}
	def clearObject(def object){
		def newObject = InvoicePaid.get(object.id)
		newObject = invoicePaidValidationService.clearObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.invoice.isCleared = true
			newObject.invoice.clearDate = new Date()
			newObject.save()
		}
		return newObject
	}
	def unClearObject(def object){
		def newObject = InvoicePaid.get(object.id)
		newObject = invoicePaidValidationService.unClearObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.invoice.isCleared = false
			newObject.invoice.clearDate = null
			newObject.save()
		}
		return newObject
	}
}
