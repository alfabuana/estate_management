package estate_management

import grails.converters.JSON
import org.apache.commons.lang3.math.NumberUtils
import grails.transaction.Transactional
@Transactional
class CashBankService {
	CashBankValidationService cashBankValidationService
	UserService userService
	def serviceMethod() {

	}
	def getObjectById(def object){
		return CashBank.get(object)
	}
	def getList(){
		return CashBank.findAll([sort: "id", order: "desc"]){}
	}
	def getListDeleted(){
		return CashBank.findAll([sort: "id", order: "desc"]){isDeleted == false}
	}
	
	def createObject(object){
		object.isDeleted = false
		object.amount = 0
		object.createdBy = userService.getObjectByUserName(object.username)
		object = cashBankValidationService.createObjectValidation(object as CashBank)
		
		if (object.errors.getErrorCount() == 0)
		{
			object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = CashBank.read(object.id)
		valObject.name = object.name
		valObject.description = object.description
		if (NumberUtils.isNumber(object.amount) ==  true)
		{
			valObject.amount = Double.parseDouble(object.amount)
		}
		else
		{ 
			valObject.amount = null
		}
		valObject.isBank = object.isBank
		valObject.updatedBy = userService.getObjectByUserName(object.username)
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
		return newObject
	}
	
}
