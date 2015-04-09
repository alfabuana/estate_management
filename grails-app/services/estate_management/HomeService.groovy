package estate_management

import grails.transaction.Transactional

@Transactional
class HomeService {
	HomeValidationService homeValidationService
	UserService userService


    def serviceMethod() {

    }
	def getObjectById(def object){
		return Home.get(object)
	}
	def getList(){
		return Home.getAll()
	}
	
	def getListDeleted(){
		return Home.findAll{isDeleted == false}
	}
	def createObject(object){
		
		object.isDeleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = homeValidationService.createObjectValidation(object as Home)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Home.read(object.id)
		valObject.name = object.name
		valObject.address = object.address
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = homeValidationService.updateObjectValidation(valObject)
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
		def newObject = Home.get(object.id)
		newObject = homeValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
}
}
