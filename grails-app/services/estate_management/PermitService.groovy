package estate_management

import grails.converters.JSON
import grails.transaction.Transactional
import java.text.SimpleDateFormat

@Transactional
class PermitService {
	PermitValidationService permitValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Permit.get(object)
	}
	def getList(){
		return Permit.findAll([sort: "id", order: "desc"]){}
	}
	def getListDeleted(){
		return Permit.findAll([sort: "id", order: "desc"]){
			isDeleted == false
		}
	}
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "PM-"+now+"-"+object.id
		return code
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.isCleared = false
		object.code = "0"
		object.createdBy = userService.getObjectByUserName(object.username)
		object = permitValidationService.createObjectValidation(object as Permit)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
			object.code = createCode(object)
			object = object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = Permit.read(object.id)
		valObject.constructionType = object.constructionType
		valObject.vendor = object.vendor
		valObject.home = object.home
		valObject.description = object.description
		valObject.numberIMB = object.numberIMB
		valObject.amountDeposit = Double.parseDouble(object.amountDeposit)
		valObject.estimateWorkDays = Integer.parseInt(object.estimateWorkDays)
		valObject.startDate = object.startDate
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = permitValidationService.updateObjectValidation(valObject)
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
		def newObject = Permit.get(object.id)
		newObject = permitValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	def confirmObject(def object){
		def newObject = Permit.get(object.id)
		newObject = permitValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			
			Receivable receivable = new Receivable()
			receivable.user = userService.getObjectByUserName(object.username)
			receivable.receivableSource = "permit"
			receivable.receivableSourceId = newObject.id
			receivable.receivableSourceDetailId = newObject.id
			receivable.code = newObject.code
			receivable.dueDate = null
			receivable.amount = newObject.amountDeposit
			receivable.remainingAmount = newObject.amountDeposit
			receivable.pendingClearanceAmount = 0
			receivable.isCompleted = false
			receivable.isDeleted = false
			receivable.save()
			newObject.save()
		}
		return newObject
	}
	def unConfirmObject(def object){
		def newObject = Permit.get(object.id)
		newObject = permitValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			Receivable receivable = Receivable.find{
				receivableSource == "permit"&&
				receivableSourceId == newObject.id &&
				receivableSourceDetailId == newObject.id &&
				isDeleted == false
			}
			receivable.isDeleted = true
			receivable.save()
			newObject.save()
		}
		return newObject
	}
	
	def clearObject(def object){
		def newObject = Permit.get(object.id)
		
		newObject = permitValidationService.clearObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isCleared = true
			newObject.clearanceDate = new Date()
			Payable payable = new Payable()
			payable.user = userService.getObjectByUserName(object.username)
			payable.payableSource = "permit"
			payable.payableSourceId = newObject.id
			payable.payableSourceDetailId = newObject.id
			payable.code = newObject.code
			payable.dueDate = null
			payable.amount = newObject.amountDeposit
			payable.remainingAmount = newObject.amountDeposit
			payable.pendingClearanceAmount = 0
			payable.isCompleted = false
			payable.isDeleted = false
			payable.save()
			newObject.save()
		}
		return newObject
	}
	
	def unClearObject(def object){
		def newObject = Permit.get(object.id)
		newObject = permitValidationService.unClearObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isCleared = false
			newObject.clearanceDate = null
			newObject.confirmedBy = null
			Payable payable = Payable.find{
				payableSource == "permit"&&
				payableSourceId == newObject.id &&
				payableSourceDetailId == newObject.id &&
				isDeleted == false
			}
			payable.isDeleted = true
			payable.save()
			newObject.save()
		}
		return newObject
	}

	def printObject(def object){
		def newObject = Permit.get(object.id)
		newObject = permitValidationService.printObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			
		}
		return newObject
	}
}
