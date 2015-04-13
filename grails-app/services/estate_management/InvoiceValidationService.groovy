package estate_management

import grails.transaction.Transactional

@Transactional
class InvoiceValidationService {

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
		if(object.invoiceDetails.size() == 0)
		{
			object.errors.rejectValue(null,'null','Harus memiliki detail')
		}
		return object
	}
	def recevableNotAssociateWithReceiptVoucher(def object)
	{
		for (detail in object.invoiceDetails.findAll{ it.isDeleted == false })
		{
			def invoiceDetail = ReceiptVoucherDetail.find {
				receivable.receivableSource == "invoice" &&
						receivable.receivableSourceId == object.id &&
						receivable.receivableSourceDetailId == detail.id &&
						isDeleted == false
			}
			if (invoiceDetail != null)
			{
				object.errors.rejectValue(null,'null','Invoice sudah di buat ReceiptVoucher')
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
	def codeNotNull(def object){
		if (object.code == null || object.code == "")
		{
			object.errors.rejectValue('code','null','Kode tidak boleh kosong')
		}
		return object
	}
	def invoiceDateNotNull(def object){
		if (object.invoiceDate == null || object.invoiceDate == "")
		{
			object.errors.rejectValue('invoiceDate','null','Invoice Date tidak boleh kosong')
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
//	def dueDateNotNull(def object){
//		if (object.dueDate == null || object.dueDate == "")
//		{
//			object.errors.rejectValue('dueDate','null','Due Date tidak boleh kosong')
//		}
//		return object
//	}
	def totalAmountNotNull(def object){
		if (object.totalAmount == null || object.totalAmount == "")
		{
			object.errors.rejectValue('totalAmount','null','Total Amount tidak boleh kosong')
		}
		return object
	}
	def createObjectValidation(def object)
	{
//		object = usernameNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object = codeNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = invoiceDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
//		object = dueDateNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = totalAmountNotNull(object)
		return object
	}
	def updateObjectValidation(def object)
	{
//		object = usernameNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object = codeNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = invoiceDateNotNull(object)
		if (object.errors.hasErrors()) return object
		object = descriptionNotNull(object)
		if (object.errors.hasErrors()) return object
//		object = dueDateNotNull(object)
//		if (object.errors.hasErrors()) return object
		object = totalAmountNotNull(object)
		return object
	}
	def softdeleteObjectValidation(object)
	{
		object = isDeleted(object)
		if (object.errors.hasErrors()) return object
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
		object = recevableNotAssociateWithReceiptVoucher(object)
		return object
	}
	def printObjectValidation(object)
	{
		object = isNotConfirmed(object)
		if (object.errors.hasErrors()) return object
		return object
	}
}
