package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentVoucherDetailValidationService {

	def serviceMethod() {

	}
	def isConfirmed(def object){
		if (object.paymentVoucher.isConfirmed == true)
		{
			object.errors.rejectValue(null,'null','Sudah terconfirm')
		}
		return object
	}
	
	def isDeleted(def object){
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
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
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = paymentVoucherNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = paymentVoucherNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = isDeleted(object)
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
