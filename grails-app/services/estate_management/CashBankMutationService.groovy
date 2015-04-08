package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class CashBankMutationService {
	CashBankMutationValidationService cashBankMutationValidationService
	CashMutationService	cashMutationService
	UserService userService
	
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
		object.createdBy = userService.getObjectByUserName(object.username)
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
		valObject.updatedBy = userService.getObjectByUserName(object.username)
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
		return newObject
	}
	def confirmObject(def object){
		def newObject = CashBankMutation.get(object.id)
		newObject = cashBankMutationValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			CashBank cashBank = CashBank.find {id == newObject.sourceCashBank.id	}
			def status = "minus"
			def sourceDocumentType = "CashBankMutation"
			def sourceDocumentCode = newObject.code
			def sourceDocumentId = newObject.id
			def amount = newObject.amount
			def mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
				sourceDocumentType, sourceDocumentCode, sourceDocumentId,
				 amount, mutationDate)
			
			 cashBank = CashBank.find {id == newObject.targetCashBank.id	}
			 status = "plus"
			 sourceDocumentType = "CashBankMutation"
			 sourceDocumentCode = newObject.code
			 sourceDocumentId = newObject.id
			 amount = newObject.amount
			 mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
				sourceDocumentType, sourceDocumentCode, sourceDocumentId,
				 amount, mutationDate)
			newObject.save()
		}
		return newObject
	}
	
	def unConfirmObject(def object){
		def newObject = CashBankMutation.get(object.id)
		newObject = cashBankMutationValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			
			CashBank cashBank = CashBank.find {id == newObject.sourceCashBank.id	}
			def status = "plus"
			def sourceDocumentType = "CashBankMutation"
			def sourceDocumentCode = newObject.code
			def sourceDocumentId = newObject.id
			def amount = newObject.amount
			def mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
				sourceDocumentType, sourceDocumentCode, sourceDocumentId,
				 amount, mutationDate)
			
			 cashBank = CashBank.find {id == newObject.targetCashBank.id	}
			 status = "minus"
			 sourceDocumentType = "CashBankMutation"
			 sourceDocumentCode = newObject.code
			 sourceDocumentId = newObject.id
			 amount = newObject.amount
			 mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
				sourceDocumentType, sourceDocumentCode, sourceDocumentId,
				 amount, mutationDate)
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			newObject.save()
		}
		return newObject
	}


	
}
