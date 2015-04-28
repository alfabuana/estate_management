package estate_management.widget

public final class Constant {
	public enum MenuGroup {
		ProjectManagement("ProjectManagement"),
		Master("Master"),
		Finance("Finance"),
		Maintenance("Maintenance"),
		Tenant("Tenant");
		
		private final String name;
		
		private MenuGroup(String s) {
			name = s;
		}
	
		//@Override
		public boolean equalsName(String otherName){
			return (otherName == null)? false:name.equals(otherName);
		}
	
		@Override
		public String toString(){
		   return name;
		}
		
		//@Override
		public String plus(String s){
			return name + s;
		}
	}
	
	public enum MenuName {
		ProjectVoting("ProjectVoting"),
		PostProject("PostProject"),
		
		Home("Home"),
		User("User"),
		UserRole("UserRole"),
		Vendor("Vendor"),
		
		
		CashBank("CashBank"),
		CashBankAdjustment("CashBankAdjustment"),
		CashBankMutation("CashBankMutation"),
		PaymentRequest("PaymentRequest"),
		PaymentVoucher("PaymentVoucher"),
		ReceiptVoucher("ReceiptVoucher"),
		Invoice("Invoice"),
		InvoiceClearance("InvoiceClearance"),
		
		MaintenanceFee("MaintenanceFee"),
		Permit("Permit"),
		Claim("Claim"),
		
		Complaint("Complaint"),
		HomeAssignment("HomeAssignment"),
		InvoicePayment("InvoicePayment"),
		OutstandingInvoice("OutstandingInvoice"),
		ParkingRegistration("ParkingRegistration");
		
		private final String name;
		
		private MenuName(String s) {
			name = s;
		}
	
		//@Override
		public boolean equalsName(String otherName){
			return (otherName == null)? false:name.equals(otherName);
		}
	
		@Override
		public String toString(){
		   return name;
		}
		
		//@Override
		public String plus(String s){
			return name + s;
		}
	}
	
	public enum AccessType {
		View("View"),
		Add("Add"),
		Edit("Edit"),
		Delete("Delete"),
		Confirm("Confirm"),
		Unconfirm("Unconfirm"),
		Clear("Clear"),
		Unclear("Unclear"),
		Finish("Finish"),
		Unfinish("Unfinish"),
		Print("Print");
		
		private final String name;
		
		private AccessType(String s) {
			name = s;
		}
	
		//@Override
		public boolean equalsName(String otherName){
			return (otherName == null)? false:name.equals(otherName);
		}
	
		@Override
		public String toString(){
		   return name;
		}
		
		//@Override
		public String plus(String s){
			return name + s;
		}
	}
}
