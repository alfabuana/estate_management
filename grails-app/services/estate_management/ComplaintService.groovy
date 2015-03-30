package estate_management

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
	def createObject(object){
		object.isDeleted = false
		object = complaintValidationService.createObjectValidation(object as Complaint)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Complaint.read(object.id)
		valObject.username = object.username
		valObject.description = object.description
		valObject.title = object.title
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
}
