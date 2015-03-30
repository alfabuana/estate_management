package estate_management

import grails.transaction.Transactional

@Transactional
class PayableValidationService {

    def serviceMethod() {

    }
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
		}
		return object
	}
	def payableSourceNotNull(def object){
		if (object.payableSource == null || object.payableSource == "")
		{
			object.errors.rejectValue('payableSource','null','Payable Source tidak boleh kosong')
		}
		return object
	}
	def payableSourceIdNotNull(def object){
		if (object.payableSourceId == null || object.payableSourceId == "")
		{
			object.errors.rejectValue('payableSourceId','null','Payable Source Id tidak boleh kosong')
		}
		return object
	}
	def payableSourceDetailIdNotNull(def object){
		if (object.payableSourceDetailId == null || object.payableSourceDetailId == "")
		{
			object.errors.rejectValue('payableSourceDetailId','null','Payable Source Detail Id tidak boleh kosong')
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
	def dueDateNotNull(def object){
		if (object.dueDate == null || object.dueDate == "")
		{
			object.errors.rejectValue('dueDate','null','DueDate tidak boleh kosong')
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
	def remainingAmountNotNull(def object){
		if (object.remainingAmount == null || object.remainingAmount == "")
		{
			object.errors.rejectValue('remainingAmount','null','Remaining Amount tidak boleh kosong')
		}
		return object
	}
	def pendingClearanceAmountNotNull(def object){
		if (object.pendingClearanceAmount == null || object.pendingClearanceAmount == "")
		{
			object.errors.rejectValue('pendingClearanceAmount','null','Pending Clearance Amount tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableSourceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableSourceIdNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableSourceDetailIdNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = dueDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = remainingAmountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = pendingClearanceAmountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = usernameNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableSourceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableSourceIdNotNull(object)
		if (object.errors.hasErrors()) return object
		object = payableSourceDetailIdNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = dueDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = remainingAmountNotNull(object)
		if (object.errors.hasErrors()) return object
		object = pendingClearanceAmountNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}

}
