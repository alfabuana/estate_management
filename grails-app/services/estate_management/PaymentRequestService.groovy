package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentRequestService {
	PaymentRequestValidationService paymentRequestValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return PaymentRequest.get(object)
	}
	def getList(){
		return PaymentRequest.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object = paymentRequestValidationService.createObjectValidation(object as PaymentRequest)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = PaymentRequest.read(object.id)
		valObject.username = object.username
		valObject.description = object.description
		valObject.code = object.code
		valObject.amount = Double.parseDouble(object.amount)
		valObject.dueDate = object.dueDate
		valObject.requestDate = object.requestDate
		valObject = paymentRequestValidationService.updateObjectValidation(valObject)
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
		def newObject = PaymentRequest.get(object.id)
		newObject = paymentRequestValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
	def confirmObject(def object){
		def newObject = PaymentRequest.get(object.id)
		newObject = paymentRequestValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = PaymentRequest.get(object.id)
		newObject = paymentRequestValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}





}
