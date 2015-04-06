package estate_management

import grails.transaction.Transactional

@Transactional
class CashBankAdjustmentService {
	CashBankAdjustmentValidationService cashBankAdjustmentValidationService
	CashMutationService	cashMutationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return CashBankAdjustment.get(object)
	}
	def getList(){
		return CashBankAdjustment.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object = cashBankAdjustmentValidationService.createObjectValidation(object as CashBankAdjustment)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = CashBankAdjustment.read(object.id)
		valObject.cashBank = object.cashBank
		valObject.adjustmentDate = object.adjustmentDate
		valObject.amount = Double.parseDouble(object.amount)
		valObject.code = object.code
		valObject = cashBankAdjustmentValidationService.updateObjectValidation(valObject)
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
		def newObject = CashBankAdjustment.get(object.id)
		newObject = cashBankAdjustmentValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
	def confirmObject(def object){
		def newObject = CashBankAdjustment.get(object.id)
		newObject = cashBankAdjustmentValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			CashBank cashBank = CashBank.find {id == newObject.cashBank.id	}
			def status = "plus"
			def sourceDocumentType = "cashBankAdjustment"
			def sourceDocumentCode = newObject.code
			def sourceDocumentId = newObject.id
			def amount = newObject.amount
			def mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
					sourceDocumentType, sourceDocumentCode, sourceDocumentId,
					amount, mutationDate)
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = CashBankAdjustment.get(object.id)
		newObject = cashBankAdjustmentValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			
			CashBank cashBank = CashBank.find {id == newObject.cashBank.id	}
			def status = "minus"
			def sourceDocumentType = "cashBankAdjustment"
			def sourceDocumentCode = newObject.code
			def sourceDocumentId = newObject.id
			def amount = newObject.amount
			def mutationDate = newObject.confirmationDate
			cashMutationService.createObject(cashBank, status,
					sourceDocumentType, sourceDocumentCode, sourceDocumentId,
					amount, mutationDate)
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}


}
