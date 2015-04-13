package estate_management.reportModel

public class PaymentRequestReportModel {

	private String code
	private Date requestDate
	private String description
	private Date dueDate
	private Integer idDetail
	private String descriptionDetail
	private Double amountDetail
	private Double totalAmount
	private String vendorName
	private String vendorDescription
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	} 
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
		}
		 
	public Date getRequestDate() {
		return requestDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
		}
		 
	public String getDescription() {
		return description;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
		}
		 
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setIdDetail(Integer idDetail) {
		this.idDetail = idDetail;
		}
		 
	public Integer getIdDetail() {
		return idDetail;
	}
	
	public void setDescriptionDetail(String descriptionDetail) {
		this.descriptionDetail = descriptionDetail;
		}
		 
	public String getDescriptionDetail() {
		return descriptionDetail;
	}
	
	public void setAmountDetail(Double amountDetail) {
		this.amountDetail = amountDetail;
		}
		 
	public Double getAmountDetail() {
		return amountDetail;
	}
	
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
		}
		 
	public Double getTotalAmount() {
		return totalAmount;
	}
	
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorName() {
		return vendorName;
	}
	
	public void setVendorDescription(String vedorDescription) {
		this.vendorDescription = vendorDescription;
		}
		 
	public String getVendorDescription() {
		return vendorDescription;
	}
	
}
