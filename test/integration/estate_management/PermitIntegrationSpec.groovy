package estate_management

import grails.converters.JSON
import grails.test.spock.IntegrationSpec
import spock.lang.Shared

class PermitIntegrationSpec extends IntegrationSpec {
	def homeService
	def userService
	def vendorService
	def permitService
	
	@Shared
	def shiroUser
	def home
	def vendor

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
		
    }

    def cleanup() {
    }

    void "test create permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		
		when:'create is called'
		permit = permitService.createObject(permit)
		
		then:'check error'
		permit.hasErrors() == false
		permit.isDeleted == false
    }
	
	void "test create permit validation construction type not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		
		when:'create is called'
		permit = permitService.createObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test create permit validation home not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:null,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		
		when:'create is called'
		permit = permitService.createObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test create permit validation vendor not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:null,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		
		when:'create is called'
		permit = permitService.createObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test create permit validation estimate work days not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:null,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		
		when:'create is called'
		permit = permitService.createObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test create permit validation amount deposit not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:null,
			startDate:new Date(2015,4,22)
			]
		
		when:'create is called'
		permit = permitService.createObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test create permit validation start date not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:null
			]
		
		when:'create is called'
		permit = permitService.createObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test update permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def permit2 = [
			id:permit.id,
			constructionType:"type2",
			home:home,
			vendor:vendor,
			estimateWorkDays:"7",
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == false
		permit.isDeleted == false
		permit.constructionType == permit.constructionType
		permit.home == permit.home
		permit.vendor == permit.vendor
		permit.estimateWorkDays == permit.estimateWorkDays
		permit.amountDeposit == permit.amountDeposit
		permit.startDate == permit.startDate
		
	}
	
	void "test update permit validation costruction type not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def permit2 = [
			id:permit.id,
			constructionType:"",
			home:home,
			vendor:vendor,
			estimateWorkDays:"7",
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test update permit validation home not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def permit2 = [
			id:permit.id,
			constructionType:"type2",
			home:null,
			vendor:vendor,
			estimateWorkDays:"7",
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test update permit validation vendor not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def permit2 = [
			id:permit.id,
			constructionType:"type2",
			home:home,
			vendor:null,
			estimateWorkDays:"7",
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test update permit validation estimate work days not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def permit2 = [
			id:permit.id,
			constructionType:"type2",
			home:home,
			vendor:vendor,
			estimateWorkDays:null,
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test update permit validation amount deposit not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def permit2 = [
			id:permit.id,
			constructionType:"type2",
			home:home,
			vendor:vendor,
			estimateWorkDays:"7",
			amountDeposit:null,
			startDate:new Date(2015,4,22)
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	
	void "test update permit validation start date not null"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:"7",
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def permit2 = [
			id:permit.id,
			constructionType:"type2",
			home:home,
			vendor:vendor,
			estimateWorkDays:"7",
			amountDeposit:"500000",
			startDate:null
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldError().defaultMessage
	}
	void "test update permit validation is confirmed"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		def permit2 = [
			id:permit.id,
			constructionType:"type2",
			home:home,
			vendor:vendor,
			estimateWorkDays:"7",
			amountDeposit:"500000",
			startDate:new Date(2015,4,22)
			]
		
		when:'update is called'
		permit = permitService.updateObject(permit2)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getAllErrors().defaultMessage
	}
	
	void "test softdelete permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		when:'softdelete is called'
		permit = permitService.softDeletedObject(permit)
		
		then:'check error'
		permit.hasErrors() == false
		permit.isDeleted == true
	}
	
	void "test softdelete permit validation is confirmed"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		when:'softdelete is called'
		permit = permitService.softDeletedObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getAllErrors().defaultMessage
	}
	
	void "test softdelete permit validation is deleted"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		permit = permitService.softDeletedObject(permit)
		
		when:'softdelete is called'
		permit = permitService.softDeletedObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getAllErrors().defaultMessage
	}
	
	void "test confirm permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		
		when:'confirm is called'
		permit = permitService.confirmObject(confirm)
		def receivable =[ Receivable.findAll{
					receivableSource == "permit" &&
					receivableSourceId == permit.id &&
					receivableSourceDetailId == permit.id &&
					amount == permit.amountDeposit &&
					remainingAmount == permit.amountDeposit
		}]
		
		then:'check error'
		permit.hasErrors() == false
		permit.isConfirmed == true
		receivable.size() > 0
	}
	
	void "test confirm permit validation is confirmed"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		when:'confirm is called'
		permit = permitService.confirmObject(confirm)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getFieldErrors().defaultMessage
	}
	
	void "test unconfirm permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		when:'confirm is called'
		permit = permitService.unConfirmObject(permit)
		
		then:'check error'
		permit.hasErrors() == false
		permit.isConfirmed == false
	}
	
	void "test unconfirm permit validation is not confirmed"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		when:'confirm is called'
		permit = permitService.unConfirmObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getAllErrors().defaultMessage
	}
	
	void "test unconfirm permit validation is cleared"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		def clear = [
			id:permit.id,
			username:shiroUser.username]
		permit = permitService.clearObject(clear)
		
		when:'confirm is called'
		permit = permitService.unConfirmObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getAllErrors().defaultMessage
	}
	
	void "test clear permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		def clear = [
			id:permit.id,
			username:shiroUser.username]
		
		when:'confirm is called'
		permit = permitService.clearObject(clear)
		def payable =[ Payable.findAll{
			payableSource == "permit" &&
			payableSourceId == permit.id &&
			payableSourceDetailId == permit.id &&
			amount == permit.amountDeposit &&
			remainingAmount == permit.amountDeposit
}]
		
		then:'check error'
		permit.hasErrors() == false
		permit.isCleared == true
		payable.size() > 0
	}
	
	void "test clear permit validation is cleared"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		def clear = [
			id:permit.id,
			username:shiroUser.username]
		permit = permitService.clearObject(clear)
		
		when:'clear is called'
		permit = permitService.clearObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getAllErrors().defaultMessage
	}
	
	void "test clear permit validation is not confirmed"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		
		def clear = [
			id:permit.id,
			username:shiroUser.username]
		
		when:'clear is called'
		permit = permitService.clearObject(clear)
		
		then:'check error'
		permit.hasErrors() == true
		println permit.errors.getAllErrors().defaultMessage
	}
	
	void "test unclear permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		def clear = [
			id:permit.id,
			username:shiroUser.username]
		permit = permitService.clearObject(clear)
		
		when:'confirm is called'
		permit = permitService.unClearObject(permit)
		
		then:'check error'
		permit.hasErrors() == false
		permit.isCleared == false
	}
	
	void "test unclear permit validation is not cleared"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		when:'confirm is called'
		permit = permitService.unClearObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		permit.errors.getAllErrors().defaultMessage
	}
	
	void "test print permit"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		permit = permitService.confirmObject(confirm)
		
		when:'confirm is called'
		permit = permitService.printObject(permit)
		
		then:'check error'
		permit.hasErrors() == false
		permit.isConfirmed == true
	}
	
	void "test print permit validation is not confirmed"() {
		setup:'setting new permit'
		def permit = [
			constructionType:"type1",
			home:home,
			vendor:vendor,
			estimateWorkDays:5,
			amountDeposit:50000,
			startDate:new Date(2015,4,22)
			]
		permit = permitService.createObject(permit)
		
		def confirm = [
			id:permit.id,
			username:shiroUser.username
			]
		
		when:'confirm is called'
		permit = permitService.printObject(permit)
		
		then:'check error'
		permit.hasErrors() == true
		permit.errors.getAllErrors().defaultMessage
	}
}
