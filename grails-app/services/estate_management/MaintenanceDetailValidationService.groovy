package estate_management

import grails.transaction.Transactional

@Transactional
class MaintenanceDetailValidationService {

    def serviceMethod() {

    }
	def maintenanceNotNull(def object){
		if (object.maintenance == null || object.maintenance == "")
		{
			object.errors.rejectValue('maintenance','null','Maintenance tidak boleh kosong')
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
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def updateObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
}
