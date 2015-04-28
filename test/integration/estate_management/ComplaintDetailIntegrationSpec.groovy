package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class ComplaintDetailIntegrationSpec extends IntegrationSpec {
	def userService
	def homeService
	def complaintService
	def complaintDetailService

	@Shared
	def shiroUser
	def home
	def complaint
	def confirm

	def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)

		home = [
			name:"Home1",
			address:"12345",
			createdBy:shiroUser
		]
		home = homeService.createObject(home)

		complaint = [
			username:shiroUser.username,
			home:home,
			title:"newTitle"
		]
		complaint = complaintService.createObject(complaint)

		confirm = [
			id:complaint.id,
			username:shiroUser
		]
		complaint = complaintService.confirmObject(confirm)
	}
	def cleanup() {
	}

	void "Test Create New Complaint Detail"() {
		setup:'setting new Complaint Detail'
		def complaintDetail = [
			complaintId:complaint.id,
			attachmentUrl:"newAttachmentUrl"
		]
		when : 'createObject is called'
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		then: 'check has errors'
		complaintDetail.hasErrors() == false
		complaintDetail.isDeleted == false
		complaintDetail.id != null
	}
	void "Test Create Complaint Detail Validation Complaint Not Null"(){
		setup:'setting new Complaint Detail'
		def complaintDetail = [
			complaintId:null,
			attachmentUrl:"newAttachmentUrl"
		]

		when:'createObject is called'
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getFieldError().defaultMessage
	}
	void "Test Create Complaint Detail Validation Attachment Url Not Null"(){
		setup:'setting new Complaint Detail'
		def complaintDetail = [
			complaintId:complaint.id,
			attachmentUrl:""
		]

		when:'createObject is called'
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getFieldError().defaultMessage
	}
	void "Test Update New Complaint Detail"(){
		setup:'setting new Complaint Detail'
		def complaintDetail = [
			complaintId:complaint.id,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		and:'setting data for Update'
		def complaintDetail2 = [
			id: complaintDetail.id,
			complaintId:complaint,
			attachmentUrl:"updateAttachmentUrl"
		]

		when:'updateObject is called'
		complaintDetail = complaintDetailService.updateObject(complaintDetail2)

		then:'check has errors'
		complaintDetail.hasErrors() == false
		complaintDetail.complaint == complaintDetail.complaint
		complaintDetail.attachmentUrl == complaintDetail.attachmentUrl
	}
	void "Test Update Complaint Detail Validation attachmentUrl Not Null"(){
		setup:'setting new Complaint Detail'
		def complaintDetail = [
			complaintId:complaint.id,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		and:'setting data for Update'
		def complaintDetail2 = [
			id: complaintDetail.id,
			complaintId:complaint.id,
			attachmentUrl:""
		]

		when:'updateObject is called'
		complaintDetail = complaintDetailService.updateObject(complaintDetail2)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getFieldError().defaultMessage

	}
	void "Test SoftDelete Complaint Detail"() {
		setup:'setting new Complaint Detail'
		def complaintDetail = [
			complaintId:complaint.id,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)

		when:'softDelete is called'
		complaintDetail = complaintDetailService.softDeletedObject(complaintDetail)

		then:'check has errors'
		complaintDetail.hasErrors() == false
		complaintDetail.isDeleted == true
	}
	void "Test SoftDelete Complaint Detail Validation Is Deleted"() {
		setup:'setting new Complaint Detail'
		def complaintDetail = [
			complaintId:complaint.id,
			attachmentUrl:"newAttachmentUrl"
		]
		complaintDetail = complaintDetailService.createObject(complaintDetail)
		complaintDetail = complaintDetailService.softDeletedObject(complaintDetail)

		when:'softDelete is called'
		complaintDetail = complaintDetailService.softDeletedObject(complaintDetail)

		then:'check has errors'
		complaintDetail.hasErrors() == true
		println "Validasi sukses dengan error message : " + complaintDetail.errors.getAllErrors().defaultMessage
	}
}
