package estate_management

import estate_management.ShiroRole
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class RoleService {
	RoleValidatorService roleValidatorService = new RoleValidatorService()
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return ShiroRole.get(object)
	}
	
	def getList(){
		return ShiroRole.getAll()
	}
	def getListDeleted(){
		return ShiroRole.findAll{
			isDeleted == false
		}
	}
	
	def createObject(object){
			object.isDeleted = false
			object = roleValidatorService.createObjectValidation(object as ShiroRole)
			
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	
	def updateObject(def object){
		def valObject = ShiroRole.read(object.id)
		valObject.name = object.name
		valObject = roleValidatorService.updateObjectValidation(valObject)
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
	
	def softDeleteObject(def object){
		def newObject = ShiroRole.get(object.id)
		newObject.isDeleted = true
		newObject.save()
	}
	
	def addPermissions(def object){
		def newObject = ShiroRole.get(object.id)
		newObject.addToPermissions(object.permission)
		newObject.save()
	}
	
	def removePermissions(def object){
		def newObject = ShiroRole.get(object.id)
		newObject.removeFromPermissions(object.permission)
		newObject.save()
	}
	
}
