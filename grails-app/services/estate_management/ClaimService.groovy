package estate_management

import grails.transaction.Transactional

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
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = ClaimValidationService.createObjectValidation(object as Claim)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = Claim.read(object.id)
		valObject.permit = object.permit
		valObject.description = object.description
		valObject.amount = Double.parseDouble(object.amount)
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
			newObject.save()
		}
		return newObject
	}

}
