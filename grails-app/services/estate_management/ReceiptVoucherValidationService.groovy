package estate_management

import grails.transaction.Transactional

@Transactional
class ReceiptVoucherValidationService {

    def serviceMethod() {

    }
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Usernam tidak boleh kosong')
		}
		return object
	}
	def cashBankNotNull(def object){
		if (object.cashBank == null || object.cashBank == "")
		{
			object.errors.rejectValue('cashBank','null','Cash Bank tidak boleh kosong')
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
	def receiptDateNotNull(def object){
		if (object.receiptDate == null || object.receiptDate == "")
		{
			object.errors.rejectValue('receiptDate','null','Receipt Date tidak boleh kosong')
		}
		return object
	}
	def isGBCHNotNull(def object){
		if (object.isGBCH == null || object.isGBCH == "")
		{
			object.errors.rejectValue('isGBCH','null','IsGBCH tidak boleh kosong')
		}
		return object
	}
	def dueDateNotNull(def object){
		if (object.dueDate == null || object.dueDate == "")
		{
			object.errors.rejectValue('dueDate','null','Due Date tidak boleh kosong')
		}
		return object
	}
	def totalAmountNotNull(def object){
		if (object.totalAmount == null || object.totalAmount == "")
		{
			object.errors.rejectValue('totalAmount','null','Total Amount tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = cashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = receiptDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = isGBCHNotNull(object)
		if (object.errors.hasErrors()) return object
		object = dueDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = totalAmountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = cashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = receiptDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = isGBCHNotNull(object)
		if (object.errors.hasErrors()) return object
		object = dueDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = totalAmountNotNull(object)
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
