package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidDetailValidationService {

    def serviceMethod() {

    }
	def isDeleted(def object){
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
	}
	def invoicePaidNotNull(def object){
		if (object.invoicePaid == null || object.invoicePaid == "")
		{
			object.errors.rejectValue('invoicePaid','null','Invoice Paid tidak boleh kosong')
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
		object = attachmentUrlNotNull(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def updateObjectValidation(def object)
	{
		object = attachmentUrlNotNull(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def softdeleteObjectValidation(object)
	{
		object = isDeleted(object)
		if (object.errors.hasErrors()) return object
		return object
	}
}
