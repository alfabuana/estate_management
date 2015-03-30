package estate_management

import grails.test.spock.IntegrationSpec

class InvoicePaidDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def invoicePaidService
	def invoicePaidDetailService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Invoice Paid Detail"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Invoice Paid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid = invoicePaidService.createObject(invoicePaid)

		and:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaid:invoicePaid,
			attachmentUrl:"newAttachmentUrl"
			]
		when : 'createObject is called'
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)

		then: 'check has errors'
		invoicePaidDetail.hasErrors() == false
		invoicePaidDetail.isDeleted == false
	}
	void "Test Create invoicePaid Detail Validation invoicePaid Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new invoicePaid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid = invoicePaidService.createObject(invoicePaid)
		
		and:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaid:null,
			attachmentUrl:"newAttachmentUrl"
		]
		
		when:'createObject is called'
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)

		then:'check has errors'
		invoicePaidDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaidDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create invoicePaid Detail Validation Attachment Url Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new invoicePaid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid = invoicePaidService.createObject(invoicePaid)
		
		and:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaid:invoicePaid,
			attachmentUrl:""
		]
		
		when:'createObject is called'
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)

		then:'check has errors'
		invoicePaidDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaidDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update New invoicePaid Detail"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new invoicePaid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid= invoicePaidService.createObject(invoicePaid)
		
		and:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaid:invoicePaid,
			attachmentUrl:"newAttachmentUrl"
		]
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)
		
		and:'setting data for Update'
		def invoicePaidDetail2 = [
			id: invoicePaidDetail.id,
			invoicePaid:invoicePaid,
			attachmentUrl:"updateAttachmentUrl"
		]

		when:'updateObject is called'
		invoicePaidDetail = invoicePaidDetailService.updateObject(invoicePaidDetail2)

		then:'check has errors'
		invoicePaidDetail.hasErrors() == false
		invoicePaidDetail.invoicePaid == invoicePaidDetail.invoicePaid
		invoicePaidDetail.attachmentUrl == invoicePaidDetail.attachmentUrl
	}
	void "Test Update invoicePaid Detail Validation invoicePaid Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new invoicePaid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid= invoicePaidService.createObject(invoicePaid)
		
		and:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaid:invoicePaid,
			attachmentUrl:"newAttachmentUrl"
		]
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)
		
		and:'setting data for Update'
		def invoicePaidDetail2 = [
			id: invoicePaidDetail.id,
			invoicePaid:null,
			attachmentUrl:"updateAttachmentUrl"
		]

		when:'updateObject is called'
		invoicePaidDetail = invoicePaidDetailService.updateObject(invoicePaidDetail2)

		then:'check has errors'
		invoicePaidDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + invoicePaidDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update invoicePaid Detail Validation attachmentUrl Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new invoicePaid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid= invoicePaidService.createObject(invoicePaid)
		
		and:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaid:invoicePaid,
			attachmentUrl:"newAttachmentUrl"
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
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new invoicePaid'
		def invoicePaid = [
			username:shiroUser,
			description:"newDescription",
			paidDate:new Date (2015,4,1)
		]
		invoicePaid= invoicePaidService.createObject(invoicePaid)
		
		and:'setting new invoicePaid Detail'
		def invoicePaidDetail = [
			invoicePaid:invoicePaid,
			attachmentUrl:"newAttachmentUrl"
		]
		invoicePaidDetail = invoicePaidDetailService.createObject(invoicePaidDetail)

		when:'softDelete is called'
		invoicePaidDetail = invoicePaidDetailService.softDeletedObject(invoicePaidDetail)
		
		then:'check has errors'
		invoicePaidDetail.hasErrors() == false
		invoicePaidDetail.isDeleted == true
	}

}
