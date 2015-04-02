package estate_management

import grails.transaction.Transactional

@Transactional
class CashBankMutationService {
	CashBankMutationValidationService cashBankMutationValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return CashBankMutation.get(object)
	}
	def getList(){
		return CashBankMutation.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object = cashBankMutationValidationService.createObjectValidation(object as CashBankMutation)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = CashBankMutation.read(object.id)
		valObject.sourceCashBank = object.sourceCashBank
		valObject.targetCashBank = object.targetCashBank
		valObject.amount = Double.parseDouble(object.amount)
		valObject.code = object.code
		valObject = cashBankMutationValidationService.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
			valObject.save()
		}
		else
		{
			valObject.discard()
		}

		return valObject
	}
	def softDeletedObject(def object){
		def newObject = CashBankMutation.get(object.id)
		newObject = cashBankMutationValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
	def confirmObject(def object){
		def newObject = CashBankMutation.get(object.id)
		newObject = cashBankMutationValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = newObject.confirmationDate
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = CashBankMutation.get(object.id)
		newObject = cashBankMutationValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}


	
}
