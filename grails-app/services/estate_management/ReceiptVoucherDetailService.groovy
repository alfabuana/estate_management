package estate_management

import grails.transaction.Transactional

@Transactional
class ReceiptVoucherDetailService {
	ReceiptVoucherDetailValidationService receiptVoucherDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ReceiptVoucherDetail.get(object)
	}
	def getList(){
		return ReceiptVoucherDetail.getAll()
	}
	def getList(object){
		def a = object.toLong()
		return ReceiptVoucherDetail.findAll("from ReceiptVoucherDetail as b where b.receiptVoucher.id=? and b.isDeleted =false",[a])
	}
	def createObject(object){
		object.receiptVoucher = ReceiptVoucher.get(object.receiptVoucherId)
		object.isDeleted = false
		object.isConfirmed = false
		object = receiptVoucherDetailValidationService.createObjectValidation(object as ReceiptVoucherDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	
	def updateObject(def object){
		def valObject = ReceiptVoucherDetail.read(object.id)
//		valObject.receiptVoucher = object.receiptVoucher
		valObject.receivable = object.receivable
		valObject.code = object.code
		valObject.amount = object.amount
		valObject.description = object.description
		valObject = receiptVoucherDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = ReceiptVoucherDetail.get(object.id)
		newObject = receiptVoucherDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = ReceiptVoucherDetail.get(object.id)
		newObject = receiptVoucherDetailValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = newObject.confirmationDate
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = ReceiptVoucherDetail.get(object.id)
		newObject = receiptVoucherDetailValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}
}
