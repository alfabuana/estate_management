package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentRequestDetailValidationService {

    def serviceMethod() {

    }
	def paymentRequestNotNull(def object){
		if (object.paymentRequest == null || object.paymentRequest == "")
		{
			object.errors.rejectValue('paymentRequest','null','Payment Request tidak boleh kosong')
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
		if (object.amount == null)
		{
			object.errors.rejectValue('amount','null','Amount tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = paymentRequestNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = paymentRequestNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
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
