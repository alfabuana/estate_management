package estate_management

import grails.transaction.Transactional

@Transactional
class MaintenanceValidationService {

    def serviceMethod() {

    }
	def descriptionNotNull(def object){
		if (object.description == null || object.description == "")
		{
			object.errors.rejectValue('description','null','Description tidak boleh kosong')
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
	def codeNotNull(def object){
		if (object.code == null || object.code == "")
		{
			object.errors.rejectValue('code','null','Kode tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
}
