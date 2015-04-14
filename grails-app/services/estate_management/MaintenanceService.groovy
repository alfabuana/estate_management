package estate_management

import grails.converters.JSON
import grails.transaction.Transactional
import java.text.SimpleDateFormat

@Transactional
class MaintenanceService {
	MaintenanceValidationService maintenanceValidationService
	UserService userService
	HomeService homeService
	InvoiceService invoiceService
	InvoiceDetailService invoiceDetailService

	def serviceMethod() {

	}
	def getObjectById(def object){
		return Maintenance.get(object)
	}
	def getList(){
		return Maintenance.findAll([sort: "id", order: "desc"]){}
	}
	def createCode(object)
	{
		Date curDate = new Date()
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String now = format.format(curDate)
		String code = "MF-"+now+"-"+object.id
		return code
	}
	def createObject(object){
		object.isDeleted = false
		object.isConfirmed = false
		object.createdBy = userService.getObjectByUserName(object.username)
		object = maintenanceValidationService.createObjectValidation(object as Maintenance)
		if (object.errors.getErrorCount() == 0)
		{
			object =object.save()
			object.code = createCode(object)
			object = object.save()
		}
		return object
	}
	def updateObject(def object){
		def valObject = Maintenance.read(object.id)
		valObject.description = object.description
		valObject.amount = Double.parseDouble(object.amount)
		valObject.code = object.code
		valObject.maintenanceDate = object.maintenanceDate
		valObject.dueDate = object.dueDate
		valObject.updatedBy = userService.getObjectByUserName(object.username)
		valObject = maintenanceValidationService.updateObjectValidation(valObject)
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
		def newObject = Maintenance.get(object.id)
		newObject = maintenanceValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject

	}
	
	def confirmObject(def object){
		def newObject = Maintenance.get(object.id)
		newObject = maintenanceValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			newObject.confirmedBy = userService.getObjectByUserName(object.username)
			newObject.save()
			for (home in homeService.getListDeleted())
			{
				def invoice = [
					home:home,
					username:object.username,
					maintenance:newObject,
					code:"MF${object.username}",
					invoiceDate:newObject.maintenanceDate,
					description:newObject.description,
					dueDate:newObject.dueDate,
					totalAmount:0
				]
				invoice = invoiceService.createObject(invoice)
				def invoiceDetail = [
					invoiceId : invoice.id,
					code:"MFD${object.username}",
					amount:newObject.amount,
					description:newObject.description
				]
				invoiceDetail = invoiceDetailService.createObject(invoiceDetail)
				def invoiceConfirm = [
					id:invoice.id.toString(),
					username:object.username,
				]
				
				invoice = invoiceService.confirmObject(invoiceConfirm)
			}
		}
		return newObject

	}
	def unConfirmObject(def object){
		def newObject = Maintenance.get(object.id)
		newObject = maintenanceValidationService.unConfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			newObject.confirmedBy = null
			newObject.save()
		}
		return newObject

	}
}
