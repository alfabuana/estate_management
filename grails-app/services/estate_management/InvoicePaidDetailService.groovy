package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidDetailService {
	InvoicePaidDetailValidationService invoicePaidDetailValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return InvoicePaidDetail.get(object)
	}
	def getList(){
		return InvoicePaidDetail.getAll()
	}
	def getList(object){
		def a = object.toLong()
		return InvoicePaidDetail.findAll("from InvoicePaidDetail as b where b.invoicePaid.id=? and b.isDeleted =false",[a])
	}
	def createObject(object){
		object.invoicePaid = InvoicePaid.get(object.invoicePaidId)
		object.isDeleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = invoicePaidDetailValidationService.createObjectValidation(object as InvoicePaidDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = InvoicePaidDetail.read(object.id)
//		valObject.invoicePaid = object.invoicePaid
		valObject.attachmentUrl = object.attachmentUrl
		valObject.updatedBy = userService.getObjectByUserName(object.username)
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
		return newObject

	}
}
