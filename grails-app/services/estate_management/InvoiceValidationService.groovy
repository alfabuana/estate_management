package estate_management

import grails.transaction.Transactional

@Transactional
class InvoiceValidationService {

    def serviceMethod() {

    }
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
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
	def invoiceDateNotNull(def object){
		if (object.invoiceDate == null || object.invoiceDate == "")
		{
			object.errors.rejectValue('invoiceDate','null','Invoice Date tidak boleh kosong')
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
//	def dueDateNotNull(def object){
//		if (object.dueDate == null || object.dueDate == "")
//		{
//			object.errors.rejectValue('dueDate','null','Due Date tidak boleh kosong')
//		}
//		return object
//	}
	def totalAmountNotNull(def object){
		if (object.totalAmount == null || object.totalAmount == "")
		{
			object.errors.rejectValue('totalAmount','null','Total Amount tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = invoiceDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
//		object = dueDateNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = totalAmountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = invoiceDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
//		object = dueDateNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = totalAmountNotNull(object)
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
