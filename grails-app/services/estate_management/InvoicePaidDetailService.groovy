package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidDetailService {
	InvoicePaidDetailValidationService invoicePaidDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return InvoicePaidDetail.get(object)
	}
	def getList(){
		return InvoicePaidDetail.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object = invoicePaidDetailValidationService.createObjectValidation(object as InvoicePaidDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = InvoicePaidDetail.read(object.id)
		valObject.invoicePaid = object.invoicePaid
		valObject.attachmentUrl = object.attachmentUrl
		valObject = invoicePaidDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = InvoicePaidDetail.get(object.id)
		newObject = invoicePaidDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
}
