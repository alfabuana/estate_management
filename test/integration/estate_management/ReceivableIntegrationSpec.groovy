package estate_management

import grails.test.spock.IntegrationSpec

class ReceivableIntegrationSpec extends IntegrationSpec {
	def userService
	def receivableService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New Receivable"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when : 'createObject is called'
		receivable = receivableService.createObject(receivable)

		then: 'check has errors'
		receivable.hasErrors() == false
		receivable.isDeleted == false
	}
	void "Test Create Receivable Validation Username Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:null,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation Receivable Source Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation receivable Source Id Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:null,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation receivable Source Detail Id Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:null,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation code Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation Due Date Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:null,
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation Amount Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:null,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation Remaining Amount Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:null,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
	void "Test Create Receivable Validation Pending Clearance Amount Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:null
		]

		when:'createObject is called'
		receivable = receivableService.createObject(receivable)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage
	}
		void "Test Update New Receivable"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == false
		receivable.username == receivable.username
		receivable.receivableSource == receivable.receivableSource
		receivable.receivableSourceId == receivable.receivableSourceId
		receivable.receivableSourceDetailId == receivable.receivableSourceDetailId
		receivable.code == receivable.code
		receivable.dueDate == receivable.dueDate
		receivable.amount == receivable.amount
		receivable.remainingAmount == receivable.remainingAmount
		receivable.pendingClearanceAmount == receivable.pendingClearanceAmount
	}
	void "Test Update Receivable Validation username Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:null,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation Receivable Source Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation Receivable Source Id Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:null,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation Receivable Source Detail Id Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:null,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation code Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation due Date Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:null,
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:null,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation remaining amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:null,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
	void "Test Update Receivable Validation Pending Clearance Amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		receivable= receivableService.createObject(receivable)

		and:'setting data for Update'
		def receivable2 = [
			id: receivable.id,
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:null
		]

		when:'updateObject is called'
		receivable = receivableService.updateObject(receivable2)

		then:'check has errors'
		receivable.hasErrors() == true
		println "Validasi sukses dengan error message : " + receivable.errors.getFieldError().defaultMessage

	}
		void "Test SoftDelete Receivable"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new Receivable'
		def receivable = [
			username:shiroUser,
			receivableSource:"newReceivableSource",
			receivableSourceId:1,
			receivableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		receivable= receivableService.createObject(receivable)

		when:'createObject is called'
		receivable = receivableService.softDeletedObject(receivable)
		
		then:'check has errors'
		receivable.hasErrors() == false
		receivable.isDeleted == true
	}
}
