package estate_management

import grails.converters.JSON
import grails.transaction.Transactional
import java.text.SimpleDateFormat

@Transactional
class CashBankAdjustmentService {
	CashBankAdjustmentValidationService cashBankAdjustmentValidationService
	CashMutationService	cashMutationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return CashBankAdjustment.get(object)
	}
	def getList(){
		return CashBankAdjustment.findAll([sort: "id", order: "desc"]){}
	}
	
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "CA-"+now+"-"+object.id  
		return code
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = cashBankAdjustmentValidationService.createObjectValidation(object as CashBankAdjustment)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
			object.code = createCode(object)
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
		valObject.updatedBy = userService.getObjectByUserName(object.username)
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
		return newObject
	}
	def confirmObject(def object){
		def newObject = CashBankAdjustment.get(object.id)
		newObject = cashBankAdjustmentValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
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
		return newObject
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
			newObject.confirmedBy = null
			newObject.save()
		}
		return newObject
	}


}
