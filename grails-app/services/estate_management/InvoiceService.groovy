package estate_management

import java.awt.event.ItemEvent;

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
	def getListDeleted(){
		return Invoice.findAll{isDeleted == false}
	}
	def calculateTotal(def objectId){
		def valObject = Invoice.read(objectId)
		Double total = 0
		for (i in valObject.invoiceDetails.findAll{it.isDeleted == false})
		{
			total = total + i.amount
		}
		valObject.totalAmount = total
		valObject.save()
		return valObject
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.isCleared = false
		object.totalAmount = 0
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
//		valObject.totalAmount = Double.parseDouble(object.totalAmount)
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
			for (detail in newObject.invoiceDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = true
				detail.confirmationDate = new Date()
				Receivable receivable = new Receivable()
				receivable.username = newObject.username
				receivable.receivableSource = "invoice"
				receivable.receivableSourceId = newObject.id
				receivable.receivableSourceDetailId = detail.id
				receivable.code = newObject.code
				receivable.dueDate = newObject.dueDate
				receivable.amount = detail.amount
				receivable.remainingAmount = detail.amount
				receivable.pendingClearanceAmount = 0
				receivable.isCompleted = false
				receivable.isDeleted = false
				receivable.save()
			}
			newObject.save()
		}
		return newObject
	}
	def unConfirmObject(def object){
		def newObject = Invoice.get(object.id)
		newObject = invoiceValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			for (detail in newObject.invoiceDetails.findAll{ it.isDeleted == false })
			{
				Receivable receivable = Receivable.find{
					receivableSource == "invoice"&&
					receivableSourceId == newObject.id &&
					receivableSourceDetailId == detail.id &&
					isDeleted == false
				}
				receivable.isDeleted = true
				receivable.save()
				detail.isConfirmed = false
				detail.confirmationDate = null
			}
			newObject.save()
		}
		return newObject
	}
}
