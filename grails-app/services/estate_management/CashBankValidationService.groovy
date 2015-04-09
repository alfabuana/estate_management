package estate_management

import grails.transaction.Transactional

@Transactional
class CashBankValidationService {

    def serviceMethod() {

    }
	
	def nameNotNull(def object){
		if (object.name == null || object.name == "")
		{
			object.errors.rejectValue('name','null','Nama tidak boleh kosong')
		}
		return object
	}
	def amountNotNull(def object){
		if (object.amount == null)
		{
			object.errors.rejectValue('amount','null','Amount tidak boleh kosong')
		}
		return object
	}
	def isBankNotNull(def object){
		if (object.isBank == null)
		{
			object.errors.rejectValue('isBank','null','isBank tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = nameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = isBankNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = nameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = isBankNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
	

}
