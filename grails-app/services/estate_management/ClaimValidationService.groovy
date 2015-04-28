package estate_management

import grails.transaction.Transactional

@Transactional
class ClaimValidationService {

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
	def isDeleted(def object){
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
	}
	def permitNotNull(def object){
		if (object.permit == null || object.permit == "")
		{
			object.errors.rejectValue('permit','null','Permit tidak boleh kosong')
		}
		return object
	}
	def claimDateNotNull(def object){
		if (object.claimDate == null || object.claimDate == "")
		{
			object.errors.rejectValue('claimDate','null','Claim Date tidak boleh kosong')
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
	def createObjectValidation(def object)
	{
		object = permitNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = claimDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = permitNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = claimDateNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = isDeleted(object)
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
	def printObjectValidation(object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}
}
