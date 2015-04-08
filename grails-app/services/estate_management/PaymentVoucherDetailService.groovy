package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentVoucherDetailService {
	PaymentVoucherDetailValidationService paymentVoucherDetailValidationService
	PaymentVoucherService paymentVoucherService
	UserService userService
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
		object.createdBy = userService.getObjectByUserName(object.username)
		object = paymentVoucherDetailValidationService.createObjectValidation(object as PaymentVoucherDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
			paymentVoucherService.calculateTotal(object.paymentVoucher.id)
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
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = paymentVoucherDetailValidationService.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
			valObject.save()
			paymentVoucherService.calculateTotal(valObject.paymentVoucher.id)
			
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
			paymentVoucherService.calculateTotal(newObject.paymentVoucher.id)
		}

	}
	def confirmObject(def object){
		def newObject = PaymentVoucherDetail.get(object.id)
		newObject = paymentVoucherDetailValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = newObject.confirmationDate
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
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
			newObject.confirmedBy = null
			newObject.save()
		}
	}
}
