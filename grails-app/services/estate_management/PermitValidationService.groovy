package estate_management

import grails.transaction.Transactional

@Transactional
class PermitValidationService {

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
			object.errors.rejectValue(null,'null','Belum di confirm')
		}
		return object
	}
	def isCleared(def object){
		if (object.isCleared == true)
		{
			object.errors.rejectValue(null,'null','Sudah di clear')
		}
		return object
	}
	def isNotCleared(def object){
		if (object.isCleared == false)
		{
			object.errors.rejectValue(null,'null','Belum di clear')
		}
		return object
	}
	def constructionTypeNotNull(def object){
		if (object.constructionType == null || object.constructionType == "")
		{
			object.errors.rejectValue('constructionType','null','Construction Type tidak boleh kosong')
		}
		return object
	}
	def vendorNotNull(def object){
		if (object.vendor == null || object.vendor == "")
		{
			object.errors.rejectValue('vendor','null','Vendor tidak boleh kosong')
		}
		return object
	}
	def homeNotNull(def object){
		if (object.home == null || object.home == "")
		{
			object.errors.rejectValue('home','null','Home tidak boleh kosong')
		}
		return object
	}
	def estimateWorkDaysNotNull(def object){
		if (object.estimateWorkDays == null || object.estimateWorkDays == "")
		{
			object.errors.rejectValue('estimateWorkDays','null','Estimate Work Days tidak boleh kosong')
		}
		return object
	}
	
	def amountDepositNotNull(def object){
		if (object.amountDeposit == null || object.amountDeposit == "")
		{
			object.errors.rejectValue('amountDeposit','null','Amount Deposit tidak boleh kosong')
		}
		return object
	}
	
	def startDateNotNull(def object){
		if (object.startDate == null || object.startDate == "")
		{
			object.errors.rejectValue('startDate','null','Start Date tidak boleh kosong')
		}
		return object
	}
	
	def createObjectValidation(def object)
	{
		object = constructionTypeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = vendorNotNull(object)
		if (object.errors.hasErrors()) return object
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = estimateWorkDaysNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountDepositNotNull(object)
		if (object.errors.hasErrors()) return object
		object = startDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = constructionTypeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = vendorNotNull(object)
		if (object.errors.hasErrors()) return object
		object = homeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = estimateWorkDaysNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountDepositNotNull(object)
		if (object.errors.hasErrors()) return object
		object = startDateNotNull(object)
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
		object = isCleared(object)
		if (object.errors.hasErrors()) return object
		object = isNotConfirmed(object)
		return object
	}
	
	def clearObjectValidation(object)
	{
		object = isCleared(object)
		if (object.errors.hasErrors()) return object
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	def unClearObjectValidation(object)
	{
		object = isNotCleared(object)
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
