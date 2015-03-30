package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidDetailValidationService {

    def serviceMethod() {

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
		object = invoicePaidNotNull(object)
		if (object.errors.hasErrors()) return object
		object = attachmentUrlNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = invoicePaidNotNull(object)
		if (object.errors.hasErrors()) return object
		object = attachmentUrlNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
}
