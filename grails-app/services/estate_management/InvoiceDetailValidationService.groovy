package estate_management

import grails.transaction.Transactional

@Transactional
class InvoiceDetailValidationService {

    def serviceMethod() {

    }
	def invoiceNotNull(def object){
		if (object.invoice == null || object.invoice == "")
		{
			object.errors.rejectValue('invoice','null','Invoice tidak boleh kosong')
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
	def createObjectValidation(def object)
	{
		object = invoiceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = invoiceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
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
