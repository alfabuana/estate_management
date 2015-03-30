package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidService {
	InvoicePaidValidationService invoicePaidValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return InvoicePaid.get(object)
	}
	def getList(){
		return InvoicePaid.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object = invoicePaidValidationService.createObjectValidation(object as InvoicePaid)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = InvoicePaid.read(object.id)
		valObject.username = object.username
		valObject.description = object.description
		valObject.paidDate = object.paidDate
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

	}
}
