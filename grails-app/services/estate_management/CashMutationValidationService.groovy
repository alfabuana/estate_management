package estate_management

import grails.transaction.Transactional

@Transactional
class CashMutationValidationService {

    def serviceMethod() {

    }
	def cashBankNotNull(def object){
		if (object.cashBank == null || object.cashBank == "")
		{
			object.errors.rejectValue('cashBank','null','Cash Bank tidak boleh kosong')
		}
		return object
	}
	def statusNotNull(def object){
		if (object.status == null || object.status == "")
		{
			object.errors.rejectValue('status','null','Status tidak boleh kosong')
		}
		return object
	}
	def sourceDocumentTypeNotNull(def object){
		if (object.sourceDocumentType == null || object.sourceDocumentType == "")
		{
			object.errors.rejectValue('sourceDocumentType','null','Source Document Type tidak boleh kosong')
		}
		return object
	}
	def sourceDocumentCodeNotNull(def object){
		if (object.sourceDocumentCode == null || object.sourceDocumentCode == "")
		{
			object.errors.rejectValue('sourceDocumentCode','null','Source Document Code tidak boleh kosong')
		}
		return object
	}
	def sourceDocumentIdNotNull(def object){
		if (object.sourceDocumentId == null || object.sourceDocumentId == "")
		{
			object.errors.rejectValue('sourceDocumentId','null','Source Document Id tidak boleh kosong')
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
		object = cashBankNotNull(object)
		if (object.errors.hasErrors()) return object
		object = statusNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = sourceDocumentTypeNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = sourceDocumentCodeNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = sourceDocumentIdNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		return object
	}

}
