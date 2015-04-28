package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class VendorService {
	VendorValidationService vendorValidationService
	UserService userService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Vendor.get(object)
	}
	def getList(){
		return Vendor.getAll()
	}
	def getListDeleted(){
		return Vendor.findAll{isDeleted == false}
	}
	
	def createObject(object){
		object.isDeleted = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = vendorValidationService.createObjectValidation(object as Vendor)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = Vendor.read(object.id)
		valObject.name = object.name
		valObject.description = object.description
		valObject.telephone = object.telephone
		valObject.fax = object.fax
		valObject.email = object.email
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = vendorValidationService.updateObjectValidation(valObject)
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
		def newObject = Vendor.get(object.id)
		newObject = vendorValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}



}
