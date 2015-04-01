package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentVoucherService {
	PaymentVoucherValidationService paymentVoucherValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return PaymentVoucher.get(object)
	}
	def getList(){
		return PaymentVoucher.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.isReconciled = false
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
		valObject.totalAmount = Double.parseDouble(object.totalAmount)
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
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = PaymentVoucher.get(object.id)
		newObject = paymentVoucherValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}
}
