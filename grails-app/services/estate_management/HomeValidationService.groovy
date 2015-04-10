package estate_management

import grails.transaction.Transactional

@Transactional
class HomeValidationService {

    def serviceMethod() {

    }
	def isDeleted(def object){
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
	}
	def nameNotNull(def object){
		if (object.name == null || object.name == "")
		{
			object.errors.rejectValue('name','null','Nama tidak boleh kosong')
		}
		return object
	}
	def addressNotNull(def object){
		if (object.address == null || object.address == "")
		{
			object.errors.rejectValue('address','null','Address tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = nameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = addressNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = nameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = addressNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		object = isDeleted(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	

}
