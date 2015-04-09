package estate_management

import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class InvoiceService {
	InvoiceValidationService invoiceValidationService
	UserService userService

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
	
	def getListOutstanding(object)
	{
		object = userService.getObjectByUserName(object)
		return Invoice.findAll{
			isDeleted == false &&
			isConfirmed == true 
			&&
			home.homeDetails.find{ it.user == object
				} != null 
			}
	}
	
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "IV-"+now+"-"+object.id
		return code
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
		object.createdBy = userService.getObjectByUserName(object.username)
		object = invoiceValidationService.createObjectValidation(object as Invoice)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
			object.code = createCode(object)
			object = object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Invoice.read(object.id)
		valObject.home = object.home
		valObject.code = object.code
		valObject.invoiceDate = object.invoiceDate
		valObject.description = object.description
//		valObject.dueDate = object.dueDate
//		valObject.totalAmount = Double.parseDouble(object.totalAmount)
		valObject.updatedBy = userService.getObjectByUserName(object.username)
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
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			for (detail in newObject.invoiceDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = true
				detail.confirmationDate = new Date()
				detail.confirmedBy = userService.getObjectByUserName(object.username)
				Receivable receivable = new Receivable()
				receivable.user = newObject.user
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
			newObject.confirmedBy = null
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
				detail.confirmedBy = null
			}
			newObject.save()
		}
		return newObject
	}
}
