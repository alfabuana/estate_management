package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class PaymentRequestValidationService {

	def serviceMethod() {

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
		if(object.paymentRequestDetails.size() == 0)
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
			print paymentVoucherDetail as JSON
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
	def descriptionNotNull(def object){
		if (object.description == null || object.description == "")
		{
			object.errors.rejectValue('description','null','Description tidak boleh kosong')
		}
		return object
	}
	def codeNotNull(def object){
		if (object.code == null || object.code == "")
		{
			object.errors.rejectValue('code','null','Kode tidak boleh kosong')
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
	def createObjectValidation(def object)
	{
//		object = usernameNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
//		object  = codeNotNull(object)
//		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = requestDateNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
//		object = usernameNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
//		object  = codeNotNull(object)
//		if (object.errors.hasErrors()) return object
		object  = amountNotNull(object)
		if (object.errors.hasErrors()) return object
		object  = requestDateNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
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

}

