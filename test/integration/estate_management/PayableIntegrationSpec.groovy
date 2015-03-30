package estate_management

import grails.test.spock.IntegrationSpec

class PayableIntegrationSpec extends IntegrationSpec {
	def userService
	def payableService

    def setup() {
    }

    def cleanup() {
    }

    void "Test Create New payable"() {
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when : 'createObject is called'
		payable = payableService.createObject(payable)

		then: 'check has errors'
		payable.hasErrors() == false
		payable.isDeleted == false
	}
	void "Test Create payable Validation Username Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:null,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation payable Source Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation payable Source Id Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:null,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation payable Source Detail Id Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:null,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation code Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation Due Date Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:null,
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation Amount Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:null,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation Remaining Amount Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:null,
			pendingClearanceAmount:3000
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
	void "Test Create payable Validation Pending Clearance Amount Not Null"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:null
		]

		when:'createObject is called'
		payable = payableService.createObject(payable)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage
	}
		void "Test Update New payable"(){
		setup:'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == false
		payable.username == payable.username
		payable.payableSource == payable.payableSource
		payable.payableSourceId == payable.payableSourceId
		payable.payableSourceDetailId == payable.payableSourceDetailId
		payable.code == payable.code
		payable.dueDate == payable.dueDate
		payable.amount == payable.amount
		payable.remainingAmount == payable.remainingAmount
		payable.pendingClearanceAmount == payable.pendingClearanceAmount
	}
	void "Test Update payable Validation username Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:null,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation payable Source Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation payable Source Id Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:null,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation payable Source Detail Id Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:null,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation code Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation due Date Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:null,
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:null,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation remaining amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:null,
			pendingClearanceAmount:3000
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
	void "Test Update payable Validation Pending Clearance Amount Not Null"(){
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]

		payable= payableService.createObject(payable)

		and:'setting data for Update'
		def payable2 = [
			id: payable.id,
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:null
		]

		when:'updateObject is called'
		payable = payableService.updateObject(payable2)

		then:'check has errors'
		payable.hasErrors() == true
		println "Validasi sukses dengan error message : " + payable.errors.getFieldError().defaultMessage

	}
		void "Test SoftDelete payable"() {
		setup: 'setting new User'
		ShiroUser shiroUser = new ShiroUser()
		shiroUser.username = "username"
		shiroUser.passwordHash = "password"
		shiroUser = userService.createObject(shiroUser)

		and: 'setting new payable'
		def payable = [
			username:shiroUser,
			payableSource:"newpayableSource",
			payableSourceId:1,
			payableSourceDetailId:2,
			code:"newCode",
			dueDate:new Date (2015,3,26),
			amount:1000,
			remainingAmount:2000,
			pendingClearanceAmount:3000
		]
		payable= payableService.createObject(payable)

		when:'createObject is called'
		payable = payableService.softDeletedObject(payable)
		
		then:'check has errors'
		payable.hasErrors() == false
		payable.isDeleted == true
	}

}
