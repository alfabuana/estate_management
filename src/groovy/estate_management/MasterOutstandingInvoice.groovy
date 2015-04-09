package estate_management
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import java.awt.event.ItemEvent;
import estate_management.widget.GeneralFunction
import org.vaadin.dialogs.ConfirmDialog
import com.vaadin.data.Property
import com.vaadin.data.Property.ValueChangeEvent
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
import estate_management.InvoiceService
import grails.converters.JSON
import com.vaadin.grails.Grails

class MasterOutstandingInvoice extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private MenuBar menuBarDetail
	private Window window
	private TextField textId
	private TextField textSKU
	//	private TextField textDescription

	//==============================
	private ComboBox cmbHome
	private TextField textCode
	private DateField textInvoiceDate
	private TextField textDescription
	//	private DateField textDueDate
	private TextField textTotalAmount

	private TextField textIdDetail
	private TextField textCodeDetail
	private TextField textAmountDetail
	private TextField textDescriptionDetail

	//==============================

	private Table table = new Table();
	private Table tableDetail = new Table()
	private BeanItemContainer<Invoice> tableContainer;
	private BeanItemContainer tableDetailContainer
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Outstanding Invoice"
	//						Constant.MenuName.Item + ":";
	Subject currentUser = SecurityUtils.getSubject();
	public MasterOutstandingInvoice() {
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
								def item = new BeanItem<PaymentRequest>(tableContainer);
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
							case "Confirm":
								if (table.getValue() != null)
									windowConfirm("Confirm");
								break;
							case "Unconfirm":
								if (table.getValue() != null)
									windowUnConfirm("Unconfirm");
								break;
							case "AddDetail":
								if (table.getValue() != null)
									windowAddDetail(tableContainer.getItem(table.getValue()),"AddDetail");
								break;
							case "EditDetail":
								if (tableDetail.getValue() != null)
									windowEditDetail(tableContainer.getItem(table.getValue()),
											tableDetailContainer.getItem(tableDetail.getValue())
											,"EditDetail");
								break;
							case "DeleteDetail":
								if (tableDetail.getValue() != null)
									windowDeleteDetail("DeleteDetail");
								break;
						}
					}
				};
		//	END EVENT CLICK

		// UNTUK BUTTON MENU
		MenuItem saveMenu =  menuBar.addItem("Add",mycommand)
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		MenuItem confirmMenu = menuBar.addItem("Confirm", mycommand)
		MenuItem unconfirmMenu = menuBar.addItem("Unconfirm", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")
		//	END BUTTON MENU

		addComponent(table)
		//		======================
		//		View Detail
		//		======================
		menuBarDetail = new MenuBar()
		MenuItem saveDetailMenu =  menuBarDetail.addItem("AddDetail",mycommand)
		MenuItem editDetailMenu = menuBarDetail.addItem("EditDetail", mycommand)
		MenuItem deleteDetailMenu = menuBarDetail.addItem("DeleteDetail",mycommand)
//		menuBarDetail.setWidth("100%")
//		menuBarDetail.setVisible(false)
//		addComponent(menuBarDetail)
		addComponent(tableDetail)

		//		==========================
		//		ENd View Detail
		//		==========================
		//		table.setPageLength(table.size())
		
	
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
								home:cmbHome.getValue(),
								username:String.valueOf(getSession().getAttribute("user")),
								code:textCode.getValue(),
								invoiceDate:textInvoiceDate.getValue(),
								description:textDescription.getValue(),
								//								  dueDate:textDueDate.getValue(),
								totalAmount:textTotalAmount.getValue()
							]

							if (object.id == "")
							{
								object =  Grails.get(InvoiceService).createObject(object)
							}
							else
							{
								object =  Grails.get(InvoiceService).updateObject(object)
							}


							if (object.errors.hasErrors())
							{
								cmbHome.setData("home")
								textCode.setData("code")
								textInvoiceDate.setData("invoiceDate")
								textDescription.setData("description")
								//						textDueDate.setData("dueDate")
								textTotalAmount.setData("totalAmount")
								Object[] tv = [cmbHome,textCode,textInvoiceDate,textDescription,textTotalAmount]
								generalFunction.setErrorUI(tv,object)
							}
							else
							{
								window.close()
							}
							initTable()
						}catch (Exception e)
						{
							Notification.show("Error\n",
									e.getMessage(),
									Notification.Type.ERROR_MESSAGE);
						}


					}
				})
	}
	private Button createSaveDetailButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {

					void buttonClick(Button.ClickEvent event) {
						try{
							def object = [id:textIdDetail.getValue(),
								invoiceId : textId.getValue(),
								code:textCodeDetail.getValue(),
								amount:textAmountDetail.getValue().toString(),
								description:textDescriptionDetail.getValue()
							]

							if (object.id == "")
							{
								object =  Grails.get(InvoiceDetailService).createObject(object)
							}
							else
							{
								object =  Grails.get(InvoiceDetailService).updateObject(object)
							}
							if (object.errors.hasErrors())
							{
								textCodeDetail.setData("code")
								textAmountDetail.setData("amount")
								textDescriptionDetail.setDate("description")
								Object[] tv = [textCodeDetail,textAmountDetail,textDescriptionDetail]
								generalFunction.setErrorUI(tv,object)
							}
							else
							{
								window.close()
							}
							initTableDetail()
							initTable()
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
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
							object = Grails.get(InvoiceService).softDeletedObject(object)
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
		//		} else {
		//			Notification.show("Access Denied\n",
		//				"Anda tidak memiliki izin untuk Menghapus Record",
		//				Notification.Type.ERROR_MESSAGE);
		//		}
	}
	//	===========================================
	//	WINDOW DELETE DETAIL
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowDeleteDetail(String caption) {
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id").toString()]
							object  = Grails.get(InvoiceDetailService).softDeletedObject(object)
							if (object.errors.hasErrors())
							{
								Object[] tv = [textId]
								generalFunction.setErrorUI(tv,object)
							}
							else
							{
								initTableDetail()
								initTable()
							}
						} else {
						}
					}
				})
		//		} else {
		//			Notification.show("Access Denied\n",
		//				"Anda tidak memiliki izin untuk Menghapus Record",
		//				Notification.Type.ERROR_MESSAGE);
		//		}
	}
	//	===========================================
	//	WINDOW CONFIRM
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowConfirm(String caption) {
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
								,username:getSession().getAttribute("user")]
							object = Grails.get(InvoiceService).confirmObject(object)
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
		//		} else {
		//			Notification.show("Access Denied\n",
		//				"Anda tidak memiliki izin untuk Menghapus Record",
		//				Notification.Type.ERROR_MESSAGE);
		//		}
	}
	//	===========================================
	//	WINDOW UNCONFIRM
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowUnConfirm(String caption) {
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
							object = Grails.get(InvoiceService).unConfirmObject(object)
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
		//		} else {
		//			Notification.show("Access Denied\n",
		//				"Anda tidak memiliki izin untuk Menghapus Record",
		//				Notification.Type.ERROR_MESSAGE);
		//		}
	}
	//	========================================
	//WINDOW EDIT
	//	========================================
	//@RequiresPermissions("Master:Item:Edit")
	private void windowEdit(def item,String caption) {
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
		window = new Window(caption);
		window.setModal(true);
		layout = new FormLayout();
		layout.setMargin(true);
		window.setContent(layout);
		textId = new TextField("Id:");
		textId.setPropertyDataSource(item.getItemProperty("id"))
		textId.setReadOnly(true)
		layout.addComponent(textId)
		textCode = new TextField("Code:");
		textCode.setPropertyDataSource(item.getItemProperty("code"))
		textCode.setBuffered(true)
		textCode.setImmediate(false)
		textCode.setReadOnly(true)
		layout.addComponent(textCode)
		cmbHome = new ComboBox("Home:");
		def beanHome = new BeanItemContainer<Home>(Home.class)
		def homeList = Grails.get(HomeService).getListDeleted()
		beanHome.addAll(userList)
		cmbHome.setContainerDataSource(beanHome)
		cmbHome.setItemCaptionPropertyId("name")
		cmbHome.select(cmbHome.getItemIds().find{ it.id == item.getItemProperty("home.id").value})
		cmbHome.setBuffered(true)
		cmbHome.setImmediate(false)
		layout.addComponent(cmbHome)
		
		textInvoiceDate = new DateField("Invoice Date:");
		textInvoiceDate.setPropertyDataSource(item.getItemProperty("invoiceDate"))
		textInvoiceDate.setBuffered(true)
		textInvoiceDate.setImmediate(false)
		layout.addComponent(textInvoiceDate)
		textDescription = new TextField("Description:");
		textDescription.setPropertyDataSource(item.getItemProperty("description"))
		textDescription.setBuffered(true)
		textDescription.setImmediate(false)
		layout.addComponent(textDescription)
		//			textDueDate = new DateField("Due Date:");
		//			textDueDate.setPropertyDataSource(item.getItemProperty("dueDate"))
		//			textDueDate.setBuffered(true)
		//			textDueDate.setImmediate(false)
		//			layout.addComponent(textDueDate)
		textTotalAmount = new TextField("Total Amount:");
		//			textTotalAmount.setPropertyDataSource(item.getItemProperty("totalAmount"))
		textTotalAmount.setValue(item.getItemProperty("totalAmount").toString())
		textTotalAmount.setBuffered(true)
		textTotalAmount.setImmediate(false)
		textTotalAmount.setReadOnly(true)
		layout.addComponent(textTotalAmount)
		layout.addComponent(createSaveButton())
		layout.addComponent(createCancelButton())
		getUI().addWindow(window);
		//		} else {
		//			Notification.show("Access Denied\n",
		//				"Anda tidak memiliki izin untuk Mengubah Record",
		//				Notification.Type.ERROR_MESSAGE);
		//		}
	}


	//	========================================
	//WINDOW ADD
	//	========================================
	//@RequiresPermissions("Master:Item:Add")
	private void windowAdd(String caption) {
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Add)) {
		println getSession()
		window = new Window(caption);
		window.setModal(true);
		def layout = new FormLayout();
		layout.setMargin(true);
		window.setContent(layout);
		textId = new TextField("Id:");
		textId.setReadOnly(true)
		layout.addComponent(textId)
		textCode = new TextField("Code:")
		textCode.setReadOnly(true)
		layout.addComponent(textCode)
		cmbHome = new ComboBox("Home:")
		def beanHome = new BeanItemContainer<Home>(Home.class)
		def homeList = Grails.get(HomeService).getListDeleted()
		beanHome.addAll(homeList)
		cmbHome.setContainerDataSource(beanHome)
		cmbHome.setItemCaptionPropertyId("name")
		layout.addComponent(cmbHome)
		
		textInvoiceDate = new DateField("Invoice Date:")
		layout.addComponent(textInvoiceDate)
		textDescription = new TextField("Description:")
		layout.addComponent(textDescription)
		//			textDueDate = new DateField("Due Date:")
		//			layout.addComponent(textDueDate)
		textTotalAmount = new TextField("Total Amount:")
		layout.addComponent(textTotalAmount)
		textTotalAmount.setReadOnly(true)

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
		layout.addComponent(createSaveButton())
		//			==================

		//			===================
		//			TOMBOL CANCEL
		//			===================
		layout.addComponent(createCancelButton())

		//			===================
		getUI().addWindow(window);
		//		} else {
		//			Notification.show("Access Denied\n",
		//				"Anda tidak memiliki izin untuk Membuat Record",
		//				Notification.Type.ERROR_MESSAGE);
		//		}
	}
	//	=======================
	//	WINDOW ADD DETAIL
	//	=======================
	private void windowAddDetail(item,String caption) {
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
		window = new Window(caption)
		window.setModal(true)
		def layout3 = new FormLayout()
		layout3.setMargin(true)
		window.setContent(layout3)
		textId = new TextField("Product Id:")
		textId.setPropertyDataSource(item.getItemProperty("id"))
		textId.setReadOnly(true)
		layout3.addComponent(textId)
		textIdDetail = new TextField("Detail Id:")
		textIdDetail.setReadOnly(true)
		layout3.addComponent(textIdDetail)
		textCodeDetail = new TextField("Code:");
		textCodeDetail.setReadOnly(true)
		layout3.addComponent(textCodeDetail)
		textDescriptionDetail = new TextField("Description:");
		layout3.addComponent(textDescriptionDetail)
		textAmountDetail = new TextField("Amount:");
		layout3.addComponent(textAmountDetail)
		
		//		comb = new ComboBox("Sales Order Detail Item:")
		//			tableSearchContainer = new BeanItemContainer<SalesOrderDetail>(SalesOrderDetail.class);
		//			itemlist = Grails.get(SalesOrderDetailService).getListForCombo(item.getItemProperty("salesOrder.id").toString())
		//			tableSearchContainer.addAll(itemlist)
		//			tableSearchContainer.addNestedContainerProperty("item.sku");
		//			comb.setContainerDataSource(tableSearchContainer)

		//			comb.setItemCaptionPropertyId("item.sku")
		//		layout3.addComponent(comb)
		//			textQuantity = new TextField("Quantity:")
		//		layout3.addComponent(textQuantity)
		layout3.addComponent(createSaveDetailButton())
		layout3.addComponent(createCancelButton())

		getUI().addWindow(window);
		//		} else {
		//			Notification.show("Access Denied\n",
		//					"Anda tidak memiliki izin untuk Mengubah Record",
		//					Notification.Type.ERROR_MESSAGE);
		//		}
	}
	//	========================
	//	WINDOW EDIT DETAIL
	//	========================
	//@RequiresPermissions("Transaction:DeliveryOrder:Edit")
	private void windowEditDetail(item,itemDetail,String caption) {
		//		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
		window = new Window(caption)
		window.setModal(true)
		def layout3 = new FormLayout()
		layout3.setMargin(true)
		window.setContent(layout3)
		textId = new TextField("Master Id:")
		textId.setPropertyDataSource(item.getItemProperty("id"))
		textId.setReadOnly(true)
		layout3.addComponent(textId)
		textIdDetail = new TextField("Detail Id:")
		textIdDetail.setPropertyDataSource(itemDetail.getItemProperty("id"))
		textIdDetail.setReadOnly(true)
		layout3.addComponent(textIdDetail)
		textCodeDetail = new TextField("Code:");
		textCodeDetail.setPropertyDataSource(itemDetail.getItemProperty("code"))
		textCodeDetail.setBuffered(true)
		textCodeDetail.setImmediate(false)
		textCodeDetail.setReadOnly(true)
		layout3.addComponent(textCodeDetail)
		textDescriptionDetail = new TextField("Description:");
		textDescriptionDetail.setPropertyDataSource(itemDetail.getItemProperty("description"))
		textDescriptionDetail.setBuffered(true)
		textDescriptionDetail.setImmediate(false)
		layout3.addComponent(textDescriptionDetail)
		//		comb = new ComboBox("Item:")

		//			tableSearchContainer = new BeanItemContainer<SalesOrderDetail>(SalesOrderDetail.class);
		//			itemlist = Grails.get(SalesOrderDetailService).getListForCombo(item.getItemProperty("salesOrder.id").toString())
		//			tableSearchContainer.addAll(itemlist)
		//			tableSearchContainer.addNestedContainerProperty("item.sku");
		//			comb.setContainerDataSource(tableSearchContainer)

		//		comb.setItemCaptionPropertyId("item.sku")
		//		layout3.addComponent(comb)
		//		comb.select(comb.getItemIds().find{ it.id == itemDetail.getItemProperty("salesOrderDetail.id").value})
		//
		//		layout3.addComponent(comb)
		textAmountDetail = new TextField("Amount:")
		//		textAmountDetail.setPropertyDataSource(itemDetail.getItemProperty("amount"))
		textAmountDetail.setValue(itemDetail.getItemProperty("amount").toString())
		textAmountDetail.setBuffered(true)
		layout3.addComponent(textAmountDetail)
		layout3.addComponent(createSaveDetailButton())
		layout3.addComponent(createCancelButton())

		getUI().addWindow(window);
		//		} else {
		//			Notification.show("Access Denied\n",
		//					"Anda tidak memiliki izin untuk Mengubah Record",
		//					Notification.Type.ERROR_MESSAGE);
		//		}
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
		tableContainer = new BeanItemContainer<Invoice>(Invoice.class);
		//fillTableContainer(tableContainer);
//		getSession().getAttribute("user")
		
		itemlist = Grails.get(InvoiceService).getListOutstanding(currentUser.getPrincipal())
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("createdBy.id")
		tableContainer.addNestedContainerProperty("createdBy.username")
		tableContainer.addNestedContainerProperty("updatedBy.id")
		tableContainer.addNestedContainerProperty("updatedBy.username")
		tableContainer.addNestedContainerProperty("confirmedBy.id")
		tableContainer.addNestedContainerProperty("confirmedBy.username")
		tableContainer.addNestedContainerProperty("home.id")
		tableContainer.addNestedContainerProperty("home.name")
		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("invoiceDate","Invoice Date")
		table.setColumnHeader("home.name","Home Name")
		table.setColumnHeader("totalAmount","Total Amount")
		//		table.setColumnHeader("startTime","Start Time")
		//		table.setColumnHeader("durasi","Duration")
		//		table.setColumnHeader("dateStartUsing","Date Start Using")
		//		table.setColumnHeader("dateEndUsing","Date End Using")
		table.visibleColumns = ["id","home.name","code","invoiceDate","description","dueDate","totalAmount","isConfirmed","confirmationDate","isCleared","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username","confirmedBy.username"]
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
//							menuBarDetail.setVisible(false)
						}
					}
				})

	}
	void initTableDetail() {
		tableDetailContainer = new BeanItemContainer<InvoiceDetail>(InvoiceDetail.class);
		def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
		def itemListDetail = Grails.get(InvoiceDetailService).getList(ind)
		tableDetailContainer.addNestedContainerProperty("invoice.id")
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.id");
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.sku");
		//		tableDetailContainer.addNestedContainerProperty("deliveryOrder.id");
		tableDetailContainer.addAll(itemListDetail)
		tableDetail.setColumnHeader("invoice.id","Invoice Id")
		tableDetail.setContainerDataSource(tableDetailContainer);
		tableDetail.visibleColumns = ["id","invoice.id","code","description","amount","isConfirmed","confirmationDate","isDeleted","dateCreated","lastUpdated"]
		tableDetail.setSelectable(true)
		tableDetail.setImmediate(false)
		tableDetail.setVisible(true)
		tableDetail.setSizeFull()
//		menuBarDetail.setVisible(true)
	}




}

