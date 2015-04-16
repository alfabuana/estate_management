package estate_management

import grails.transaction.Transactional

@Transactional
class ParkingRegistrationValidationService {

    def serviceMethod() {

    }
	
	def isDeleted(def object)
	{
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
	}
	
	def homeNotNull(def object)
	{
		if (object.home == null || object.home == "")
		{
			object.errors.rejectValue('home','null','Home tidak boleh kosong')
		}
		return object
	}
	
	def carNumberNotNull(def object)
	{
		if (object.carNumber == null || object.carNumber == "")
		{
			object.errors.rejectValue('carNumber','null','Car Number tidak boleh kosong')
		}
		return object
	}
	
	def isConfirmed(def object)
	{
		if (object.isConfirmed == true)
		{
			object.errors.rejectValue(null,'null','Sudah terconfirm')
		}
		return object
	}
	
	def isNotConfirmed(def object)
	{
		if (object.isConfirmed == false)
		{
			object.errors.rejectValue(null,'null','Belum terconfirm')
		}
		return object
	}
	
	def createObjectValidation(def object)
	{
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = carNumberNotNull(object)
		return object
	}
	
	def updateObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = carNumberNotNull(object)
		return object
	}
	
	def softDeleteObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = isDeleted(object)
		return object
	}
	
	def confirmObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	
	def unConfirmObjectValidation(def object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}

}