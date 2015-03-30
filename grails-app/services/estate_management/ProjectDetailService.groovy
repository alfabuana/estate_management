package estate_management

import grails.transaction.Transactional

@Transactional
class ProjectDetailService {
	ProjectDetailValidationService projectDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return ProjectDetail.get(object)
	}
	def getList(){
		return ProjectDetail.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object = projectDetailValidationService.createObjectValidation(object as ProjectDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = ProjectDetail.read(object.id)
		valObject.project = object.project
		valObject.attachmentUrl = object.attachmentUrl
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

	}
}
