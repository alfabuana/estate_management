package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared
class InvoicePaidDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def invoiceService
	def invoicePaidService
	def invoicePaidDetailService
	
	@Shared
	def shiroUser
	def invoice
	def invoicePaid

    def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		invoice = [
			invoiceDate:new Date (2015,3,30),
			totalAmount:0,
			]
		invoice = invoiceService.createObject(invoice)
		
		invoicePaid = [
			username:shiroUser.username,
			invoice:invoice.id,
			paidDate:new Date (2015,4,1)
		]

		invoicePaid = invoicePaidService.createObject(invoicePaid)
    }

    def cleanup() {
    }

    void "Test Create New Invoice Paid Detail"() {
		setup:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaidId:invoicePaid.id,
			attachmentUrl:"newAttachmentUrl",
			username:shiroUser.username
			]
		when : 'createObject is called'
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)

		then: 'check has errors'
		invoicePaidDetail.hasErrors() == false
		invoicePaidDetail.isDeleted == false
	}
	
	void "Test Create invoicePaid Detail Validation Attachment Url Not Null"(){
		setup:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaidId:invoicePaid.id,
			attachmentUrl:"",
			username:shiroUser.username
		]
		
		when:'createObject is called'
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)

		then:'check has errors'
		invoicePaidDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaidDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update New invoicePaid Detail"(){
		setup:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaidId:invoicePaid.id,
			attachmentUrl:"newAttachmentUrl",
			username:shiroUser.username
		]
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)
		
		and:'setting data for Update'
		def invoicePaidDetail2 = [
			id: invoicePaidDetail.id,
			invoicePaid:invoicePaid,
			attachmentUrl:"updateAttachmentUrl",
			username:shiroUser.username
		]

		when:'updateObject is called'
		invoicePaidDetail = invoicePaidDetailService.updateObject(invoicePaidDetail2)

		then:'check has errors'
		invoicePaidDetail.hasErrors() == false
		invoicePaidDetail.invoicePaid == invoicePaidDetail.invoicePaid
		invoicePaidDetail.attachmentUrl == invoicePaidDetail.attachmentUrl
	}

	void "Test Update invoicePaid Detail Validation attachmentUrl Not Null"(){
		setup:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaidId:invoicePaid.id,
			attachmentUrl:"newAttachmentUrl",
			username:shiroUser.username
		]
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)
		
		and:'setting data for Update'
		def invoicePaidDetail2 = [
			id: invoicePaidDetail.id,
			invoicePaid:invoicePaid,
			attachmentUrl:""
		]

		when:'updateObject is called'
		invoicePaidDetail = invoicePaidDetailService.updateObject(invoicePaidDetail2)

		then:'check has errors'
		invoicePaidDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaidDetail.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete invoicePaid Detail"() {
		setup:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaidId:invoicePaid.id,
			attachmentUrl:"newAttachmentUrl",
			username:shiroUser.username
		]
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)

		when:'softDelete is called'
		invoicePaidDetail = invoicePaidDetailService.softDeletedObject(invoicePaidDetail)
		
		then:'check has errors'
		invoicePaidDetail.hasErrors() == false
		invoicePaidDetail.isDeleted == true
	}
	void "Test SoftDelete invoicePaid Detail Validation Is Deleted"() {
		setup:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaidId:invoicePaid.id,
			attachmentUrl:"newAttachmentUrl",
			username:shiroUser.username
		]
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)
		invoicePaidDetail = invoicePaidDetailService.softDeletedObject(invoicePaidDetail)
		
		when:'softDelete is called'
		invoicePaidDetail = invoicePaidDetailService.softDeletedObject(invoicePaidDetail)
		
		then:'check has errors'
		invoicePaidDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaidDetail.errors.getAllErrors().defaultMessage
	}

}

