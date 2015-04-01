package estate_management

import grails.transaction.Transactional

@Transactional
class InvoiceService {
	InvoiceValidationService invoiceValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Invoice.get(object)
	}
	def getList(){
		return Invoice.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.isCleared = false
		object = invoiceValidationService.createObjectValidation(object as Invoice)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Invoice.read(object.id)
		valObject.username = object.username
		valObject.code = object.code
		valObject.invoiceDate = object.invoiceDate
		valObject.description = object.description
//		valObject.dueDate = object.dueDate
		valObject.totalAmount = Double.parseDouble(object.totalAmount)
		valObject = invoiceValidationService.updateObjectValidation(valObject)
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
		def newObject = Invoice.get(object.id)
		newObject = invoiceValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = Invoice.get(object.id)
		newObject = invoiceValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = Invoice.get(object.id)
		newObject = invoiceValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}
}
