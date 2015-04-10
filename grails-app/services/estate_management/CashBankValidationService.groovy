package estate_management

import grails.transaction.Transactional

@Transactional
class CashBankValidationService {

    def serviceMethod() {

    }
	def isDeleted(def object){
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
	}
	def cashBankIsUsed(def object){
		if(PaymentVoucher.find{cashBank.id == object.id}!=null
		|| ReceiptVoucher.find{cashBank.id == object.id}!=null
		|| CashBankMutation.find{sourceCashBank.id == object.id || targetCashBank.id == object.id}!=null
		|| CashBankAdjustment.find{cashBank.id == object.id}!=null)
		{
			object.errors.rejectValue(null,'null','Cash Bank sudah digunakan')
		}
		return object
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
		object = cashBankIsUsed(object)
		if (object.errors.hasErrors()) return object
//		object = isDeleted(object)
//		if (object.errors.hasErrors()) return object
		return object
	}
	

}
