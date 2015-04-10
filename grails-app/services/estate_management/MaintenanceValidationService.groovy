package estate_management

import grails.transaction.Transactional

@Transactional
class MaintenanceValidationService {

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
	def descriptionNotNull(def object){
		if (object.description == null || object.description == "")
		{
			object.errors.rejectValue('description','null','Description tidak boleh kosong')
		}
		return object
	}
	def amountNotNull(def object){
		if (object.amount == null || object.amount == "")
		{
			object.errors.rejectValue('amount','null','Amount tidak boleh kosong')
		}
		return object
	}
	def codeNotNull(def object){
		if (object.code == null || object.code == "")
		{
			object.errors.rejectValue('code','null','Kode tidak boleh kosong')
		}
		return object
	}
	def maintenanceDateNotNull(def object){
		if (object.maintenanceDate == null || object.maintenanceDate == "")
		{
			object.errors.rejectValue('maintenanceDate','null','maintenanceDate tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = maintenanceDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = maintenanceDateNotNull(object)
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
}
