package estate_management

import grails.converters.JSON
import grails.transaction.Transactional
import java.text.SimpleDateFormat

@Transactional
class ComplaintService {
	ComplaintValidationService complaintValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Complaint.get(object)
	}
	def getList(){
		return Complaint.findAll([sort: "id", order: "desc"]){}
	}
	def getListDeleted(){
		return Complaint.findAll([sort: "id", order: "desc"]){isDeleted == false}
	}
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "CT-"+now+"-"+object.id
		return code
	}
	def createObject(object){
		object.user = userService.getObjectByUserName(object.username)
		object.isDeleted = false
		object.isConfirmed = false
		object.isCleared = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = complaintValidationService.createObjectValidation(object as Complaint)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
			object.code = createCode(object)
			object = object.save()
		}
		return object
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
		valObject.code = object.code
		valObject.updatedBy = userService.getObjectByUserName(object.username)
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
		return newObject
	}
	def confirmObject(def object){
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.confirmObjectValidation(newObject)
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
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
		return newObject
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
		return newObject
	}
	
	def unClearObject(def object){
		def newObject = Complaint.get(object.id)
		newObject = complaintValidationService.unClearObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isCleared = false
			newObject.clearDate = null
			newObject.confirmedBy = null
			newObject.save()
		}
		return newObject
	}
}
