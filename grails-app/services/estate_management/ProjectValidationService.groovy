package estate_management

import grails.transaction.Transactional

@Transactional
class ProjectValidationService {

    def serviceMethod() {

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
			object.errors.rejectValue(null,'null','Belum terconfirm')
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
	def descriptionNotNull(def object){
		if (object.description == null || object.description == "")
		{
			object.errors.rejectValue('description','null','Description tidak boleh kosong')
		}
		return object
	}
//	def amountAgreeNotNull(def object){
//		if (object.amountAgree == null || object.amountAgree == "")
//		{
//			object.errors.rejectValue('amountAgree','null','Amount Agree tidak boleh kosong')
//		}
//		return object
//	}
//	def amountDisagreeNotNull(def object){
//		if (object.amountDisagree == null || object.amountDisagree == "")
//		{
//			object.errors.rejectValue('amountDisagree','null','Amount Disagree tidak boleh kosong')
//		}
//		return object
//	}
	def createObjectValidation(def object)
	{
		object = titleNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object = amountDisagreeNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = titleNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object = amountDisagreeNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
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
		return object
	}
	def finishObjectValidation(object)
	{
		return object
	}
	def unFinishObjectValidation(object)
	{
		return object
	}
}
