package estate_management

import grails.transaction.Transactional

@Transactional
class CashBankService {
	CashBankValidationService cashBankValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return CashBank.get(object)
	}
	def getList(){
		return CashBank.getAll()
	}
	def getListDeleted(){
		return CashBank.findAll{isDeleted == false}
	}
	
	def createObject(object){
		object.isDeleted = false
		object = cashBankValidationService.createObjectValidation(object as CashBank)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = CashBank.read(object.id)
		valObject.name = object.name
		valObject.description = object.description
		valObject.amount = Double.parseDouble(object.amount)
		valObject.isBank = object.isBank
		valObject = cashBankValidationService.updateObjectValidation(valObject)
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
		def newObject = CashBank.get(object.id)
		newObject = cashBankValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
	
}
