package estate_management

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class CashMutationService {
	CashMutationValidationService cashMutationValidationService

    def serviceMethod() {

    }
	def getObjectById(def object){
		return CashMutation.get(object)
	}
	def getList(){
		return CashMutation.getAll()
	}
	
	def createObject(def cashBank, def status, def sourceDocumentType, 
		def sourceDocumentCode, def sourceDocumentId, def amount,def mutationDate){
		CashMutation cashMutation = new CashMutation()
		cashMutation.cashBank = cashBank
		cashMutation.status = status
		cashMutation.sourceDocumentType = sourceDocumentType
		cashMutation.sourceDocumentCode = sourceDocumentCode
		cashMutation.sourceDocumentId = sourceDocumentId
		cashMutation.amount = amount
		cashMutation.mutationDate = mutationDate
		cashMutation.isDeleted = false
		cashMutation = cashMutationValidationService.createObjectValidation(cashMutation as CashMutation)
		if (cashMutation.errors.getErrorCount() == 0)
		{
			CashBank cashBank2 = CashBank.find{
				id == cashMutation.cashBank.id
			}
			if(status=="plus"){
				cashBank2.amount = cashBank2.amount + amount
			}
			else
			{
				cashBank2.amount = cashBank2.amount - amount
			}
			cashBank2.save()
			cashMutation.save()
		}
		return cashMutation
	}
	def softDeletedObject(def object){
		def newObject = CashMutation.get(object.id)
		newObject = cashMutationValidationService.softdeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
	}
}
