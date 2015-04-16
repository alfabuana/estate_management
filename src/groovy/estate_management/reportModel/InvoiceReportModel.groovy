package estate_management.reportModel

public class InvoiceReportModel {

	private String code
	private Date invoiceDate
	private String description
	private Date dueDate
	private Integer idDetail
	private String descriptionDetail
	private Double amount
	private Double totalAmount
	private String name
	private String address
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	} 
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
		}
		 
	public Date getInvoiceDate() {
		return invoiceDate;
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
	
	public void setAmount(Double amount) {
		this.amount = amount;
		}
		 
	public Double getAmount() {
		return amount;
	}
	
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
		}
		 
	public Double getTotalAmount() {
		return totalAmount;
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
}
