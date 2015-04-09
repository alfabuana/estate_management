package estate_management

import grails.transaction.Transactional

@Transactional
class ComplaintDetailService {
	ComplaintDetailValidationService complaintDetailValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ComplaintDetail.get(object)
	}
	def getList(){
		return ComplaintDetail.getAll()
	}
	def getList(object){
		def a = object.toLong()
//		return ComplaintDetail.findAll("from ComplaintDetail as b where b.complaint.id=? and b.isDeleted =false",[a])
		return ComplaintDetail.findAll{ 
			complaint.id == object
		}
	}
	def createObject(object){
		object.complaint = Complaint.get(object.complaintId)
		object.isDeleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = complaintDetailValidationService.createObjectValidation(object as ComplaintDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = ComplaintDetail.read(object.id)
//		valObject.complaint = object.complaint
		valObject.attachmentUrl = object.attachmentUrl
		valObject.updatedBy = userService.getObjectByUserName(object.username)
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
