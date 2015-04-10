package estate_management

import grails.transaction.Transactional

@Transactional
class InvoicePaidValidationService {

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
			object.errors.rejectValue(null,'null','Belum terconfirm')
		}
		return object
	}
	def isCleared(def object){
		if (object.invoice.isCleared == true)
		{
			object.errors.rejectValue(null,'null','Sudah diclear')
		}
		return object
	}
	def isNotClear(def object){
		if (object.invoice.isCleared == false)
		{
			object.errors.rejectValue(null,'null','Belum diclear')
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
	def invoiceNotNull(def object){
		if (object.invoice == null || object.invoice == "")
		{
			object.errors.rejectValue('invoice','null','Invoice tidak boleh kosong')
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
	def paidDateNotNull(def object){
		if (object.paidDate == null || object.paidDate == "")
		{
			object.errors.rejectValue('paidDate','null','Paid Date tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = invoiceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = paidDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = invoiceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = paidDateNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		object = isDeleted(object)
		if (object.errors.hasErrors()) return object
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
	def clearObjectValidation(object)
	{
		object = isCleared(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def unClearObjectValidation(object)
	{
		object = isNotClear(object)
		if (object.errors.hasErrors()) return object
		return object
	}
}
