package estate_management

import grails.transaction.Transactional

@Transactional
class ComplaintDetailService {
	ComplaintDetailValidationService complaintDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ComplaintDetail.get(object)
	}
	def getList(){
		return ComplaintDetail.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object = complaintDetailValidationService.createObjectValidation(object as ComplaintDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = ComplaintDetail.read(object.id)
		valObject.complaint = object.complaint
		valObject.attachmentUrl = object.attachmentUrl
		valObject = complaintDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = ComplaintDetail.get(object.id)
		newObject = complaintDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
}
