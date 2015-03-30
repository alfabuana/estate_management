package estate_management

import grails.transaction.Transactional

@Transactional
class PaymentRequestValidationService {

    def serviceMethod() {

    }
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
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
	
	def requestDateNotNull(def object){
		if (object.requestDate == null)
		{
			object.errors.rejectValue('requestDate','null','Request Date tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = requestDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = requestDateNotNull(object)
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

