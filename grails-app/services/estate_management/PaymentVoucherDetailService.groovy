package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentVoucherDetailService {
	PaymentVoucherDetailValidationService paymentVoucherDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return PaymentVoucherDetail.get(object)
	}
	def getList(){
		return PaymentVoucherDetail.getAll()
	}
	def getList(object){
		def a = object.toLong()
		return PaymentVoucherDetail.findAll("from PaymentVoucherDetail as b where b.paymentVoucher.id=? and b.isDeleted =false",[a])
	}
	def createObject(object){
		object.paymentVoucher = PaymentVoucher.get(object.paymentVoucherId)
		object.isDeleted = false
		object.isConfirmed = false
		object.amount = object.payable.amount
		object = paymentVoucherDetailValidationService.createObjectValidation(object as PaymentVoucherDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = PaymentVoucherDetail.read(object.id)
//		valObject.paymentVoucher = object.paymentVoucher
		valObject.payable = object.payable
		valObject.code = object.code
		valObject.amount = object.payable.amount
		valObject.description = object.description
		valObject = paymentVoucherDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = PaymentVoucherDetail.get(object.id)
		newObject = paymentVoucherDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = PaymentVoucherDetail.get(object.id)
		newObject = paymentVoucherDetailValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = newObject.confirmationDate
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = PaymentVoucherDetail.get(object.id)
		newObject = paymentVoucherDetailValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}
}
