package estate_management

import grails.transaction.Transactional

@Transactional
class ComplaintDetailValidationService {

    def serviceMethod() {

    }
	def complaintNotNull(def object){
		if (object.complaint == null || object.complaint == "")
		{
			object.errors.rejectValue('complaint','null','Complaint tidak boleh kosong')
		}
		return object
	}
	def attachmentUrlNotNull(def object){
		if (object.attachmentUrl == null || object.attachmentUrl == "")
		{
			object.errors.rejectValue('attachmentUrl','null','Attachment Url tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = complaintNotNull(object)
		if (object.errors.hasErrors()) return object
		object = attachmentUrlNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = complaintNotNull(object)
		if (object.errors.hasErrors()) return object
		object = attachmentUrlNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
}
