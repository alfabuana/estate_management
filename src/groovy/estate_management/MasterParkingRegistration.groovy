package estate_management

import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat
import java.util.ArrayList;
import java.util.Date;

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

import estate_management.ParkingRegistrationService
import grails.converters.JSON











import com.vaadin.grails.Grails

import estate_management.widget.Constant

import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
class MasterParkingRegistration extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private MenuBar menuBarDetail
	private Window window
	private TextField textId

	//==============================
	private ComboBox cmbHome
	private TextField textCarNumber

	//==============================

	private Table table = new Table();
	private Table tableDetail = new Table()
	private BeanItemContainer<ParkingRegistration> tableContainer;
	private BeanItemContainer tableDetailContainer
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Tenant:ParkingRegistration:"
	//						Constant.MenuName.Item + ":";
	private Subject currentUser
	public MasterParkingRegistration() {
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
							if (table.getValue() != null){
								def item = new BeanItem<ParkingRegistration>(tableDetailContainer);
								windowAdd("Add")};
								break
							case "Edit":
								if (tableDetail.getValue() != null)
									windowEdit(tableDetailContainer.getItem(tableDetail.getValue()),"Edit");
								break;
							case "Delete":
								if (tableDetail.getValue() != null)
									windowDelete("Delete");
								break;
							case "Confirm":
								if (tableDetail.getValue() != null)
									windowConfirm("Confirm");
								break;
							case "Unconfirm":
								if (tableDetail.getValue() != null)
									windowUnConfirm("Unconfirm");
								break;
//							case "Print":
//								if (table.getValue() != null)
//									windowPrint("Print");
//								break;
//							case "AddDetail":
//								if (table.getValue() != null)
//									windowAddDetail(tableContainer.getItem(table.getValue()),"AddDetail");
//								break;
//							case "EditDetail":
//								if (tableDetail.getValue() != null)
//									windowEditDetail(tableContainer.getItem(table.getValue()),
//											tableDetailContainer.getItem(tableDetail.getValue())
//											,"EditDetail");
//								break;
//							case "DeleteDetail":
//								if (tableDetail.getValue() != null)
//									windowDeleteDetail("DeleteDetail");
//								break;
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
//		MenuItem printMenu = menuBar.addItem("Print", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")
		//	END BUTTON MENU
		HorizontalLayout h1 = new HorizontalLayout()
//		h1.setWidth("100%")
		h1.addComponent(table)
		addComponent(h1)
		//		======================
		//		View Detail
		//		======================
//		menuBarDetail = new MenuBar()
//		MenuItem saveDetailMenu =  menuBarDetail.addItem("AddDetail",mycommand)
//		MenuItem editDetailMenu = menuBarDetail.addItem("EditDetail", mycommand)
//		MenuItem deleteDetailMenu = menuBarDetail.addItem("DeleteDetail",mycommand)
//		menuBarDetail.setWidth("100%")
//		menuBarDetail.setVisible(false)
		
//		h1.addComponent(menuBarDetail)
		h1.addComponent(tableDetail)
		
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
								carNumber:textCarNumber.getValue()
							]

							if (object.id == "")
							{
								object =  Grails.get(ParkingRegistrationService).createObject(object)
							}
							else
							{
								object =  Grails.get(ParkingRegistrationService).updateObject(object)
							}

							if (object.errors.hasErrors())
							{
								cmbHome.setData("home")
								textCarNumber.setData("carNumber")
								Object[] tv = [cmbHome,textCarNumber]
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
//	private Button createSaveDetailButton() {
//		def saveButton = new Button("Save", new Button.ClickListener() {
//
//					void buttonClick(Button.ClickEvent event) {
//						try{
//							def object = [id:textIdDetail.getValue(),
//								invoiceId : textId.getValue(),
//								code:textCodeDetail.getValue(),
//								amount:textAmountDetail.getValue().toString(),
//								description:textDescriptionDetail.getValue()
//							]
//							if (object.id == "")
//							{
//								object =  Grails.get(InvoiceDetailService).createObject(object)
//							}
//							else
//							{
//								object =  Grails.get(InvoiceDetailService).updateObject(object)
//							}
//							if (object.errors.hasErrors())
//							{
//								textCodeDetail.setData("code")
//								textAmountDetail.setData("amount")
//								textDescriptionDetail.setData("description")
//								Object[] tv = [textCodeDetail,textAmountDetail,textDescriptionDetail]
//								generalFunction.setErrorUI(tv,object)
//							}
//							else
//							{
//								window.close()
//								initTableDetail()
//								initTable()
//							}
//							
//						}catch (Exception e)
//						{
//							Notification.show("Error\n",
//									e.getMessage(),
//									Notification.Type.ERROR_MESSAGE);
//						}
//
//
//					}
//				})
//	}

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
							object = Grails.get(ParkingRegistrationService).softDeletedObject(object)
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
//	private void windowDeleteDetail(String caption) {
//				if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
//		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id") + " ? ",
//				new ConfirmDialog.Listener() {
//					public void onClose(ConfirmDialog dialog) {
//						if (dialog.isConfirmed()) {
//							def object = [id:tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id").toString()]
//							object  = Grails.get(InvoiceDetailService).softDeletedObject(object)
//							if (object.errors.hasErrors())
//							{
//								Object[] tv = [textId]
//								generalFunction.setErrorUI(tv,object)
//							}
//							else
//							{
//								initTableDetail()
//								initTable()
//							}
//						} else {
//
//						}
//					}
//				})
//				} else {
//					Notification.show("Access Denied\n",
//						"Anda tidak memiliki izin untuk Menghapus Record",
//						Notification.Type.ERROR_MESSAGE);
//				}
//	}
	//	===========================================
	//	WINDOW CONFIRM
	//	===========================================

	//@RequiresPermissions("Master:Item:Delete")
	private void windowConfirm(String caption) {
				if (currentUser.isPermitted(Title + Constant.AccessType.Confirm)) {
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id").toString()
								,username:getSession().getAttribute("user")]
							object = Grails.get(ParkingRegistrationService).confirmObject(object)
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
							object = Grails.get(ParkingRegistrationService).unConfirmObject(object)
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
		cmbHome = new ComboBox("Home:");
		def beanHome = new BeanItemContainer<Home>(Home.class)
		def homeList = Grails.get(HomeService).getListDeleted()
		beanHome.addAll(homeList)
		cmbHome.setContainerDataSource(beanHome)
		cmbHome.setItemCaptionPropertyId("name")
		cmbHome.select(cmbHome.getItemIds().find{ it.id == item.getItemProperty("home.id").value})
		cmbHome.setBuffered(true)
		cmbHome.setImmediate(false)
		layout.addComponent(cmbHome)
		textCarNumber = new TextField("Car Number:");
		textCarNumber.setPropertyDataSource(item.getItemProperty("carNumber"))
		textCarNumber.setBuffered(true)
		textCarNumber.setImmediate(false)
		layout.addComponent(textCarNumber)
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
		cmbHome = new ComboBox("Home:")
		def beanHome = new BeanItemContainer<Home>(Home.class)
		def homeList = Grails.get(HomeService).getListDeleted()
		beanHome.addAll(homeList)
		cmbHome.setContainerDataSource(beanHome)
		cmbHome.setItemCaptionPropertyId("name")
		layout.addComponent(cmbHome)
		textCarNumber = new TextField("Car Number:")
		layout.addComponent(textCarNumber)
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
	//	========================
	//	WINDOW EDIT DETAIL
//		========================
//	@RequiresPermissions("Transaction:DeliveryOrder:Edit")
//	private void windowEditDetail(item,itemDetail,String caption) {
//				if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
//		window = new Window(caption)
//		window.setModal(true)
//		def layout3 = new FormLayout()
//		layout3.setMargin(true)
//		window.setContent(layout3)
//		textId = new TextField("Master Id:")
//		textId.setPropertyDataSource(item.getItemProperty("id"))
//		textId.setReadOnly(true)
//		layout3.addComponent(textId)
//		textIdDetail = new TextField("Detail Id:")
//		textIdDetail.setPropertyDataSource(itemDetail.getItemProperty("id"))
//		textIdDetail.setReadOnly(true)
//		layout3.addComponent(textIdDetail)
//		textCodeDetail = new TextField("Code:");
//		textCodeDetail.setPropertyDataSource(itemDetail.getItemProperty("code"))
//		textCodeDetail.setBuffered(true)
//		textCodeDetail.setImmediate(false)
//		textCodeDetail.setReadOnly(true)
//		layout3.addComponent(textCodeDetail)
//		textDescriptionDetail = new TextArea("Description:");
//		textDescriptionDetail.setPropertyDataSource(itemDetail.getItemProperty("description"))
//		textDescriptionDetail.setBuffered(true)
//		textDescriptionDetail.setImmediate(false)
//		layout3.addComponent(textDescriptionDetail)
//		//		comb = new ComboBox("Item:")
//
//		//			tableSearchContainer = new BeanItemContainer<SalesOrderDetail>(SalesOrderDetail.class);
//		//			itemlist = Grails.get(SalesOrderDetailService).getListForCombo(item.getItemProperty("salesOrder.id").toString())
//		//			tableSearchContainer.addAll(itemlist)
//		//			tableSearchContainer.addNestedContainerProperty("item.sku");
//		//			comb.setContainerDataSource(tableSearchContainer)
//
//		//		comb.setItemCaptionPropertyId("item.sku")
//		//		layout3.addComponent(comb)
//		//		comb.select(comb.getItemIds().find{ it.id == itemDetail.getItemProperty("salesOrderDetail.id").value})
//		//
//		//		layout3.addComponent(comb)
//		textAmountDetail = new TextField("Amount:")
//		//		textAmountDetail.setPropertyDataSource(itemDetail.getItemProperty("amount"))
//		textAmountDetail.setValue(itemDetail.getItemProperty("amount").toString())
//		textAmountDetail.setBuffered(true)
//		layout3.addComponent(textAmountDetail)
//		def horizontal3 = new HorizontalLayout()
//		layout3.addComponent(horizontal3)
//		horizontal3.addComponent(createSaveDetailButton())
//		horizontal3.addComponent(createCancelButton())
//
//		getUI().addWindow(window);
//				} else {
//					Notification.show("Access Denied\n",
//							"Anda tidak memiliki izin untuk Mengubah Record",
//							Notification.Type.ERROR_MESSAGE);
//				}
//	}

	void updateTable() {
		//		if (table.size() > MAX_PAGE_LENGTH) {
		//		table.setPageLength(MAX_PAGE_LENGTH);
		//		} else {
		//		table.setPageLength(table.size());
		//		}
		//		table.markAsDirtyRecursive();
	}

	void initTable() {
		tableContainer = new BeanItemContainer<Home>(Home.class);
		//fillTableContainer(tableContainer);
		itemlist = Grails.get(HomeService).getList()
		tableContainer.addAll(itemlist)
		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("home.name","Home Name")
		table.visibleColumns = ["id","name","address","dateCreated","lastUpdated","isDeleted"]
		table.setSelectable(true)
		table.setWidth("300")
		table.setImmediate(false)
//				table.setPageLength(table.size())
//		table.setSizeFull()

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
		tableDetailContainer = new BeanItemContainer<ParkingRegistration>(ParkingRegistration.class);
		def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
		def itemListDetail = Grails.get(ParkingRegistrationService).getListForParkingRegistration(ind)
		tableDetailContainer.addNestedContainerProperty("createdBy.id")
		tableDetailContainer.addNestedContainerProperty("createdBy.username")
		tableDetailContainer.addNestedContainerProperty("updatedBy.id")
		tableDetailContainer.addNestedContainerProperty("updatedBy.username")
		tableDetailContainer.addNestedContainerProperty("confirmedBy.id")
		tableDetailContainer.addNestedContainerProperty("confirmedBy.username")
		tableDetailContainer.addNestedContainerProperty("home.id")
		tableDetailContainer.addNestedContainerProperty("home.name")
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.id");
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.sku");
		//		tableDetailContainer.addNestedContainerProperty("deliveryOrder.id");
		tableDetailContainer.addAll(itemListDetail)
		tableDetail.setColumnHeader("home.name","Home Name")
		tableDetail.setContainerDataSource(tableDetailContainer);
		tableDetail.visibleColumns = ["id","carNumber","isConfirmed","confirmationDate","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username","confirmedBy.username"]
		tableDetail.setSelectable(true)
		tableDetail.setImmediate(false)
		tableDetail.setVisible(true)
		tableDetail.setSizeFull()
//		menuBarDetail.setVisible(true)
	}


//	private void windowPrint(String caption){
//		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
//				new ConfirmDialog.Listener() {
//					public void onClose(ConfirmDialog dialog) {
//						if (dialog.isConfirmed()) {
//							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
//							object = Grails.get(InvoiceService).printObject(object)
//							if (object.hasErrors())
//							{
//								Object[] tv = [textId]
//								generalFunction.setErrorUI(tv,object)
//							}
//							else
//							{
//								final Map map = new HashMap();
//								StreamResource.StreamSource source = new StreamResource.StreamSource() {
//											public InputStream getStream() {
//												byte[] b = null;
//												try {
//													DataBeanMaker dataBeanMaker = new DataBeanMaker()
//													object = Grails.get(InvoiceDetailService).getList(object.id)
//													ArrayList dataBeanList = dataBeanMaker.getDataBeanList(object);
//													JRBeanCollectionDataSource beanColDataSource = new
//															JRBeanCollectionDataSource(dataBeanList);
//													Map parameters = new HashMap();
//													b = JasperRunManager.runReportToPdf(getClass().
//															getClassLoader().getResourceAsStream("reports/Invoice.jasper"),
//															map,beanColDataSource);
//												} catch (JRException ex) {
//													ex.printStackTrace();
//												}
//
//												return new ByteArrayInputStream(b);
//											}
//										};
//								Date curDate = new Date()
//								SimpleDateFormat format = new SimpleDateFormat("yyMMddhhMMss");
//								String now = format.format(curDate)
//								StreamResource resource = new StreamResource(source, "Invoice${now}.pdf");
//								resource.setMIMEType("application/pdf");
//								BrowserFrame browser = new BrowserFrame("Browser");
//								browser.setWidth("600px");
//								browser.setHeight("400px");
//								VerticalLayout v = new VerticalLayout();
//								Embedded e = new Embedded("", resource);
//								e.setSizeFull();
//								e.setHeight("600px")
//								e.setType(Embedded.TYPE_BROWSER)
//								v.addComponent(e);
//								Window w = new Window()
//								w.setContent(v);
//								w.setWindowMode(WindowMode.MAXIMIZED)
//								UI.getCurrent().addWindow(w);
//
//							}
//						} else {
//
//						}
//					}
//				})
//
//	}
//
//
//	private class DataBeanMaker {
//		public ArrayList getDataBeanList(def object) {
//			ArrayList<InvoiceReportModel> dataBeanList = new ArrayList<InvoiceReportModel>();
//			for(data in object)
//			{
//				dataBeanList.add(produce(data.invoice.code,data.invoice.invoiceDate,
//						data.invoice.description, data.invoice.dueDate, data.id.toInteger(),
//						data.description,
//						data.amount, data.invoice.totalAmount, data.invoice.home.name, data.invoice.home.address));
//			}
//			return dataBeanList
//		}
//
//		private InvoiceReportModel produce(
//				String code,
//				Date invoiceDate,
//				String description,
//				Date dueDate,
//				Integer idDetail,
//				String descriptionDetail,
//				Double amount,
//				Double totalAmount,
//				String name,
//				String address
//		) {
//
//			InvoiceReportModel dataBean = new InvoiceReportModel();
//
//			dataBean.setCode(code);
//			dataBean.setInvoiceDate(invoiceDate)
//			dataBean.setDescription(description)
//			dataBean.setDueDate(dueDate)
//			dataBean.setIdDetail(idDetail)
//			dataBean.setDescriptionDetail(descriptionDetail)
//			dataBean.setAmount(amount)
//			dataBean.setTotalAmount(totalAmount)
//			dataBean.setName(name)
//			dataBean.setAddress(address)
//
//			return dataBean;
//		}
//	}



}

