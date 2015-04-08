package estate_management

import grails.transaction.Transactional

@Transactional
class ReceiptVoucherService {
	ReceiptVoucherValidationService receiptVoucherValidationService
	CashMutationService	cashMutationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ReceiptVoucher.get(object)
	}
	def getList(){
		return ReceiptVoucher.getAll()
	}
	def calculateTotal(def objectId){
		def valObject = ReceiptVoucher.read(objectId)
		Double total = 0
		for (i in valObject.receiptVoucherDetails.findAll{it.isDeleted == false})
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
		object.totalAmount = 0
		object.createdBy = userService.getObjectByUserName(object.username)
		object = receiptVoucherValidationService.createObjectValidation(object as ReceiptVoucher)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = ReceiptVoucher.read(object.id)
		valObject.user = object.user
		valObject.cashBank = object.cashBank
		valObject.code = object.code
		valObject.receiptDate = object.receiptDate
		valObject.isGBCH = object.isGBCH
		valObject.updatedBy = userService.getObjectByUserName(object.username)
//		valObject.dueDate = object.dueDate
//		valObject.totalAmount = Double.parseDouble(object.totalAmount)
		valObject = receiptVoucherValidationService.updateObjectValidation(valObject)
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
		def newObject = ReceiptVoucher.get(object.id)
		newObject = receiptVoucherValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			for (detail in newObject.receiptVoucherDetails.findAll{ it.isDeleted == false })
			{
				detail.isDeleted = true
			}
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = ReceiptVoucher.get(object.id)
		newObject = receiptVoucherValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			for (detail in newObject.receiptVoucherDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = true
				detail.confirmationDate = new Date()
				detail.confirmedBy = userService.getObjectByUserName(object.username)
				Receivable receivable = Receivable.find{
					id == detail.receivable.id
				}
				receivable.remainingAmount =receivable.remainingAmount - detail.amount
				if(receivable.remainingAmount == 0)
				{
					receivable.isCompleted = true
				}
			}
			CashBank cashBank = CashBank.find {id == newObject.cashBank.id	}
			def status = "plus"
			def sourceDocumentType = "ReceiptVoucher"
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
		def newObject = ReceiptVoucher.get(object.id)
		newObject = receiptVoucherValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			
			for (detail in newObject.receiptVoucherDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = false
				detail.confirmationDate = null
				detail.confirmedBy = null
				Receivable receivable = Receivable.find{
					id == detail.receivable.id
				}
				receivable.remainingAmount =receivable.remainingAmount + detail.amount
				if(receivable.remainingAmount == 0)
				{
					receivable.isCompleted = true
				}
			}
			CashBank cashBank = CashBank.find {id == newObject.cashBank.id	}
			def status = "minus"
			def sourceDocumentType = "ReceiptVoucher"
			def sourceDocumentCode = newObject.code
			def sourceDocumentId = newObject.id
			def amount = newObject.totalAmount
			def mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
				sourceDocumentType, sourceDocumentCode, sourceDocumentId,
				 amount, mutationDate)
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			newObject.save()
		}
		return newObject
	}
}
