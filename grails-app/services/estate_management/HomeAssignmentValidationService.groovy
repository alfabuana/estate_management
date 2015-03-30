package estate_management

import grails.transaction.Transactional

@Transactional
class HomeAssignmentValidationService {

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
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
		}
		return object
	}
	def assignDateNotNull(def object){
		if (object.assignDate == null || object.assignDate =="")
		{
			object.errors.rejectValue('assignDate','null','Assign Date tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = assignDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = assignDateNotNull(object)
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
