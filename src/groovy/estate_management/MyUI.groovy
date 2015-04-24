package estate_management


import java.awt.Event;
import estate_management.widget.GeneralFunction
import estate_management.widget.Constant
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.grails.Grails;



import com.vaadin.event.ItemClickEvent
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.navigator.Navigator
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent
import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.MenuBar
import com.vaadin.ui.Notification
import com.vaadin.ui.Panel
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Window
import com.vaadin.ui.MenuBar.MenuItem

import com.vaadin.annotations.Theme;

@Theme("chameleon")
public class MyUI extends UI{
	Navigator navigator

	public String UserName = "";

	def generalFunction = new GeneralFunction()
	def windowChgPass
	def textId
	def textName
	def textOldPassword
	def textNewPassword
	def textConfirmPassword

	public class MainView extends VerticalLayout implements View {
		Panel panel
		MenuBar menuBar
		MenuItem userMenu



		public MainView() {
			setSizeFull()

			// Layout with menu on left and view area on right
			HorizontalLayout hLayout = new HorizontalLayout();
			hLayout.setSizeFull();

			// Menu on top of the screen
			menuBar = new MenuBar()
			menuBar.setWidth("100%")
			userMenu =  menuBar.addItem(UserName, null)
			userMenu.setStyleName("menuRight");
			userMenu.addItem("Change Password", new MenuBar.Command(){
						public void menuSelected(MenuItem selectedItem) {
							windowChangePassword("Change Password");
						}
					});
			userMenu.addItem("Logout", new MenuBar.Command(){
						public void menuSelected(MenuItem selectedItem) {
							SignOut();
						}
					});

			// Have a menu on the left side of the screen
			VerticalLayout menuContent = new VerticalLayout();
			menuContent.setWidth(null);
			menuContent.setMargin(true);
			// Create TreeView For left Content



			def tree = new TreeMenu()

			tree.addItemClickListener(new ItemClickListener() {
						@Override
						public void itemClick(ItemClickEvent event) {
							navigator.navigateTo("MAINVIEW" + "/" + event.getItemId());
						}
					})
			for(def item in tree.getItemIds()){
				tree.expandItemsRecursively(item)
			}
			menuContent.addComponent(tree)

			def menu = new Panel("Menu");
			menu.setHeight("100%");
			menu.setWidth(null);
			menu.setContent(menuContent);

			hLayout.addComponent(menu);

			//			==========================
			// JUDUL
			//			==========================
			// A panel that contains a content area on right
			panel = new Panel("Estate Management");
			panel.setSizeFull();
			//			==========================

			hLayout.addComponent(panel);
			hLayout.setExpandRatio(panel, 1.0f);
			//hLayout.setWidth(null);

			addComponent(menuBar);//1
			setComponentAlignment(menuBar, Alignment.MIDDLE_RIGHT );
			addComponent(hLayout);//2
			setExpandRatio(hLayout, 1.0f);

		}

		@Override
		public void enter(ViewChangeEvent event) {
			// TODO Auto-generated method stub
			// Get the user name from the session
			UserName = String.valueOf(getSession().getAttribute("user"));
			Subject currentUser = SecurityUtils.getSubject();
			//print UserName + "/" + currentUser.getPrincipal()
			userMenu.setText(UserName)
			userMenu.setDescription("UserName")

			//print event.getParameters()
			VerticalLayout panelContent = new VerticalLayout();
			panelContent.setSizeFull();
			//			panelContent.setMargin(true);
			//			panelContent.setWidth(null);
			panel.setContent(panelContent);

			if (event.getParameters() == null
			|| event.getParameters().isEmpty()) {
				panelContent.addComponent(
						new Label(" " +
						""));
				return;
			}
			else{
				switch(event.getParameters())
				{
					case "Home":
						if (currentUser.isPermitted("Master:Home:" + Constant.AccessType.View)) {
							panelContent.addComponent(new MasterHome())
						}
						else
						{
							Notification.show("Access Denied\n",
								"Anda tidak memiliki izin untuk mengakses Master Home",
								Notification.Type.ERROR_MESSAGE);
						}
						break
						
						
					case "User":
					if (currentUser.isPermitted("Master:User:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterUser())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Master User",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "User Role":
					if (currentUser.isPermitted("Master:UserRole:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterRole())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Master Role",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Vendor":
					if (currentUser.isPermitted("Master:Vendor:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterVendor())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Master Vendor",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Cash Bank":
					if (currentUser.isPermitted("Finance:CashBank:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterCashBank())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Cash Bank",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
					case "Cash Bank Adjustment":
					if (currentUser.isPermitted("Finance:CashBankAdjustment:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterCashBankAdjustment())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Cash Bank Adjustment",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Payment Request":
					if (currentUser.isPermitted("Finance:PaymentRequest:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterPaymentRequest())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Payment Request",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Receipt Voucher":
					if (currentUser.isPermitted("Finance:ReceiptVoucher:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterReceiptVoucher())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Receipt Voucher",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Invoice":
					if (currentUser.isPermitted("Finance:Invoice:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterInvoice())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Invoice",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Payment Voucher":
					if (currentUser.isPermitted("Finance:PaymentVoucher:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterPaymentVoucher())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Payment Voucher",
							Notification.Type.ERROR_MESSAGE);
					}
						
						break
					case "Post Project":
					if (currentUser.isPermitted("ProjectManagement:PostProject:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterPostProject())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Post Project",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Complaint":
					if (currentUser.isPermitted("Tenant:Complaint:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterComplaint())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Tenant Complaint",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Home Assignment":
					if (currentUser.isPermitted("Tenant:HomeAssignment:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterHomeAssignment())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Tenant Home Assignment",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Invoice Payment":
					if (currentUser.isPermitted("Tenant:InvoicePayment:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterInvoicePayment())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Tenant Invoice Payment",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Invoice Clearance":
					if (currentUser.isPermitted("Finance:InvoiceClearance:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterInvoiceClearance())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Invoice Clearance",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Project Voting":
					if (currentUser.isPermitted("ProjectManagement:ProjectVoting:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterProjectVote())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Project Voting",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Maintenance Fee":
					if (currentUser.isPermitted("Maintenance:MaintenanceFee:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterMaintenance())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Maintenance Fee",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Cash Bank Mutation":
					if (currentUser.isPermitted("Finance:CashBankMutation:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterCashBankMutation())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Finance Cash Bank Mutation",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Outstanding Invoice":
					if (currentUser.isPermitted("Tenant:OutstadingInvoice:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterOutstandingInvoice())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Outstanding Invoice",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Parking Registration":
					if (currentUser.isPermitted("Tenant:ParkingRegistration:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterParkingRegistration())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Parking Registration",
							Notification.Type.ERROR_MESSAGE);
					}
						
						break
					case "Permit":
					if (currentUser.isPermitted("Maintenance:Permit:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterPermit())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Permit",
							Notification.Type.ERROR_MESSAGE);
					}
						break
						
						
					case "Claim":
					if (currentUser.isPermitted("Maintenance:Claim:" + Constant.AccessType.View)) {
						panelContent.addComponent(new MasterClaim())
					}
					else
					{
						Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk mengakses Claim",
							Notification.Type.ERROR_MESSAGE);
					}
						break
				}
			}
		}
	}

	public boolean SignOut()
	{
		// "Logout" the user
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated())
		{
			currentUser.logout();
			currentUser.session.stop()
			getSession().setAttribute("user", null);
			getSession().close()
			getUI().getNavigator().navigateTo("");
		}
		// Refresh this view, should redirect to login view
		getUI().getPage().getCurrent().getJavaScript().execute("window.location.reload();");
		//getApplication().getWindow().executeJavaScript("window.location.reload();");
		return true
	}

	public void windowChangePassword(String caption) {
		Subject currentUser = SecurityUtils.getSubject();
		def user = ShiroUser.findByUsername(currentUser.getPrincipal())
		windowChgPass = new Window(caption);
		windowChgPass.setModal(true);
		def layout = new FormLayout() //FormLayout();
		layout.setMargin(true);
		//layout.setWidth("600px");
		//layout.setHeight("300px");
		windowChgPass.setContent(layout);
		def name = new Label()
		textId = new TextField("User ID:");
		textId.setValue(user.id.toString())
		textId.setReadOnly(true)
		layout.addComponent(textId) //"left: 20px; top: 20px;"
		textName = new TextField("UserName:");
		textName.setValue(user.username)
		textName.setReadOnly(true)
		layout.addComponent(textName)
		textOldPassword = new PasswordField("Old Password:");
		layout.addComponent(textOldPassword)
		textNewPassword = new PasswordField("New Password:");
		layout.addComponent(textNewPassword)
		textConfirmPassword = new PasswordField("Confirm Password:");
		layout.addComponent(textConfirmPassword)
		layout.addComponent(createSaveButton())
		layout.addComponent(createCancelButton())
		getUI().addWindow(windowChgPass);
	}

	private Button createCancelButton() {
		def button = new Button("Cancel", new Button.ClickListener() {
					void buttonClick(Button.ClickEvent event) {
						windowChgPass.setCaption(textName.getValue())
						textName.discard()
						windowChgPass.close()
					}
				})
	}

	private Button createSaveButton() {
		def button = new Button("Save", new Button.ClickListener() {

					void buttonClick(Button.ClickEvent event) {
						try{
							def object = [id:textId.getValue(),
								username:textName.getValue(),
								passwordHash:textNewPassword.getValue()]

							object =  Grails.get(UserService).updatePasswordObject(object, textOldPassword.getValue(), textConfirmPassword.getValue())


							if (object.errors.hasErrors())
							{
								textName.setData("username")
								textNewPassword.setData("newpasswordHash")
								textOldPassword.setData("oldpasswordHash")
								textConfirmPassword.setData("confirmpasswordHash")
								Object[] tv = [textName, textNewPassword, textOldPassword, textConfirmPassword]
								generalFunction.setErrorUI(tv,object)
							}
							else
							{
								windowChgPass.close()
							}
							//initTable()
						}catch (Exception e)// (MalformedURLException e)
						{
							Notification.show("Error\n",
									e.getLocalizedMessage(),
									Notification.Type.ERROR_MESSAGE);
						}


					}
				})
		//button.setClickShortcut(KeyCode.ENTER);
		//button.addStyleName("primary"); //Reindeer.BUTTON_DEFAULT
	}

	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		getPage().setTitle("ESTATE MANAGEMENT");
		// Create a navigator to control the views
		navigator = new Navigator(this, this);

		// Create and register the views
		navigator.addView("", new LoginForm());

		//		navigator.addView("", new MainView());
		navigator.addView("MAINVIEW", new MainView());

		// We use a view change handler to ensure the user is always redirected
		// to the login view if the user is not logged in.
		//
		navigator.addViewChangeListener(new ViewChangeListener() {


					@Override
					public boolean beforeViewChange(ViewChangeEvent event) {

						// Check if a user has logged in
						Subject currentUser = SecurityUtils.getSubject();
						boolean isLoggedIn = currentUser.isAuthenticated();
						boolean isLoginView = event.getNewView() instanceof LoginForm;

						if (!isLoggedIn && !isLoginView) {
							// Redirect to login view always if a user has not yet
							// logged in
							navigator.navigateTo(""/*LoginForm.NAME*/);
							return false;

						} else if (isLoggedIn && isLoginView) {
							// If someone tries to access to login view while logged in,
							// then cancel
							navigator.navigateTo("MAINVIEW")
							return false;
						}

						return true;
					}

					@Override
					public void afterViewChange(ViewChangeEvent event) {

					}
				});
	}

}
