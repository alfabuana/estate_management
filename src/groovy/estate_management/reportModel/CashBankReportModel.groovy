package estate_management.reportModel

public class CashBankReportModel {

	private Integer id
	private String name
	private String description
	private Double amount
	private Boolean isBank
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	} 
	public void setName(String name) {
		this.name = name;
		}
		 
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
		}
		 
	public String getDescription() {
		return description;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
		}
		 
	public Double getAmount() {
		return amount;
	}
	
	public void setIsBank(Boolean isBank) {
		this.isBank = isBank;
		}
		 
	public Boolean getIsBank() {
		return isBank;
	}
	
}
