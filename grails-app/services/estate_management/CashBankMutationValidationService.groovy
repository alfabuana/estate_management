package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class CashBankMutationValidationService {

    def serviceMethod() {

    }
	def sourceAndTargetCannotSame(def object){
		if (object.sourceCashBank == object.targetCashBank)
		{
			object.errors.rejectValue('targetCashBank','null','Source & Target tidak boleh sama')
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
	def sourceCashBankNotNull(def object){
		if (object.sourceCashBank == null || object.sourceCashBank == "")
		{
			object.errors.rejectValue('sourceCashBank','null','Source Cash Bank tidak boleh kosong')
		}
		return object
	}
	def targetCashBankNotNull(def object){
		if (object.targetCashBank == null || object.sourceCashBank == "")
		{
			object.errors.rejectValue('targetCashBank','null','Target Cash Bank tidak boleh kosong')
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
		object = sourceCashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = targetCashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object  = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = sourceAndTargetCannotSame(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = sourceCashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = targetCashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object  = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = sourceAndTargetCannotSame(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
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
