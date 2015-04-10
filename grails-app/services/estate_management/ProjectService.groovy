package estate_management

import grails.transaction.Transactional
import java.text.SimpleDateFormat

@Transactional
class ProjectService {
	ProjectValidationService projectValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Project.get(object)
	}
	def getList(){
		return Project.findAll([sort: "id", order: "desc"]){}
	}
	
	def getListUnFinish(){
		return Project.findAll([sort: "id", order: "desc"]){isFinished == false && isDeleted == false}
	}
	def getListConfirm(){
		return Project.findAll([sort: "id", order: "desc"]){isConfirmed == true && isDeleted == false}
	}
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "PJ-"+now+"-"+object.id
		return code
	}
	def calculateTotal(def objectId){
		def valObject = Project.read(objectId)
		Double totalAgree = 0
		Double totalDisagree = 0 
		for (i in valObject.projectVotes.findAll{it.isDeleted == false})
		{
			if (i.isAgree == true){
				totalAgree = totalAgree + 1
			}
			else
			{
				totalDisagree = totalDisagree + 1
			}
		}
		valObject.amountAgree = totalAgree
		valObject.amountDisagree = totalDisagree
		valObject.save()
		return valObject
	}
	
	def createObject(object){
		object.amountAgree = 0
		object.amountDisagree = 0
		object.isDeleted = false
		object.isConfirmed = false
		object.isFinished = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = projectValidationService.createObjectValidation(object as Project)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
			object.code = createCode(object)
			object = object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Project.read(object.id)
		valObject.title = object.title
		valObject.description = object.description
		valObject.complaint = object.complaint
		valObject.code = object.code
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = projectValidationService.updateObjectValidation(valObject)
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
		def newObject = Project.get(object.id)
		newObject = projectValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}

	}
	def confirmObject(def object){
		def newObject = Project.get(object.id)
		newObject = projectValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			newObject.save()
		}

	}
	def unConfirmObject(def object){
		def newObject = Project.get(object.id)
		newObject = projectValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			newObject.save()
		}

	}
	def finishObject(def object){
		def newObject = Project.get(object.id)
		newObject = projectValidationService.finishObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isFinished = true
			newObject.finishDate = new Date()
			newObject.save()
		}

	}
	def unFinishObject(def object){
		def newObject = Project.get(object.id)
		newObject = projectValidationService.unFinishObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isFinished = false
			newObject.finishDate = null
			newObject.save()
		}

	}
}
