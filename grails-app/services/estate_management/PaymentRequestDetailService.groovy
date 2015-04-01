package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentRequestDetailService {
	PaymentRequestDetailValidationService paymentRequestDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return PaymentRequestDetail.get(object)
	}
	def getList(){
		return PaymentRequestDetail.getAll()
	}
	
	def getList(object){
		def a = object.toLong()
		return PaymentRequestDetail.findAll("from PaymentRequestDetail as b where b.paymentRequest.id=? and b.isDeleted =false",[a])
	}
	def createObject(object){
		object.paymentRequest = PaymentRequest.get(object.paymentRequestId)
		object.isDeleted = false
		object.isConfirmed = false
		object = paymentRequestDetailValidationService.createObjectValidation(object as PaymentRequestDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = PaymentRequestDetail.read(object.id)
//		valObject.paymentRequest = PaymentRequest.get(object.paymentRequestId)
		valObject.code = object.code
		valObject.amount = Double.parseDouble(object.amount)
		valObject.description = object.description
		valObject = paymentRequestDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = PaymentRequestDetail.get(object.id)
		newObject = paymentRequestDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
	def confirmObject(def object){
		def newObject = PaymentRequestDetail.get(object.id)
		newObject = paymentRequestDetailValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = PaymentRequestDetail.get(object.id)
		newObject = paymentRequestDetailValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}






}
