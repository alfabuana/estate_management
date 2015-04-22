package estate_management

import grails.transaction.Transactional
import org.apache.commons.lang3.math.NumberUtils
import java.text.SimpleDateFormat

@Transactional
class ClaimService {
	ClaimValidationService claimValidationService
	UserService userService

    def serviceMethod() {

    }
	def getObjectById(def object){
		return Claim.get(object)
	}
	def getList(){
		return Claim.getAll()
	}
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "CL-"+now+"-"+object.id
		return code
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = claimValidationService.createObjectValidation(object as Claim)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
			object.code = createCode(object)
			object = object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = Claim.read(object.id)
		valObject.permit = object.permit
		valObject.description = object.description
		if (NumberUtils.isNumber(object.amount) ==  true)
		{
			valObject.amount = Double.parseDouble(object.amount)
		}
		else
		{
			valObject.amount = null
		}
		valObject.claimDate = object.claimDate
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = claimValidationService.updateObjectValidation(valObject)
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
		def newObject = Claim.get(object.id)
		newObject = claimValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	def confirmObject(def object){
		def newObject = Claim.get(object.id)
		newObject = claimValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			Receivable receivable = new Receivable()
			receivable.user = userService.getObjectByUserName(object.username)
			receivable.receivableSource = "claim"
			receivable.receivableSourceId = newObject.id
			receivable.receivableSourceDetailId = newObject.id
			receivable.code = newObject.code
			receivable.dueDate = null
			receivable.amount = newObject.amount
			receivable.remainingAmount = newObject.amount
			receivable.pendingClearanceAmount = 0
			receivable.isCompleted = false
			receivable.isDeleted = false
			receivable.save()
			newObject.save()
		}
		return newObject
	}
	def unConfirmObject(def object){
		def newObject = Claim.get(object.id)
		newObject = claimValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			Receivable receivable = Receivable.find{
				receivableSource == "claim"&&
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

	def printObject(def object){
		def newObject = Claim.get(object.id)
		newObject = claimValidationService.printObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			
		}
		return newObject
	}
}
