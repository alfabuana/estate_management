package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentVoucherDetailValidationService {

	def serviceMethod() {

	}
	def paymentVoucherNotNull(def object){
		if (object.paymentVoucher == null || object.paymentVoucher == "")
		{
			object.errors.rejectValue('paymentVoucher','null','Payment Voucher tidak boleh kosong')
		}
		return object
	}
	def payableNotNull(def object){
		if (object.payable == null || object.payable == "")
		{
			object.errors.rejectValue('payable','null','Payable tidak boleh kosong')
		}
		return object
	}
	def codeNotNull(def object){
		if (object.code == null || object.code == "")
		{
			object.errors.rejectValue('code','null','Kode tidak boleh kosong')
		}
		return object
	}
	def amountNotNull(def object){
		if (object.amount == null || object.amount == "")
		{
			object.errors.rejectValue('amount','null','Amount tidak boleh kosong')
		}
		return object
	}
	def descriptionNotNull(def object){
		if (object.description == null || object.description == "")
		{
			object.errors.rejectValue('description','null','Description tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = paymentVoucherNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableNotNull(object)
		if (object.errors.hasErrors()) return object
//		object = codeNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = paymentVoucherNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableNotNull(object)
		if (object.errors.hasErrors()) return object
//		object = codeNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
	def confirmObjectValidation(object)
	{
		return object
	}
	def unConfirmObjectValidation(object)
	{
		return object
	}

}
