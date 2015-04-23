package estate_management

import grails.transaction.Transactional

@Transactional
class ReceiptVoucherValidationService {

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
	def hasDetail(def object){
		if(object.receiptVoucherDetails == null || object.receiptVoucherDetails.findAll{ it.isDeleted == false }.size() == 0)
		{
			object.errors.rejectValue(null,'null','Harus memiliki detail')
		}
		return object
	}
	def usernameNotNull(def object){
		if (object.user == null || object.user == "")
		{
			object.errors.rejectValue('user','null','Usernam tidak boleh kosong')
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
//	def dueDateNotNull(def object){
//		if (object.dueDate == null || object.dueDate == "")
//		{
//			object.errors.rejectValue('dueDate','null','Due Date tidak boleh kosong')
//		}
//		return object
//	}
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
		object = receiptDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = totalAmountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = cashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = receiptDateNotNull(object)
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
		object = hasDetail(object)
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
