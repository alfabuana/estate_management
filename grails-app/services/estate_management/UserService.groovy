package estate_management

import grails.transaction.Transactional

import estate_management.ShiroUser;

import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.subject.SimplePrincipalCollection
import org.apache.shiro.subject.Subject
@Transactional
class UserService {
	UserValidatorService userValidatorService = new UserValidatorService()
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return ShiroUser.get(object)
	}
	
	def getObjectByUserName(def object){
		return ShiroUser.find{ username == String.valueOf(object).toUpperCase()}
	}
	
	def getList(){
		return ShiroUser.getAll()
	}
	def getListDeleted(){
		return ShiroUser.findAll{isDeleted == false}
	}
	def createObject(object){
		ShiroUser newObject = new ShiroUser()
		newObject.username = String.valueOf(object.username).toUpperCase()
		newObject.passwordHash = new Sha256Hash(object.passwordHash).toHex()
		newObject.roles= object.roles
		newObject.isDeleted = false
		object = userValidatorService.createObjectValidation(newObject)
		if (object.errors.getErrorCount() == 0)
		{
			newObject.save()
			object = newObject
		} 
		return newObject
	}
	
	def updatePassword(def object){
		def newObject = ShiroUser.read(object.id)
		newObject.passwordHash = new Sha256Hash(object.passwordHash).toHex()
		object = userValidatorService.updateObjectValidation(newObject)
		if (object.errors.getErrorCount() == 0)
		{
			newObject.save()
			object = newObject
		}
		else newObject.discard()
		return object
	}
	
	def updateRoles(def object){
		def newObject = ShiroUser.read(object.id)
		newObject.roles = object.roles
		object = userValidatorService.updateObjectValidation(newObject)
		if (object.errors.getErrorCount() == 0)
		{
			newObject.save()
			object = newObject
		}
		else newObject.discard()
		return object
	}
	
	def updatePasswordObject(def object, oldpass, confirmpass){
		def newObject = ShiroUser.read(object.id)
		object.passwordHash = new Sha256Hash(object.passwordHash).toHex()
		object = userValidatorService.updatePasswordObjectValidation(object as ShiroUser, new Sha256Hash(oldpass).toHex(), new Sha256Hash(confirmpass).toHex())
		if (object.errors.getErrorCount() == 0)
		{
			newObject.passwordHash = object.passwordHash
			newObject.save()
			object = newObject
		} 
		//else object.discard()
		return object
	}
	
	def softDeleteObject(def object){
		def newObject = ShiroUser.get(object.id)
		newObject.isDeleted = true
		newObject.save()
	}
	
}
