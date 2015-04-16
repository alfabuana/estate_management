package estate_management.reportModel

public class PermitReportModel {

	private String code
	private String constructionType
	private String name
	private String address
	private String imbNo
	private String description
	private Date startDate
	private Integer estimateWorkDays
	private Double depositAmount
	private String vendorName
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	} 
	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
		}
		 
	public String getConstructionType() {
		return constructionType;
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
	public void setImbNo(String imbNo) {
		this.imbNo = imbNo;
		}
		 
	public String getImbNo() {
		return imbNo;
	}
	
	public void setDescription(String description) {
		this.description = description;
		}
		 
	public String getDescription() {
		return description;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		}
		 
	public Date getStartDate() {
		return startDate;
	}
	
	public void setEstimateWorkDays(Integer estimateWorkDays) {
		this.estimateWorkDays = estimateWorkDays;
		}
		 
	public Integer getEstimateWorkDays() {
		return estimateWorkDays;
	}
	
	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
		}
		 
	public Double getDepositAmount() {
		return depositAmount;
	}
	
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
		}
		 
	public String getVendorName() {
		return vendorName;
	}
	
	
}
