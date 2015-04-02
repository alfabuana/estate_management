package estate_management

import grails.transaction.Transactional

@Transactional
class ReceiptVoucherService {
	ReceiptVoucherValidationService receiptVoucherValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ReceiptVoucher.get(object)
	}
	def getList(){
		return ReceiptVoucher.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.isReconciled = false
		object = receiptVoucherValidationService.createObjectValidation(object as ReceiptVoucher)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = ReceiptVoucher.read(object.id)
		valObject.username = object.username
		valObject.cashBank = object.cashBank
		valObject.code = object.code
		valObject.receiptDate = object.receiptDate
		valObject.isGBCH = object.isGBCH
//		valObject.dueDate = object.dueDate
		valObject.totalAmount = Double.parseDouble(object.totalAmount)
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
			for (detail in newObject.receiptVoucherDetails.findAll{ it.isDeleted == false })
			{
				detail.isConfirmed = true
				detail.confirmationDate = new Date()
				Receivable receivable = Receivable.find{
					id == newObject.receivable.id
				}
				receivable.remainingAmount =receivable.remainingAmount - detail.amount
				if(receivable.remainingAmount == 0)
				{
					receivable.isCompleted = true
				}
			}
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = ReceiptVoucher.get(object.id)
		newObject = receiptVoucherValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}
}
