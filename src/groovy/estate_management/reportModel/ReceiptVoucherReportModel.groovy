package estate_management.reportModel

public class ReceiptVoucherReportModel {

	private String code
	private String username
	private String cashBankName
	private Date receiptDate
	private Integer idDetail
	private String codeReceivable
	private String descriptionDetail
	private Double amountDetail
	private Double totalAmount
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	} 
	public void setUsername(String username) {
		this.username = username;
		}
		 
	public String getUsername() {
		return username;
	}
	
	public void setCashBankName(String cashBankName) {
		this.cashBankName = cashBankName;
		}
		 
	public String getCashBankName() {
		return cashBankName;
	}
	
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
		}
		 
	public Date getReceiptDate() {
		return receiptDate;
	}
	
	public void setIdDetail(Integer idDetail) {
		this.idDetail = idDetail;
		}
		 
	public Integer getIdDetail() {
		return idDetail;
	}
	
	public void setCodeReceivable(String codeReceivable) {
		this.codeReceivable = codeReceivable;
		}
		 
	public String getCodeReceivable() {
		return codeReceivable;
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
	
}
