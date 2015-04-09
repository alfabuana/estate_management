package estate_management

import grails.transaction.Transactional
import java.text.SimpleDateFormat

@Transactional
class ReceiptVoucherDetailService {
	ReceiptVoucherDetailValidationService receiptVoucherDetailValidationService
	ReceiptVoucherService receiptVoucherService
	UserService userService

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
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "RVD-"+now+"-"+object.id
		return code
	}
	def createObject(object){
		object.receiptVoucher = ReceiptVoucher.get(object.receiptVoucherId)
		object.isDeleted = false
		object.isConfirmed = false
		object.amount = object.receivable.amount
		object.createdBy = userService.getObjectByUserName(object.username)
		object = receiptVoucherDetailValidationService.createObjectValidation(object as ReceiptVoucherDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
			receiptVoucherService.calculateTotal(object.receiptVoucher.id)
			object.code = createCode(object)
			object = object.save()
		}
		return object
	}
	
	def updateObject(def object){
		def valObject = ReceiptVoucherDetail.read(object.id)
//		valObject.receiptVoucher = object.receiptVoucher
		valObject.receivable = object.receivable
		valObject.code = object.code
		valObject.amount = object.receivable.amount
		valObject.description = object.description
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = receiptVoucherDetailValidationService.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
			valObject.save()
			receiptVoucherService.calculateTotal(valObject.receiptVoucher.id)
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
			receiptVoucherService.calculateTotal(newObject.receiptVoucher.id)
		}

	}
	def confirmObject(def object){
		def newObject = ReceiptVoucherDetail.get(object.id)
		newObject = receiptVoucherDetailValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = newObject.confirmationDate
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
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
			newObject.confirmedBy = null
			newObject.save()
		}
	}
}
