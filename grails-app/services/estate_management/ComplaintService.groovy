package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class ComplaintService {
	ComplaintValidationService complaintValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Complaint.get(object)
	}
	def getList(){
		return Complaint.getAll()
	}
	def getListDeleted(){
		return Complaint.findAll{isDeleted == false}
	}
	def createObject(object){
		object.username = ShiroUser.find{
			username == object.username
		}
		object.isDeleted = false
		object.isConfirmed = false
		object.isCleared = false
		object = complaintValidationService.createObjectValidation(object as Complaint)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
	}
	def updateObject(def object){
		
		def valObject = Complaint.read(object.id)
//		valObject.username = ShiroUser.find{
//			username == object.username
//		}
//		valObject.username = object.username
		valObject.description = object.description
		valObject.title = object.title
		valObject.home = object.home
		valObject = complaintValidationService.updateObjectValidation(valObject)
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
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date() 
			newObject.save()
		}

	}
	def unConfirmObject(def object){
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}

	}
	def clearObject(def object){
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.clearObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isCleared = true
			newObject.clearDate = new Date()
			newObject.save()
		}

	}
	def unClearObject(def object){
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.unClearObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isCleared = false
			newObject.clearDate = null
			newObject.save()
		}

	}
}
