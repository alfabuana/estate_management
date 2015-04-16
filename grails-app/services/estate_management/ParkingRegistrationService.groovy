package estate_management

import grails.transaction.Transactional

@Transactional
class ParkingRegistrationService {
	ParkingRegistrationValidationService parkingRegistrationValidationService

    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return ParkingRegistration.get(object)
	}
	
	def getList(){
		return ParkingRegistration.getAll()
	}
	
	def getListForParkingRegistration(def object){
		return ParkingRegistration.findAll{
			home.id == object && isDeleted == false
		}
	}
	
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object = parkingRegistrationValidationService.createObjectValidation(object as ParkingRegistration)
		if (object.errors.getErrorCount() == 0)
		{
			object = object.save()
		}

		return object
	}
	
	def updateObject(def object){
		def valObject = ParkingRegistration.read(object.id)
		valObject.home = object.home
		valObject.carNumber = object.carNumber
		valObject = parkingRegistrationValidationService.updateObjectValidation(valObject)
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
		def newObject = ParkingRegistration.get(object.id)
		newObject = parkingRegistrationValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	
	def confirmObject(def object){
		def newObject = ParkingRegistration.get(object.id)
		newObject = parkingRegistrationValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.save()
		}
		return newObject
	}
	
	def unConfirmObject(def object){
		def newObject = ParkingRegistration.get(object.id)
		newObject = parkingRegistrationValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.save()
		}
		return newObject
	}

}
