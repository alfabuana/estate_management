package estate_management

import grails.transaction.Transactional

@Transactional
class HomeDetailService {
	HomeDetailValidationService homeDetailValidationService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return HomeDetail.get(object)
	}
	def getList(){
		return HomeDetail.getAll()
	}
	def getListForMasterHome(def object){
		return HomeDetail.findAll{
			home.id == object && isDeleted == false
		}
	}
	def getListForMasterUser(def object){
		return HomeDetail.findAll{
			username.id == object && isDeleted == false
		}
	}
	def createObject(object){
		object.isDeleted = false
		object = homeDetailValidationService.createObjectValidation(object as HomeDetail)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = HomeDetail.read(object.id)
		valObject.home = object.home
		valObject.username = object.username
		valObject = homeDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = HomeDetail.get(object.id)
		newObject = homeDetailValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
}
