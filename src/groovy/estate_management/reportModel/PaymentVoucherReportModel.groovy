package estate_management.reportModel

public class PaymentVoucherReportModel {

	private String code
	private String username
	private String cashBankName
	private Date paymentDate
	private Integer idDetail
	private String codePayable
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
	
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
		}
		 
	public Date getPaymentDate() {
		return paymentDate;
	}
	
	public void setIdDetail(Integer idDetail) {
		this.idDetail = idDetail;
		}
		 
	public Integer getIdDetail() {
		return idDetail;
	}
	
	public void setCodePayable(String codePayable) {
		this.codePayable = codePayable;
		}
		 
	public String getCodePayable() {
		return codePayable;
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
