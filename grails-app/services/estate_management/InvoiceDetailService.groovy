package estate_management

import grails.transaction.Transactional

@Transactional
class InvoiceDetailService {
	InvoiceDetailValidationService invoiceDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return InvoiceDetail.get(object)
	}
	def getList(){
		return InvoiceDetail.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object = invoiceDetailValidationService.createObjectValidation(object as InvoiceDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = InvoiceDetail.read(object.id)
		valObject.invoice = object.invoice
		valObject.code = object.code
		valObject.amount = object.amount
		valObject = invoiceDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = InvoiceDetail.get(object.id)
		newObject = invoiceDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = InvoiceDetail.get(object.id)
		newObject = invoiceDetailValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = newObject.confirmationDate
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = InvoiceDetail.get(object.id)
		newObject = invoiceDetailValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}
}
