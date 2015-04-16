package estate_management

import java.awt.event.ItemEvent;
import estate_management.widget.GeneralFunction
import com.vaadin.ui.CheckBox;
import org.vaadin.dialogs.ConfirmDialog
import com.vaadin.data.Property
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.data.Property.ValueChangeListener
import com.vaadin.data.fieldgroup.BeanFieldGroup
import com.vaadin.data.fieldgroup.FieldGroup
import com.vaadin.data.fieldgroup.FieldGroup.CommitException
import com.vaadin.data.util.BeanItem
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.event.Action
import com.vaadin.event.ItemClickEvent
import com.vaadin.event.Action.Handler
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.event.MouseEvents.ClickEvent
import com.vaadin.event.MouseEvents.ClickListener
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.UserError
import com.vaadin.ui.Button
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Component
import com.vaadin.shared.ui.datefield.Resolution
import com.vaadin.ui.DateField
import com.vaadin.ui.Field
import com.vaadin.ui.FormLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.MenuBar
import com.vaadin.ui.Notification
import com.vaadin.ui.Table
import com.vaadin.ui.TextArea
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import com.vaadin.ui.MenuBar.MenuItem
import estate_management.RoleService
import com.vaadin.grails.Grails
import estate_management.widget.Constant
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
class MasterRole extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	private TextField textSKU
	private TextField textDescription

	//==============================
	private TextField textName
	//==============================

	private Table table = new Table();
	private Table tableDetail = new Table()
	private BeanItemContainer<ShiroRole> tableContainer;
	private BeanItemContainer tableDetailContainer
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "User Role"
	//						Constant.MenuName.Item + ":";
	private Subject currentUser
	public MasterRole() {
		currentUser = SecurityUtils.getSubject();

		initTable();

		HorizontalLayout menu = new HorizontalLayout()
		menu.setWidth("100%")
		//		menu.addComponent(createAddButton())
		//		menu.addComponent(createUpdateButton())
		//		menu.addComponent(createDeleteButton())
		//
		addComponent(menu)


		//		EVENT CLICK MENUBAR
		menuBar = new MenuBar()
		MenuBar.Command mycommand = new MenuBar.Command() {
					public void menuSelected(MenuItem selectedItem) {
						switch(selectedItem.getText())
						{
							case "Add":
								def item = new BeanItem<ShiroRole>(tableContainer);
								windowAdd("Add");
								break
							case "Edit":
								if (table.getValue() != null)
									windowEdit(tableContainer.getItem(table.getValue()),"Edit");
								break;
							case "Delete":
								if (table.getValue() != null)
									windowDelete("Delete");
								break;
								break;
						}
					}
				};
		//	END EVENT CLICK

		// UNTUK BUTTON MENU
		MenuItem saveMenu =  menuBar.addItem("Add",mycommand)
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")
		//	END BUTTON MENU

		addComponent(table)
		addComponent(tableDetail)
		//				table.setPageLength(table.size())
	}


	private Button createCancelButton() {
		def saveButton = new Button("Cancel", new Button.ClickListener() {
					void buttonClick(Button.ClickEvent event) {
						//				window.setCaption(textSKU.getValue())
						//				textSKU.discard()
						window.close()
					}
				})
	}

	private Button createSaveButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {

					void buttonClick(Button.ClickEvent event) {
						try{
							def object = [id:textId.getValue(),
								name:textName.getValue(),
							]

							if (object.id == "")
							{
								object =  Grails.get(RoleService).createObject(object)
							}
							else
							{
								object =  Grails.get(RoleService).updateObject(object)
							}


							if (object.errors.hasErrors())
							{
								textName.setData("name")
								Object[] tv = [textName]
								generalFunction.setErrorUI(tv,object)
							}
							else
							{
								window.close()
								initTable()
							}

						}catch (Exception e)
						{
							Notification.show("Error\n",
									e.getMessage(),
									Notification.Type.ERROR_MESSAGE);
						}


					}
				})
	}


	//	===========================================
	//	WINDOW DELETE
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowDelete(String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
			ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
					new ConfirmDialog.Listener() {
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
								object = Grails.get(RoleService).softDeleteObject(object)
								if (object.errors.hasErrors())
								{
									Object[] tv = [textId]
									generalFunction.setErrorUI(tv,object)
								}
								else
								{
									initTable()
								}
							} else {

							}
						}
					})
		} else {
			Notification.show("Access Denied\n",
					"Anda tidak memiliki izin untuk Menghapus Record",
					Notification.Type.ERROR_MESSAGE);
		}
	}
	//	========================================
	//WINDOW EDIT
	//	========================================
	//@RequiresPermissions("Master:Item:Edit")
	private void windowEdit(def item,String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Id:");
			textId.setPropertyDataSource(item.getItemProperty("id"))
			textId.setReadOnly(true)
			layout.addComponent(textId)
			textName = new TextField("Name:");
			textName.setPropertyDataSource(item.getItemProperty("name"))
			textName.setBuffered(true)
			textName.setImmediate(false)
			layout.addComponent(textName)
			def horizontal = new HorizontalLayout()
			layout.addComponent(horizontal)
			horizontal.addComponent(createSaveButton())
			horizontal.addComponent(createCancelButton())
			getUI().addWindow(window);
		} else {
			Notification.show("Access Denied\n",
					"Anda tidak memiliki izin untuk Mengubah Record",
					Notification.Type.ERROR_MESSAGE);
		}
	}


	//	========================================
	//WINDOW ADD
	//	========================================
	//@RequiresPermissions("Master:Item:Add")
	private void windowAdd(String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Add)) {
			window = new Window(caption);
			window.setModal(true);
			def layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Id:");
			textId.setReadOnly(true)
			layout.addComponent(textId)
			textName = new TextField("Name:")
			layout.addComponent(textName)
			//			def textArea = new TextArea("Text Area")
			//			layout.addComponent(textArea)
			//			def dateField = new DateField("Date Field")
			//			layout.addComponent(dateField)
			//			def comboBox = new ComboBox("combo Box")
			//			comboBox.addItem("test")
			//			comboBox.addItem("test2")
			//			layout.addComponent(comboBox)
			//
			//			===================
			//TOMBOL SAVE
			//			===================
			//			layout.addComponent(createSaveButton())
			//			==================

			//			===================
			//			TOMBOL CANCEL
			//			===================
			//			layout.addComponent(createCancelButton())
			def horizontal = new HorizontalLayout()
			layout.addComponent(horizontal)
			horizontal.addComponent(createSaveButton())
			horizontal.addComponent(createCancelButton())
			//			===================
			getUI().addWindow(window);
		} else {
			Notification.show("Access Denied\n",
					"Anda tidak memiliki izin untuk Membuat Record",
					Notification.Type.ERROR_MESSAGE);
		}
	}

	void updateTable() {
		//		if (table.size() > MAX_PAGE_LENGTH) {
		//		table.setPageLength(MAX_PAGE_LENGTH);
		//		} else {
		//		table.setPageLength(table.size());
		//		}
		//		table.markAsDirtyRecursive();
	}

	void initTable() {
		tableContainer = new BeanItemContainer<ShiroRole>(ShiroRole.class);
		//fillTableContainer(tableContainer);
		itemlist = Grails.get(RoleService).getList()
		tableContainer.addAll(itemlist)
		//		tableContainer.addNestedContainerProperty("facility1.id")
		//		tableContainer.addNestedContainerProperty("facility1.nama")
		//		tableContainer.addNestedContainerProperty("customer1.id")
		//		tableContainer.addNestedContainerProperty("customer1.nama")
		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("name","Name")
		table.visibleColumns = ["id","name","dateCreated","lastUpdated","isDeleted"]
		table.setSelectable(true)
		table.setImmediate(false)
		//		table.setPageLength(table.size())
		table.setSizeFull()
		table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
					@Override
					public void itemClick(ItemClickEvent itemClickEvent) {

						//				selectedRow = table.getValue()

						//				print selectedRow
					}
				});
		table.addValueChangeListener(new Property.ValueChangeListener() {
					public void valueChange(ValueChangeEvent event) {
						selectedRow = table.getValue()
						if (selectedRow != null) {
							initTableDetail()

						}
						else
						{
							tableDetail.setVisible(false)
						}
					}
				})

	}
	void initTableDetail() {
		// Table with a component column in non-editable mode
		//		final TableDetail tableDetail = new TableDetail("My Table");
//		tableDetail.addContainerProperty("id", String.class, null);
		tableDetail.addContainerProperty("menu", String.class, null);
		tableDetail.addContainerProperty("item", String.class, null);
		tableDetail.addContainerProperty("view", CheckBox.class, null);
		tableDetail.addContainerProperty("add", CheckBox.class, null);
		tableDetail.addContainerProperty("edit", CheckBox.class, null);
		tableDetail.addContainerProperty("delete", CheckBox.class, null);
		tableDetail.addContainerProperty("confirm", CheckBox.class, null);
		tableDetail.addContainerProperty("unconfirm", CheckBox.class, null);
		tableDetail.addContainerProperty("print", CheckBox.class, null);
		tableDetail.addContainerProperty("clear", CheckBox.class, null);
		tableDetail.addContainerProperty("unclear", CheckBox.class, null);
		tableDetail.addContainerProperty("finish", CheckBox.class, null);
		tableDetail.addContainerProperty("unfinish", CheckBox.class, null);

		//		// Insert this data
		def people = [["menu":Constant.MenuGroup.ProjectManagement.toString(),"item":Constant.MenuName.ProjectVoting.toString()],
			["menu":Constant.MenuGroup.ProjectManagement.toString(),"item":Constant.MenuName.PostProject.toString()],
			["menu":Constant.MenuGroup.Master.toString(),"item":Constant.MenuName.Home.toString()],
			["menu":Constant.MenuGroup.Master.toString(),"item":Constant.MenuName.User.toString()],
			["menu":Constant.MenuGroup.Master.toString(),"item":Constant.MenuName.UserRole.toString()],
			["menu":Constant.MenuGroup.Master.toString(),"item":Constant.MenuName.Vendor.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.CashBank.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.CashBankAdjustment.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.CashBankMutation.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.PaymentRequest.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.PaymentVoucher.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.ReceiptVoucher.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.Invoice.toString()],
			["menu":Constant.MenuGroup.Finance.toString(),"item":Constant.MenuName.InvoiceClearance.toString()],
			["menu":Constant.MenuGroup.Maintenance.toString(),"item":Constant.MenuName.MaintenanceFee.toString()],
			["menu":Constant.MenuGroup.Maintenance.toString(),"item":Constant.MenuName.Permit.toString()],
			["menu":Constant.MenuGroup.Maintenance.toString(),"item":Constant.MenuName.Claim.toString()],
			["menu":Constant.MenuGroup.Tenant.toString(),"item":Constant.MenuName.Complaint.toString()],
			["menu":Constant.MenuGroup.Tenant.toString(),"item":Constant.MenuName.HomeAssignment.toString()],
			["menu":Constant.MenuGroup.Tenant.toString(),"item":Constant.MenuName.InvoicePayment.toString()],
			["menu":Constant.MenuGroup.Tenant.toString(),"item":Constant.MenuName.OutstandingInvoice.toString()],
			["menu":Constant.MenuGroup.Tenant.toString(),"item":Constant.MenuName.ParkingRegistration.toString()]]

		Integer x = 0
		// Insert the data and the additional component column
		
		def roleList = ShiroRole.find{
			id == 1
		}
		
		for (def a in people) {
			x++

			Integer itemId = x;

			//			TextArea area = new TextArea(null, people[i][1]);
			//			area.setRows(2);

			final CheckBox checkboxView = new CheckBox();
			final CheckBox checkboxAdd = new CheckBox();
			final CheckBox checkboxEdit = new CheckBox();
			final CheckBox checkboxDelete = new CheckBox();
			final CheckBox checkboxConfirm = new CheckBox();
			final CheckBox checkboxUnconfirm = new CheckBox();
			final CheckBox checkboxPrint = new CheckBox();
			final CheckBox checkboxClear = new CheckBox();
			final CheckBox checkboxUnclear = new CheckBox();
			final CheckBox checkboxFinish = new CheckBox();
			final CheckBox checkboxUnfinish = new CheckBox();


			checkboxView.setData(a.menu+ ":"+a.item+":View")
			checkboxView.setValue(checkRoleValue(roleList.permissions,checkboxView.getData()))
			checkboxAdd.setData(a.menu+ ":"+a.item+":Add")
			checkboxAdd.setValue(checkRoleValue(roleList.permissions,checkboxAdd.getData()))
			checkboxEdit.setData(a.menu+ ":"+a.item+":Edit")
			checkboxEdit.setValue(checkRoleValue(roleList.permissions,checkboxEdit.getData()))
			checkboxDelete.setData(a.menu+ ":"+a.item+":Delete")
			checkboxDelete.setValue(checkRoleValue(roleList.permissions,checkboxDelete.getData()))
			checkboxConfirm.setData(a.menu+ ":"+a.item+":Confirm")
			checkboxConfirm.setValue(checkRoleValue(roleList.permissions,checkboxConfirm.getData()))
			checkboxUnconfirm.setData(a.menu+ ":"+a.item+":Unconfirm")
			checkboxUnconfirm.setValue(checkRoleValue(roleList.permissions,checkboxUnconfirm.getData()))
			checkboxPrint.setData(a.menu+ ":"+a.item+":Print")
			checkboxPrint.setValue(checkRoleValue(roleList.permissions,checkboxPrint.getData()))
			checkboxClear.setData(a.menu+ ":"+a.item+":Print")
			checkboxClear.setValue(checkRoleValue(roleList.permissions,checkboxClear.getData()))
			checkboxUnclear.setData(a.menu+ ":"+a.item+":Unclear")
			checkboxUnclear.setValue(checkRoleValue(roleList.permissions,checkboxUnclear.getData()))
			checkboxFinish.setData(a.menu+ ":"+a.item+":Finish")
			checkboxFinish.setValue(checkRoleValue(roleList.permissions,checkboxFinish.getData()))
			checkboxUnfinish.setData(a.menu+ ":"+a.item+":Unfinish"); // Store item ID
			checkboxUnfinish.setValue(checkRoleValue(roleList.permissions,checkboxUnfinish.getData()))

			Object [] values = new Object[13]
			
			values[0] = a.menu
			values[1] = a.item
			values[2] = checkboxView
			values[3] = checkboxAdd
			values[4] = checkboxEdit
			values[5] = checkboxDelete
			values[6] = checkboxConfirm
			values[7] = checkboxUnconfirm
			values[8] = checkboxPrint
			values[9] = checkboxClear
			values[10] = checkboxUnclear
			values[11] = checkboxFinish
			values[12] = checkboxUnfinish


			// Add an item with two components
			tableDetail.addItem(values, itemId);
		}


		tableDetail.setSelectable(true)
		tableDetail.setImmediate(false)
		tableDetail.setVisible(true)
		tableDetail.setSizeFull()
	}
	//		tableDetailContainer = new BeanItemContainer<InvoiceDetail>(InvoiceDetail.class);
	//		def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
	//		def itemListDetail = Grails.get(InvoiceDetailService).getList(ind)
	//		tableDetailContainer.addNestedContainerProperty("invoice.id")
	//		//					tableoDetailContainer.addNestedContainerProperty("salesOrderDetail.item.id");
	//		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.sku");
	//		//		tableDetailContainer.addNestedContainerProperty("deliveryOrder.id");
	//		tableDetailContainer.addAll(itemListDetail)
	//		tableDetail.setColumnHeader("invoice.id","Invoice Id")
	//		tableDetail.setContainerDataSource(tableDetailContainer);
	//		tableDetail.visibleColumns = ["id","code","description","amount","isConfirmed","confirmationDate","isDeleted","dateCreated","lastUpdated"]
	//		tableDetail.setSelectable(true)
	//		tableDetail.setImmediate(false)
	//		tableDetail.setVisible(true)
	//		tableDetail.setSizeFull()
	//		menuBarDetail.setVisible(true)

	private def checkRoleValue(object,name)
	{
		if (object.size() == 0)
		{
			return false
		}
		for (def permission in object)
		{
			if (permission == name)
			{
				return true
			}
		}
		return false
	}

}

	