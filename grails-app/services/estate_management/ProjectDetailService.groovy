package estate_management

import grails.transaction.Transactional

@Transactional
class ProjectDetailService {
	ProjectDetailValidationService projectDetailValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ProjectDetail.get(object)
	}
	def getList(){
		return ProjectDetail.getAll()
	}
	def getList(object){
		def a = object.toLong()
		return ProjectDetail.findAll("from ProjectDetail as b where b.project.id=? and b.isDeleted =false",[a])
	}
	def createObject(object){
		object.project = Project.get(object.projectId)
		object.isDeleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = projectDetailValidationService.createObjectValidation(object as ProjectDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = ProjectDetail.read(object.id)
//		valObject.project = object.project
		valObject.attachmentUrl = object.attachmentUrl
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = projectDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = ProjectDetail.get(object.id)
		newObject = projectDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject

	}
}
