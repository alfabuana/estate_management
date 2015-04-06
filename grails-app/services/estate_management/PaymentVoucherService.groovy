package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class PaymentVoucherService {
	PaymentVoucherValidationService paymentVoucherValidationService
	CashMutationService	cashMutationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return PaymentVoucher.get(object)
	}
	def getList(){
		return PaymentVoucher.getAll()
	}
	
	
	def calculateTotal(def objectId){
		def valObject = PaymentVoucher.read(objectId)
		Double total = 0 
		for (i in valObject.paymentVoucherDetails.findAll{it.isDeleted == false})
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
		object.isReconciled = false
		object.totalAmount= 0
		object = paymentVoucherValidationService.createObjectValidation(object as PaymentVoucher)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = PaymentVoucher.read(object.id)
		valObject.username = object.username
		valObject.cashBank = object.cashBank
		valObject.code = object.code
		valObject.paymentDate = object.paymentDate
		valObject.isGBCH = object.isGBCH
		//		valObject.dueDate = object.dueDate
		valObject = paymentVoucherValidationService.updateObjectValidation(valObject)
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
		def newObject = PaymentVoucher.get(object.id)
		newObject = paymentVoucherValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			for (detail in newObject.paymentVoucherDetails.findAll{ it.isDeleted == false })
			{
				detail.isDeleted = true
			}
			newObject.save()
		}
	}
	def confirmObject(def object){
		def newObject = PaymentVoucher.get(object.id)
		newObject = paymentVoucherValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			for (detail in newObject.paymentVoucherDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = true
				detail.confirmationDate = new Date()
				Payable payable = Payable.find{
					id == detail.payable.id
				}
				payable.remainingAmount =payable.remainingAmount - detail.amount
				if(payable.remainingAmount == 0)
				{
					payable.isCompleted = true
				}


			}
			CashBank cashBank = CashBank.find {id == newObject.cashBank.id	}
			def status = "minus"
			def sourceDocumentType = "PaymentVoucher"
			def sourceDocumentCode = newObject.code
			def sourceDocumentId = newObject.id
			def amount = newObject.totalAmount
			def mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
					sourceDocumentType, sourceDocumentCode, sourceDocumentId,
					amount, mutationDate)
			newObject.save()
		}
		return newObject
	}
	def unConfirmObject(def object){
		def newObject = PaymentVoucher.get(object.id)
		newObject = paymentVoucherValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			
			for (detail in newObject.paymentVoucherDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = false
				detail.confirmationDate = null
				Payable payable = Payable.find{
					id == detail.payable.id
				}
				payable.remainingAmount =payable.remainingAmount + detail.amount
				payable.isCompleted = false


			}
			CashBank cashBank = CashBank.find {id == newObject.cashBank.id	}
			def status = "plus"
			def sourceDocumentType = "PaymentVoucher"
			def sourceDocumentCode = newObject.code
			def sourceDocumentId = newObject.id
			def amount = newObject.totalAmount
			def mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
					sourceDocumentType, sourceDocumentCode, sourceDocumentId,
					amount, mutationDate)
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
		return newObject
	}
}
