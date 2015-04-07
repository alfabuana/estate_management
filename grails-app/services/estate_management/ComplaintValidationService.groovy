package estate_management

import grails.transaction.Transactional

@Transactional
class ComplaintValidationService {

    def serviceMethod() {

    }
	
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
		}
		return object
	}
	def homeNotNull(def object){
		if (object.home == null || object.home == "")
		{
			object.errors.rejectValue('home','null','Home tidak boleh kosong')
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
	def titleNotNull(def object){
		if (object.title == null || object.title == "")
		{
			object.errors.rejectValue('title','null','Title tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = titleNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = titleNotNull(object)
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
	def clearObjectValidation(object)
	{
		return object
	}
	def unClearObjectValidation(object)
	{
		return object
	}
}
