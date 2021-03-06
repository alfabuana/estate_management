package estate_management

import grails.transaction.Transactional

@Transactional
class ComplaintValidationService {

    def serviceMethod() {

    }
	def isDeleted(def object){
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
	}
	def isConfirmed(def object){
		if (object.isConfirmed == true)
		{
			object.errors.rejectValue(null,'null','Sudah terconfirm')
		}
		return object
	}
	def isNotConfirmed(def object){
		if (object.isConfirmed == false)
		{
			object.errors.rejectValue(null,'null','Belum diconfirm')
		}
		return object
	}
	def isCleared(def object){
		if (object.isCleared == true)
		{
			object.errors.rejectValue(null,'null','Sudah diclear')
		}
		return object
	}
	def isNotCleared(def object){
		if (object.isCleared == false)
		{
			object.errors.rejectValue(null,'null','Belum clear')
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
//		object = descriptionNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = titleNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
//		object = descriptionNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = titleNotNull(object)
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
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def unConfirmObjectValidation(object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = isCleared(object)
		return object
	}
	
	def clearObjectValidation(object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def unClearObjectValidation(object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = isNotCleared(object)
		return object
	}
}
