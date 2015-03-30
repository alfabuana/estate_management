package estate_management

import grails.test.spock.IntegrationSpec

class ComplaintDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def complaintService
	def complaintDetailService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Complaint Detail"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			description:"newDescription",
			title:"newTitle"
		]
		complaint = complaintService.createObject(complaint)

		and:'setting new Complaint Detail'
		def complaintDetail = [
			complaint:complaint,
			attachmentUrl:"newAttachmentUrl"
			]
		when : 'createObject is called'
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		then: 'check has errors'
		complaintDetail.hasErrors() == false
		complaintDetail.isDeleted == false
	}
	void "Test Create Complaint Detail Validation Complaint Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			description:"newDescription",
			title:"newTitle"
		]
		complaint = complaintService.createObject(complaint)
		
		and:'setting new Complaint Detail'
		def complaintDetail = [
			complaint:null,
			attachmentUrl:"newAttachmentUrl"
		]
		
		when:'createObject is called'
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Complaint Detail Validation Attachment Url Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			description:"newDescription",
			title:"newTitle"
		]
		complaint = complaintService.createObject(complaint)
		
		and:'setting new Complaint Detail'
		def complaintDetail = [
			complaint:complaint,
			attachmentUrl:""
		]
		
		when:'createObject is called'
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update New Complaint Detail"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			description:"newDescription",
			title:"newTitle"
		]
		complaint= complaintService.createObject(complaint)
		
		and:'setting new Complaint Detail'
		def complaintDetail = [
			complaint:complaint,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)
		
		and:'setting data for Update'
		def complaintDetail2 = [
			id: complaintDetail.id,
			complaint:complaint,
			attachmentUrl:"updateAttachmentUrl"
		]

		when:'updateObject is called'
		complaintDetail = complaintDetailService.updateObject(complaintDetail2)

		then:'check has errors'
		complaintDetail.hasErrors() == false
		complaintDetail.complaint == complaintDetail.complaint
		complaintDetail.attachmentUrl == complaintDetail.attachmentUrl
	}
	void "Test Update Complaint Detail Validation complaint Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			description:"newDescription",
			title:"newTitle"
		]
		complaint= complaintService.createObject(complaint)
		
		and:'setting new Complaint Detail'
		def complaintDetail = [
			complaint:complaint,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)
		
		and:'setting data for Update'
		def complaintDetail2 = [
			id: complaintDetail.id,
			complaint:null,
			attachmentUrl:"updateAttachmentUrl"
		]

		when:'updateObject is called'
		complaintDetail = complaintDetailService.updateObject(complaintDetail2)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getFieldError().defaultMessage

	}
	void "Test Update Complaint Detail Validation attachmentUrl Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			description:"newDescription",
			title:"newTitle"
		]
		complaint= complaintService.createObject(complaint)
		
		and:'setting new Complaint Detail'
		def complaintDetail = [
			complaint:complaint,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)
		
		and:'setting data for Update'
		def complaintDetail2 = [
			id: complaintDetail.id,
			complaint:complaint,
			attachmentUrl:""
		]

		when:'updateObject is called'
		complaintDetail = complaintDetailService.updateObject(complaintDetail2)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Complaint Detail"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Complaint'
		def complaint = [
			username:shiroUser,
			description:"newDescription",
			title:"newTitle"
		]
		complaint= complaintService.createObject(complaint)
		
		and:'setting new Complaint Detail'
		def complaintDetail = [
			complaint:complaint,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		when:'softDelete is called'
		complaintDetail = complaintDetailService.softDeletedObject(complaintDetail)
		
		then:'check has errors'
		complaintDetail.hasErrors() == false
		complaintDetail.isDeleted == true
	}
}
