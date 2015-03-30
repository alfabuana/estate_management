package estate_management

import grails.transaction.Transactional

@Transactional
class ReceivableValidationService {

    def serviceMethod() {

    }
	def usernameNotNull(def object){
		if (object.username == null || object.username == "")
		{
			object.errors.rejectValue('username','null','Username tidak boleh kosong')
		}
		return object
	}
	def receivableSourceNotNull(def object){
		if (object.receivableSource == null || object.receivableSource == "")
		{
			object.errors.rejectValue('receivableSource','null','Receivable Source tidak boleh kosong')
		}
		return object
	}
	def receivableSourceIdNotNull(def object){
		if (object.receivableSourceId == null || object.receivableSourceId == "")
		{
			object.errors.rejectValue('receivableSourceId','null','Receivable Source Id tidak boleh kosong')
		}
		return object
	}
	def receivableSourceDetailIdNotNull(def object){
		if (object.receivableSourceDetailId == null || object.receivableSourceDetailId == "")
		{
			object.errors.rejectValue('receivableSourceDetailId','null','Receivable Source Detail Id tidak boleh kosong')
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
		object = receivableSourceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = receivableSourceIdNotNull(object)
		if (object.errors.hasErrors()) return object
		object = receivableSourceDetailIdNotNull(object)
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
		object = receivableSourceNotNull(object)
		if (object.errors.hasErrors()) return object
		object = receivableSourceIdNotNull(object)
		if (object.errors.hasErrors()) return object
		object = receivableSourceDetailIdNotNull(object)
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
