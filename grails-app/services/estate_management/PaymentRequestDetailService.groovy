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
	def createObject(object){
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
		valObject.paymentRequest = object.paymentRequest
		valObject.code = object.code
		valObject.amount = object.amount
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
			newObject.confirmationDate = newObject.confirmationDate
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
