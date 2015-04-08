package estate_management

import grails.transaction.Transactional

@Transactional
class HomeAssignmentService {
	HomeAssignmentValidationService homeAssignmentValidationService
	HomeDetailService homeDetailService
	UserService userService
	def serviceMethod() {

	}
	def getObjectById(def object){
		return HomeAssignment.get(object)
	}
	def getList(){
		return HomeAssignment.getAll()
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = homeAssignmentValidationService.createObjectValidation(object as HomeAssignment)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
		}

		return object
	}
	def updateObject(def object){
		def valObject = HomeAssignment.read(object.id)
		valObject.home = object.home
		valObject.user = userService.getObjectByUserName(object.username)
		valObject.assignDate = object.assignDate
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = homeAssignmentValidationService.updateObjectValidation(valObject)
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
		def newObject = HomeAssignment.get(object.id)
		newObject = homeAssignmentValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	def confirmObject(def object){
		def newObject = HomeAssignment.get(object.id)
		newObject = homeAssignmentValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			newObject.save()

			HomeDetail homeDetail = HomeDetail.find{
				home == newObject.home && user == newObject.user
			}
			if (homeDetail == null)
			{
				homeDetail = new HomeDetail()
				homeDetail.home = newObject.home
				homeDetail.lastAssignDate = newObject.assignDate
				homeDetailService.createObject(homeDetail)
			}
			else
			{
				homeDetail.isDeleted = false
				homeDetail.lastAssignDate = newObject.assignDate
				homeDetailService.updateObject(homeDetail)
			}

		}
		return newObject
	}
	def unConfirmObject(def object){
		def newObject = HomeAssignment.get(object.id)
		newObject = homeAssignmentValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			newObject.save()
			HomeDetail homeDetail = HomeDetail.find{
				home == newObject.home && user == newObject.user
			}
			homeDetail.isDeleted = true
			homeDetail.save()
		}
		return newObject
	}


}
