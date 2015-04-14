package estate_management

import grails.converters.JSON
import grails.transaction.Transactional
import java.text.SimpleDateFormat

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
	
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "IVD-"+now+"-"+object.id
		return code
	}
	
	
	def createObject(object){
		Invoice invoice = Invoice.get(object.invoiceId)
		object.invoice = invoice
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = invoiceDetailValidationService.createObjectValidation(object as InvoiceDetail)
		
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
			invoice.addToInvoiceDetails(object)
			invoiceService.calculateTotal(object.invoice.id)
			object.code = createCode(object)
			object = object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = InvoiceDetail.read(object.id)
//		valObject.invoice = object.invoice
		valObject.code = object.code
		valObject.amount = Double.parseDouble(object.amount)
		valObject.description = object.description
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
		return newObject

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
		return newObject
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
		return newObject
	}
}
