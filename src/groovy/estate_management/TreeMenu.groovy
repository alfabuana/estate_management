package estate_management

import com.vaadin.event.ItemClickEvent
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.navigator.Navigator
import com.vaadin.ui.Tree


class TreeMenu extends Tree{

	public TreeMenu(){
		//get the current Subject

		//Book
		//--Parent
		addItem( "Project Management" )
		//--Child
		addItem("Project Voting")
		setParent("Project Voting", "Project Management")
		setChildrenAllowed( "Project Voting", false)
		addItem("Post Project")
		setParent("Post Project", "Project Management")
		setChildrenAllowed( "Post Project", false)
		//Master Date
		//Parent
		addItem( "Master" )
		//Child
		addItem("Home")
		setParent("Home", "Master")
		setChildrenAllowed( "Home", false)

		addItem("User")
		setParent("User", "Master")
		setChildrenAllowed( "User", false)

		addItem("User Role")
		setParent("User Role", "Master")
		setChildrenAllowed( "User Role", false)

		addItem("Vendor")
		setParent("Vendor", "Master")
		setChildrenAllowed( "Vendor", false)

		
		addItem( "Finance" )
		//Child
		addItem("Cash Bank")
		setParent("Cash Bank", "Finance")
		setChildrenAllowed( "Cash Bank", false)

		addItem("Cash Bank Adjustment")
		setParent("Cash Bank Adjustment", "Finance")
		setChildrenAllowed( "Cash Bank Adjustment", false)
		
		addItem("Cash Bank Mutation")
		setParent("Cash Bank Mutation", "Finance")
		setChildrenAllowed( "Cash Bank Mutation", false)

		addItem("Payment Request")
		setParent("Payment Request", "Finance")
		setChildrenAllowed( "Payment Request", false)

		addItem("Payment Voucher")
		setParent("Payment Voucher", "Finance")
		setChildrenAllowed( "Payment Voucher", false)
		
		addItem("Receipt Voucher")
		setParent("Receipt Voucher", "Finance")
		setChildrenAllowed( "Receipt Voucher", false)
		
		addItem("Invoice")
		setParent("Invoice", "Finance")
		setChildrenAllowed( "Invoice", false)
		
		addItem("Invoice Clearance")
		setParent("Invoice Clearance", "Finance")
		setChildrenAllowed( "Invoice Clearance", false)
		
		
		addItem( "Maintenance" )
		//Child
		addItem("Maintenance Fee")
		setParent("Maintenance Fee", "Maintenance")
		setChildrenAllowed( "Maintenance Fee", false)

		addItem("Invoice Payment")
		setParent("Invoice Payment", "Maintenance")
		setChildrenAllowed( "Invoice Payment", false)

		
		addItem( "Tenant" )
		//Child
		addItem("Complaint")
		setParent("Complaint", "Tenant")
		setChildrenAllowed( "Complaint", false)

		addItem("Home Assignment")
		setParent("Home Assignment", "Tenant")
		setChildrenAllowed( "Home Assignment", false)
		//	   if (currentUser.hasRole("Administrator"))
		//	   {
		//		   //Parent
		//		   addItem( "Settings" )
		//		   setChildrenAllowed( "Settings", true)
		//		   //Child
		//		   addItem("Role")
		//		   setParent("Role", "Settings")
		//		   setChildrenAllowed( "Role", false)
		//		   addItem("User")
		//		   setParent("User", "Settings")
		//		   setChildrenAllowed( "User", false)
		//	   }

	}


}
