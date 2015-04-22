package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class PaymentRequestValidationService {

	def serviceMethod() {

	}
	def isDeleted(def object){
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah dihapus')
		}
		return object
	}
	def isConfirmed(def object){
		if (object.isConfirmed == true)
		{
			object.errors.rejectValue(null,'null','Sudah terconfirm')
		}
		return object
	}
	def isNotConfirmed(def object){
		if (object.isConfirmed == false)
		{
			object.errors.rejectValue(null,'null','Belum terconfirm')
		}
		return object
	}

	def hasDetail(def object){
		if(object.paymentRequestDetails == null || object.paymentRequestDetails.findAll{ it.isDeleted == false }.size() == 0)
		{
			object.errors.rejectValue(null,'null','Harus memiliki detail')
		}
		return object
	}

	def payableNotAssociateWithPaymentVoucher(def object)
	{
		for (detail in object.paymentRequestDetails.findAll{ it.isDeleted == false })
		{
			def paymentVoucherDetail = PaymentVoucherDetail.find {
				payable.payableSource == "paymentRequest" &&
						payable.payableSourceId == object.id &&
						payable.payableSourceDetailId == detail.id &&
						isDeleted == false
			}
			if (paymentVoucherDetail != null)
			{
				object.errors.rejectValue(null,'null','PaymentRequest sudah di buat PaymentVoucher')
				return object
			}
		}
		return object
	}

	def usernameNotNull(def object){
		if (object.user == null || object.user == "")
		{
			object.errors.rejectValue('user','null','Username tidak boleh kosong')
		}
		return object
	}
	def vendorNotNull(def object){
		if (object.vendor == null || object.vendor == "")
		{
			object.errors.rejectValue('vendor','null','vendor tidak boleh kosong')
		}
		return object
	}
	def projectNotNull(def object){
		if (object.project == null || object.project == "")
		{
			object.errors.rejectValue('project','null','project tidak boleh kosong')
		}
		return object
	}
	def amountNotNull(def object){
		if (object.amount == null)
		{
			object.errors.rejectValue('amount','null','Amount tidak boleh kosong')
		}
		return object
	}

	def requestDateNotNull(def object){
		if (object.requestDate == null)
		{
			object.errors.rejectValue('requestDate','null','Request Date tidak boleh kosong')
		}
		return object
	}
	def dueDateNotNull(def object){
		if (object.dueDate == null)
		{
			object.errors.rejectValue('dueDate','null','Due Date tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
		object  = vendorNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = projectNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = requestDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = dueDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object  = vendorNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = projectNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = requestDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = dueDateNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = isDeleted(object)
		return object
	}
	def confirmObjectValidation(object)
	{
		object = isConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = hasDetail(object)
		return object
	}
	def unConfirmObjectValidation(object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = payableNotAssociateWithPaymentVoucher(object)
		return object
	}
	def printObjectValidation(object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}
}

