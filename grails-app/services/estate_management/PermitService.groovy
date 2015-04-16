package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

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
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.isCleared = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = permitValidationService.createObjectValidation(object as Permit)
		if (object.errors.getErrorCount() == 0)
		{
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
		valObject.estimateWorkDays = Integer.parseInteger(object.estimateWorkDays)
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
			newObject.save()
		}
		return newObject
	}

}
