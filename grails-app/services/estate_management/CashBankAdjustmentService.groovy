package estate_management

import grails.transaction.Transactional

@Transactional
class CashBankAdjustmentService {
	CashBankAdjustmentValidationService cashBankAdjustmentValidationService

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
		valObject.amount = object.amount
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
			newObject.confirmationDate = newObject.confirmationDate
			newObject.save()
		}
	}
	def unConfirmObject(def object){
		def newObject = CashBankAdjustment.get(object.id)
		newObject = cashBankAdjustmentValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
	}


}
