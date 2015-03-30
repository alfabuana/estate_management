package estate_management

import grails.transaction.Transactional

@Transactional
class ProjectVoteValidationService {

    def serviceMethod() {

    }
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
		}
		return object
	}
	def projectNotNull(def object){
		if (object.project == null || object.project == "")
		{
			object.errors.rejectValue('project','null','Project tidak boleh kosong')
		}
		return object
	}
	def isAgreeNotNull(def object){
		if (object.isAgree == null || object.isAgree == "")
		{
			object.errors.rejectValue('isAgree','null','Is Agree tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = projectNotNull(object)
		if (object.errors.hasErrors()) return object
		object = isAgreeNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = projectNotNull(object)
		if (object.errors.hasErrors()) return object
		object = isAgreeNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
}
