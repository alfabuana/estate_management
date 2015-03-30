package estate_management

import grails.transaction.Transactional

@Transactional
class CashBankAdjustmentValidationService {

    def serviceMethod() {

    }
	def cashBankNotNull(def object){
		if (object.cashBank == null || object.cashBank == "")
		{
			object.errors.rejectValue('cashBank','null','Cash Bank tidak boleh kosong')
		}
		return object
	}
	def adjustmentDateNotNull(def object){
		if (object.adjustmentDate == null)
		{
			object.errors.rejectValue('adjustmentDate','null','Adjustment Date tidak boleh kosong')
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
	def codeNotNull(def object){
		if (object.code == null || object.code == "")
		{
			object.errors.rejectValue('code','null','Kode tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = cashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = adjustmentDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = codeNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = cashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = adjustmentDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = codeNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}
	def confirmObjectValidation(object)
	{
		return object
	}
	def unConfirmObjectValidation(object)
	{
		return object
	}

}
