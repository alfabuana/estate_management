package estate_management

import grails.transaction.Transactional

@Transactional
class HomeDetailValidationService {

    def serviceMethod() {

    }
	def homeNotNull(def object){
		if (object.home == null || object.home == "")
		{
			object.errors.rejectValue('home','null','Home tidak boleh kosong')
		}
		return object
	}
	def usernameNotNull(def object){
		if (object.user == null || object.user == "")
		{
			object.errors.rejectValue('user','null','Username tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
}
