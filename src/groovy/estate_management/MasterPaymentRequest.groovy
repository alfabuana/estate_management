package estate_management

import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat
import java.util.ArrayList;
import java.util.Date;

import estate_management.reportModel.PaymentRequestReportModel
import estate_management.widget.GeneralFunction





import net.sf.jasperreports.engine.JasperRunManager
import net.sf.jasperreports.engine.JRException
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
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
import com.vaadin.server.StreamResource
import com.vaadin.server.UserError
import com.vaadin.ui.Button
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Component
import com.vaadin.shared.ui.datefield.Resolution
import com.vaadin.ui.BrowserFrame
import com.vaadin.ui.DateField
import com.vaadin.ui.Embedded
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
import com.vaadin.shared.ui.window.WindowMode
import estate_management.PaymentRequestService







import com.vaadin.grails.Grails
import estate_management.widget.Constant
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
class MasterPaymentRequest extends VerticalLayout{
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
	private ComboBox cmbVendor
	private ComboBox cmbProject
	private TextArea textDescription
	private TextField textCode
	private TextField textAmount
	private DateField textRequestDate
	private DateField textDueDate

	private TextField textIdDetail
	private TextField textCodeDetail
	private TextField textAmountDetail
	private TextArea textDescriptionDetail

	//==============================

	private Table table = new Table()
	private Table tableDetail = new Table()
	private BeanItemContainer<PaymentRequest> tableContainer;
	private BeanItemContainer tableDetailContainer
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Finance:PaymentRequest:"
	//						Constant.MenuName.Item + ":";
	
	private Subject currentUser
	public MasterPaymentRequest() {
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
							case "Print":
								if (table.getValue() != null)
									windowPrint("Print");
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
		MenuItem printMenu = menuBar.addItem("Print", mycommand)
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
		menuBarDetail.setWidth("100%")
		menuBarDetail.setVisible(false)
		addComponent(menuBarDetail)
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
								//								paymentRequestId : textId.getValue(),
								vendor:cmbVendor.getValue(),
								project:cmbProject.getValue(),
								username:String.valueOf(getSession().getAttribute("user")),
								description:textDescription.getValue(),
								code:textCode.getValue(),
								amount:textAmount.getValue().toString(),
								requestDate:textRequestDate.getValue(),
								dueDate:textDueDate.getValue()
							]

							if (object.id == "")
							{
								object =  Grails.get(PaymentRequestService).createObject(object)
							}
							else
							{
								object =  Grails.get(PaymentRequestService).updateObject(object)
							}


							if (object.errors.hasErrors())
							{
								cmbVendor.setData("vendor")
								cmbProject.setData("project")
								textDescription.setData("description")
								textCode.setData("code")
								textAmount.setData("amount")
								textRequestDate.setData("requestDate")
								textDueDate.setData("dueDate")
								Object[] tv = [cmbVendor,cmbProject,textDescription,textCode,textAmount,textRequestDate,textDueDate]
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
	private Button createSaveDetailButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {

					void buttonClick(Button.ClickEvent event) {
						try{
							def object = [id:textIdDetail.getValue(),
								paymentRequestId : textId.getValue(),
								code:textCodeDetail.getValue(),
								amount:textAmountDetail.getValue().toString(),
								description:textDescriptionDetail.getValue(),
								username:getSession().getAttribute("user")
							]

							if (object.id == "")
							{
								object =  Grails.get(PaymentRequestDetailService).createObject(object)
							}
							else
							{
								object =  Grails.get(PaymentRequestDetailService).updateObject(object)
							}

							if (object.errors.hasErrors())
							{
								textCodeDetail.setData("code")
								textAmountDetail.setData("amount")
								textDescriptionDetail.setData("description")
								Object[] tv = [textCodeDetail,textAmountDetail,textDescriptionDetail]
								generalFunction.setErrorUI(tv,object)
							}
							else
							{
								window.close()
								initTableDetail()
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
							object = Grails.get(PaymentRequestService).softDeletedObject(object)
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
	//	===========================================
	//	WINDOW DELETE DETAIL
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowDeleteDetail(String caption) {
				if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id").toString()]
							object = Grails.get(PaymentRequestDetailService).softDeletedObject(object)
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
				} else {
					Notification.show("Access Denied\n",
						"Anda tidak memiliki izin untuk Menghapus Record",
						Notification.Type.ERROR_MESSAGE);
				}
	}
	//	===========================================
	//	WINDOW CONFIRM
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowConfirm(String caption) {
				if (currentUser.isPermitted(Title + Constant.AccessType.Confirm)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
								,username:getSession().getAttribute("user")]
							object = Grails.get(PaymentRequestService).confirmObject(object)
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
						"Anda tidak memiliki izin untuk Mengkonfirmasi Record",
						Notification.Type.ERROR_MESSAGE);
				}
	}
	//	===========================================
	//	WINDOW UNCONFIRM
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowUnConfirm(String caption) {
				if (currentUser.isPermitted(Title + Constant.AccessType.Unconfirm)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
							object = Grails.get(PaymentRequestService).unConfirmObject(object)
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
						"Anda tidak memiliki izin untuk Unkonfirmasi Record",
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
		textCode = new TextField("Code:");
		textCode.setPropertyDataSource(item.getItemProperty("code"))
		textCode.setBuffered(true)
		textCode.setImmediate(false)
		textCode.setReadOnly(true)
		layout.addComponent(textCode)
		cmbVendor = new ComboBox("Vendor:");
		def beanVendor = new BeanItemContainer<Vendor>(Vendor.class)
		def vendorList = Grails.get(VendorService).getListDeleted()
		beanVendor.addAll(vendorList)
		cmbVendor.setContainerDataSource(beanVendor)
		cmbVendor.setItemCaptionPropertyId("name")
		cmbVendor.select(cmbVendor.getItemIds().find{ it.id == item.getItemProperty("vendor.id").value})
		cmbVendor.setBuffered(true)
		cmbVendor.setImmediate(false)
		layout.addComponent(cmbVendor)
		cmbProject = new ComboBox("Project:");
		def beanProject = new BeanItemContainer<Project>(Project.class)
		def projectList = Grails.get(ProjectService).getListUnFinish()
		beanProject.addAll(projectList)
		cmbProject.setContainerDataSource(beanProject)
		cmbProject.setItemCaptionPropertyId("title")
		cmbProject.select(cmbProject.getItemIds().find{ it.id == item.getItemProperty("project.id").value})
		cmbProject.setBuffered(true)
		cmbProject.setImmediate(false)
		layout.addComponent(cmbProject)
		textDescription = new TextArea("Description:");
		textDescription.setPropertyDataSource(item.getItemProperty("description"))
		textDescription.setBuffered(true)
		textDescription.setImmediate(false)
		layout.addComponent(textDescription)

		textAmount = new TextField("Amount:");
		//			textAmount.setPropertyDataSource(item.getItemProperty("amount"))
		textAmount.setValue(item.getItemProperty("amount").toString())
		textAmount.setReadOnly(true)
		textAmount.setBuffered(true)
		textAmount.setImmediate(false)
		layout.addComponent(textAmount)
		textRequestDate = new DateField("Request Date:");
		textRequestDate.setPropertyDataSource(item.getItemProperty("requestDate"))
		textRequestDate.setBuffered(true)
		textRequestDate.setImmediate(false)
		layout.addComponent(textRequestDate)
		textDueDate = new DateField("Due Date:");
		textDueDate.setPropertyDataSource(item.getItemProperty("dueDate"))
		textDueDate.setBuffered(true)
		textDueDate.setImmediate(false)
		layout.addComponent(textDueDate)
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
		textCode = new TextField("Code:")
		textCode.setReadOnly(true)
		layout.addComponent(textCode)
		cmbVendor = new ComboBox("Vendor:")
		def beanVendor = new BeanItemContainer<Vendor>(Vendor.class)
		def vendorList = Grails.get(VendorService).getListDeleted()
		beanVendor.addAll(vendorList)
		cmbVendor.setContainerDataSource(beanVendor)
		cmbVendor.setItemCaptionPropertyId("name")
		layout.addComponent(cmbVendor)
		cmbProject = new ComboBox("Project:")
		def beanProject = new BeanItemContainer<Project>(Project.class)
		def projectList = Grails.get(ProjectService).getListUnFinish()
		beanProject.addAll(projectList)
		cmbProject.setContainerDataSource(beanProject)
		cmbProject.setItemCaptionPropertyId("title")
		layout.addComponent(cmbProject)
		textDescription = new TextArea("Description:")
		layout.addComponent(textDescription)

		textAmount = new TextField("Amount:")
		textAmount.setReadOnly(true)
		layout.addComponent(textAmount)
		textRequestDate = new DateField("Request Date:")
		layout.addComponent(textRequestDate)
		textDueDate = new DateField("Due Date:")
		layout.addComponent(textDueDate)
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
//		layout.addComponent(createSaveButton())
		//			==================

		//			===================
		//			TOMBOL CANCEL
		//			===================
//		layout.addComponent(createCancelButton())
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
	//	=======================
	//	WINDOW ADD DETAIL
	//	=======================
	private void windowAddDetail(item,String caption) {
				if (currentUser.isPermitted(Title + Constant.AccessType.Add)) {
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
		textAmountDetail = new TextField("Amount:");
		layout3.addComponent(textAmountDetail)
		textDescriptionDetail = new TextArea("Description:");
		layout3.addComponent(textDescriptionDetail)
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
		def horizontal3 = new HorizontalLayout()
		layout3.addComponent(horizontal3)
		horizontal3.addComponent(createSaveDetailButton())
		horizontal3.addComponent(createCancelButton())

		getUI().addWindow(window);
				} else {
					Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk Membuat Record",
							Notification.Type.ERROR_MESSAGE);
				}
	}
	//	========================
	//	WINDOW EDIT DETAIL
	//	========================
	//@RequiresPermissions("Transaction:DeliveryOrder:Edit")
	private void windowEditDetail(item,itemDetail,String caption) {
				if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
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
		textDescriptionDetail = new TextArea("Description:")
		textDescriptionDetail.setPropertyDataSource(itemDetail.getItemProperty("description"))
		textDescriptionDetail.setBuffered(true)
		layout3.addComponent(textDescriptionDetail)
		def horizontal3 = new HorizontalLayout()
		layout3.addComponent(horizontal3)
		horizontal3.addComponent(createSaveDetailButton())
		horizontal3.addComponent(createCancelButton())
		getUI().addWindow(window);
				} else {
					Notification.show("Access Denied\n",
							"Anda tidak memiliki izin untuk Mengubah Record",
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
		tableContainer = new BeanItemContainer<PaymentRequest>(PaymentRequest.class);
		//fillTableContainer(tableContainer);
		itemlist = Grails.get(PaymentRequestService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("createdBy.id")
		tableContainer.addNestedContainerProperty("createdBy.username")
		tableContainer.addNestedContainerProperty("updatedBy.id")
		tableContainer.addNestedContainerProperty("updatedBy.username")
		tableContainer.addNestedContainerProperty("confirmedBy.id")
		tableContainer.addNestedContainerProperty("confirmedBy.username")
		tableContainer.addNestedContainerProperty("vendor.id")
		tableContainer.addNestedContainerProperty("vendor.name")
		tableContainer.addNestedContainerProperty("project.id")
		tableContainer.addNestedContainerProperty("project.title")
		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("vendor.name","Vendor name")
		table.setColumnHeader("requestDate","Request Date")
		//		table.setColumnHeader("startDate","Start Date")
		//		table.setColumnHeader("startTime","Start Time")
		//		table.setColumnHeader("durasi","Duration")
		//		table.setColumnHeader("dateStartUsing","Date Start Using")
		//		table.setColumnHeader("dateEndUsing","Date End Using")
		table.visibleColumns = ["id","vendor.name","project.title","description","code","amount","requestDate","dueDate","isConfirmed","confirmationDate","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username","confirmedBy.username"
		]
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
							menuBarDetail.setVisible(false)
						}
					}
				})

	}

	void initTableDetail() {
		tableDetailContainer = new BeanItemContainer<PaymentRequestDetail>(PaymentRequestDetail.class);
		def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
		def itemListDetail = Grails.get(PaymentRequestDetailService).getList(ind)
		tableDetailContainer.addNestedContainerProperty("paymentRequest.id")
		tableDetailContainer.addNestedContainerProperty("createdBy.id")
		tableDetailContainer.addNestedContainerProperty("createdBy.username")
		tableDetailContainer.addNestedContainerProperty("updatedBy.id")
		tableDetailContainer.addNestedContainerProperty("updatedBy.username")
		tableDetailContainer.addNestedContainerProperty("confirmedBy.id")
		tableDetailContainer.addNestedContainerProperty("confirmedBy.username")
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.id");
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.sku");
		//		tableDetailContainer.addNestedContainerProperty("deliveryOrder.id");
		tableDetailContainer.addAll(itemListDetail)
		tableDetail.setColumnHeader("paymentRequest.id","Payment Request Id")
		tableDetail.setContainerDataSource(tableDetailContainer);
		tableDetail.visibleColumns = ["id","paymentRequest.id","code","amount","description","isConfirmed","confirmationDate","isDeleted","dateCreated","lastUpdated","createdBy.username","updatedBy.username","confirmedBy.username"]
		tableDetail.setSelectable(true)
		tableDetail.setImmediate(false)
		tableDetail.setVisible(true)
		tableDetail.setSizeFull()
		menuBarDetail.setVisible(true)
	}
	private void windowPrint(String caption){
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
							object = Grails.get(PaymentRequestService).printObject(object)
							if (object.hasErrors())
							{
								Object[] tv = [textId]
								generalFunction.setErrorUI(tv,object)
							}
							else
							{
								final Map map = new HashMap();
								StreamResource.StreamSource source = new StreamResource.StreamSource() {
											public InputStream getStream() {
												byte[] b = null;
												try {
													DataBeanMaker dataBeanMaker = new DataBeanMaker();
													object = Grails.get(PaymentRequestDetailService).getList(object.id)
													ArrayList dataBeanList = dataBeanMaker.getDataBeanList(object);
													JRBeanCollectionDataSource beanColDataSource = new
															JRBeanCollectionDataSource(dataBeanList);
													Map parameters = new HashMap();
													b = JasperRunManager.runReportToPdf(getClass().
															getClassLoader().getResourceAsStream("reports/PaymentRequest.jasper"),
															map,beanColDataSource);
												} catch (JRException ex) {
													ex.printStackTrace();
												}

												return new ByteArrayInputStream(b);
											}
										};
								Date curDate = new Date()
								SimpleDateFormat format = new SimpleDateFormat("yyMMddhhMMss");
								String now = format.format(curDate)
								StreamResource resource = new StreamResource(source, "PaymentRequest${now}.pdf");
								resource.setMIMEType("application/pdf");
								BrowserFrame browser = new BrowserFrame("Browser");
								browser.setWidth("600px");
								browser.setHeight("400px");
								VerticalLayout v = new VerticalLayout();
								Embedded e = new Embedded("", resource);
								e.setSizeFull();
								e.setHeight("600px")
								e.setType(Embedded.TYPE_BROWSER)
								v.addComponent(e);
								Window w = new Window()
								w.setContent(v);
								w.setWindowMode(WindowMode.MAXIMIZED)
								UI.getCurrent().addWindow(w);

							}
						} else {

						}
					}
				})

	}


	private class DataBeanMaker {
		public ArrayList getDataBeanList(def object) {
			ArrayList<PaymentRequestReportModel> dataBeanList = new ArrayList<PaymentRequestReportModel>();
			for(data in object)
			{
				dataBeanList.add(produce(data.paymentRequest.code,data.paymentRequest.requestDate,
						data.paymentRequest.description, data.paymentRequest.dueDate, data.id.toInteger(),
						data.description,
						data.amount, data.paymentRequest.amount, data.paymentRequest.vendor.name, data.paymentRequest.vendor.description));
			}
			return dataBeanList
		}

		private PaymentRequestReportModel produce(
				String code,
				Date requestDate,
				String description,
				Date dueDate,
				Integer idDetail,
				String descriptionDetail,
				Double amountDetail,
				Double totalAmount,
				String vendorName,
				String vendorDescription
		) {

			PaymentRequestReportModel dataBean = new PaymentRequestReportModel();

			dataBean.setCode(code);
			dataBean.setRequestDate(requestDate)
			dataBean.setDescription(description)
			dataBean.setDueDate(dueDate)
			dataBean.setIdDetail(idDetail)
			dataBean.setDescriptionDetail(descriptionDetail)
			dataBean.setAmountDetail(amountDetail)
			dataBean.setTotalAmount(totalAmount)
			dataBean.setVendorName(vendorName)
			dataBean.setVendorDescription(vendorDescription)

			return dataBean;
		}
	}



}

