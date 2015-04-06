package estate_management

import grails.converters.JSON
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
	def calculateTotal(def objectId){
		def valObject = PaymentRequest.read(objectId)
		Double total = 0
		for (i in valObject.paymentRequestDetails.findAll{it.isDeleted == false})
		{
			total = total + i.amount
		}
		valObject.amount = total
		valObject.save()
		return valObject
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.amount = 0
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
//		valObject.amount = Double.parseDouble(object.amount)
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
			for (detail in newObject.paymentRequestDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = true
				detail.confirmationDate = new Date()
				Payable payable = new Payable()
				payable.username = newObject.username
				payable.payableSource = "paymentRequest"
				payable.payableSourceId = newObject.id
				payable.payableSourceDetailId = detail.id
				payable.code = newObject.code
				payable.dueDate = newObject.dueDate
				payable.amount = detail.amount
				payable.remainingAmount = detail.amount
				payable.pendingClearanceAmount = 0
				payable.isCompleted = false
				payable.isDeleted = false
				payable.save()
			}
			newObject.save()
		}
		return newObject
	}
	def unConfirmObject(def object){
		def newObject = PaymentRequest.get(object.id)
		println newObject as JSON
		newObject = paymentRequestValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			
			for (detail in newObject.paymentRequestDetails.findAll{ it.isDeleted == false })
			{
				Payable payable = Payable.find{
					payableSource == "paymentRequest"&&
					payableSourceId == newObject.id &&
					payableSourceDetailId == detail.id && 
					isDeleted == false
				}
				payable.isDeleted = true
				payable.save()
				
				detail.isConfirmed = false
				detail.confirmationDate = null
				detail.save()
			}
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
		return newObject
	}





}
