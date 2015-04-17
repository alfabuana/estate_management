package estate_management.reportModel

public class ClaimReportModel {

	private String code
	private String permitCode
	private String constructionType
	private String vendorName
	private String name
	private String address
	private Double amount
	private String description
	private Date claimDate
	
	
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	 
	public void setPermitCode(String permitCode) {
		this.permitCode = permitCode;
		}
		 
	public String getPermitCode() {
		return permitCode;
	}
	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
		}
		 
	public String getConstructionType() {
		return constructionType;
	}
	
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
		}
		 
	public String getVendorName() {
		return vendorName;
	}
	
	public void setName(String name) {
		this.name = name;
		}
		 
	public String getName() {
		return name;
	}
	
	public void setAddress(String address) {
		this.address = address;
		}
		 
	public String getAddress() {
		return address;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
		}
		 
	public Double getAmount() {
		return amount;
	}
	
	public void setDescription(String description) {
		this.description = description;
		}
		 
	public String getDescription() {
		return description;
	}
	
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
		}
		 
	public Date getClaimDate() {
		return claimDate;
	}
	
	
}
