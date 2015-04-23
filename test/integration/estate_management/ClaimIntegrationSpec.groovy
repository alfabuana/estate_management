package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class ClaimIntegrationSpec extends IntegrationSpec {
	def homeService
	def userService
	def vendorService
	def permitService
	def claimService
	
	@Shared
	def shiroUser
	def home
	def vendor
	def permit

    def setup() {
		shiroUser = new ShiroUser()
		shiroUser.username = "admin1234"
		shiroUser.passwordHash = "sysadmin"
		shiroUser = userService.createObject(shiroUser)
		
		home = [
			name:"home1",
			address:"12334"
			]
		home = homeService.createObject(home)
		
		vendor = [
			name:"vendor1"
			]
		vendor = vendorService.createObject(vendor)
		
		permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
    }

    def cleanup() {
    }

    void "test create claim"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
	
		when:'create is called'
		claim = claimService.createObject(claim)
		
		then:'check error'
		claim.hasErrors() == false
		claim.isDeleted == false
    }
	
	void "test create claim validation permit not null"() {
		setup:'setting new claim'
		def claim = [
			permit:null,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
	
		when:'create is called'
		claim = claimService.createObject(claim)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getFieldError().defaultMessage
	}
	
	void "test create claim validation amount not null"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:null,
			claimDate:new Date(2015,4,22)
			]
	
		when:'create is called'
		claim = claimService.createObject(claim)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getFieldError().defaultMessage
	}
	
	void "test create claim validation claim date not null"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:null
			]
	
		when:'create is called'
		claim = claimService.createObject(claim)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getFieldError().defaultMessage
	}
	
	void "test update claim"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		println claim as JSON
		
		def claim2 = [
			id:claim.id,
			permit:permit,
			amount:"6000",
			claimDate:new Date(2015,4,22)
			]
	
		when:'update is called'
		claim = claimService.updateObject(claim2)
		println claim as JSON
		
		then:'check error'
		claim.hasErrors() == false
		claim.isDeleted == false
		claim.permit == claim.permit
		claim.amount == claim.amount
		claim.claimDate == claim.claimDate
	}
	
	void "test update claim validation permit not null"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def claim2 = [
			id:claim.id,
			permit:null,
			amount:"6000",
			claimDate:new Date(2015,4,22)
			]
	
		when:'update is called'
		claim = claimService.updateObject(claim2)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getFieldError().defaultMessage
	}
	
	void "test update claim validation amount not null"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def claim2 = [
			id:claim.id,
			permit:permit,
			amount:null,
			claimDate:new Date(2015,4,22)
			]
	
		when:'update is called'
		claim = claimService.updateObject(claim2)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getFieldError().defaultMessage
	}
	void "test update claim validation claimDate not null"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def claim2 = [
			id:claim.id,
			permit:permit,
			amount:"6000",
			claimDate:null
			]
	
		when:'update is called'
		claim = claimService.updateObject(claim2)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getFieldError().defaultMessage
	}
	
	void "test update claim validation is confirmed"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		claim = claimService.confirmObject(confirm)
		
		def claim2 = [
			id:claim.id,
			permit:permit,
			amount:"6000",
			claimDate:new Date(2015,4,22)
			]
	
		when:'update is called'
		claim = claimService.updateObject(claim2)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getAllErrors().defaultMessage
	}
	
	void "test softdelete claim"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		when:'delete is called'
		claim = claimService.softDeletedObject(claim)
		
		then:'check error'
		claim.hasErrors() == false
		claim.isDeleted == true
	}
	
	void "test softdelete claim validation is deleted"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		claim = claimService.softDeletedObject(claim)
		
		when:'delete is called'
		claim = claimService.softDeletedObject(claim)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getAllErrors().defaultMessage
	}
	
	void "test softdelete claim validation is confirmed"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		claim = claimService.confirmObject(confirm)
		
		when:'delete is called'
		claim = claimService.softDeletedObject(claim)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getAllErrors().defaultMessage
	}
	
	void "test confirm claim"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		
		when:'delete is called'
		claim = claimService.confirmObject(confirm)
		
		then:'check error'
		claim.hasErrors() == false
		claim.isConfirmed == true
	}
	
	void "test confirm claim validation is confirmed"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		claim = claimService.confirmObject(confirm)
		
		when:'delete is called'
		claim = claimService.confirmObject(confirm)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getAllErrors().defaultMessage
	}
	
	void "test unconfirm claim"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		claim = claimService.confirmObject(confirm)
		when:'delete is called'
		claim = claimService.unConfirmObject(claim)
		
		then:'check error'
		claim.hasErrors() == false
		claim.isConfirmed == false
	}
	
	void "test unconfirm claim validation is not confirmed"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		when:'delete is called'
		claim = claimService.unConfirmObject(claim)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getAllErrors().defaultMessage
	}
	
	void "test print claim"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		claim = claimService.confirmObject(confirm)
		
		when:'delete is called'
		claim = claimService.printObject(claim)
		
		then:'check error'
		claim.hasErrors() == false
		claim.isConfirmed == true
	}
	
	void "test print claim validation is not confirmed"() {
		setup:'setting new claim'
		def claim = [
			permit:permit,
			amount:5000,
			claimDate:new Date(2015,4,22)
			]
		claim = claimService.createObject(claim)
		
		def confirm = [
			id:claim.id,
			username:shiroUser.username
			]
		
		when:'delete is called'
		claim = claimService.printObject(claim)
		
		then:'check error'
		claim.hasErrors() == true
		println claim.errors.getAllErrors().defaultMessage
	}
}

