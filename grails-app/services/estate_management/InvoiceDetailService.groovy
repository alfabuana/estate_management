package estate_management

import grails.transaction.Transactional

@Transactional
class InvoiceDetailService {
	InvoiceDetailValidationService invoiceDetailValidationService
	InvoiceService	invoiceService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return InvoiceDetail.get(object)
	}
	def getList(){
		return InvoiceDetail.getAll()
	}
	def getList(object){
		def a = object.toLong()
		return InvoiceDetail.findAll("from InvoiceDetail as b where b.invoice.id=? and b.isDeleted =false",[a])
	}
	def createObject(object){
		object.invoice = Invoice.get(object.invoiceId)
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = invoiceDetailValidationService.createObjectValidation(object as InvoiceDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
			invoiceService.calculateTotal(object.invoice.id)
		}
		return object
	}
	def updateObject(def object){
		def valObject = InvoiceDetail.read(object.id)
//		valObject.invoice = object.invoice
		valObject.code = object.code
		valObject.amount = Double.parseDouble(object.amount)
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = invoiceDetailValidationService.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
			valObject.save()
			invoiceService.calculateTotal(valObject.invoice.id)
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
			invoiceService.calculateTotal(newObject.invoice.id)
		}

	}
	def confirmObject(def object){
		def newObject = InvoiceDetail.get(object.id)
		newObject = invoiceDetailValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
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
			newObject.confirmedBy = null
			newObject.save()
		}
	}
}
