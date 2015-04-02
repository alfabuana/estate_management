package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidValidationService {

    def serviceMethod() {

    }
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
		}
		return object
	}
	def invoiceNotNull(def object){
		if (object.invoice == null || object.invoice == "")
		{
			object.errors.rejectValue('invoice','null','Invoice tidak boleh kosong')
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
	def paidDateNotNull(def object){
		if (object.paidDate == null || object.paidDate == "")
		{
			object.errors.rejectValue('paidDate','null','Paid Date tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = invoiceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = paidDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = invoiceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = paidDateNotNull(object)
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
