package estate_management

import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat
import java.util.ArrayList;
import java.util.Date;

import estate_management.reportModel.CashBankReportModel
import estate_management.reportModel.ClaimReportModel
import estate_management.reportModel.InvoiceReportModel;
import estate_management.widget.GeneralFunction
import estate_management.widget.Constant

import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils

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
import com.vaadin.ui.CheckBox
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

import estate_management.CashBankService









import com.vaadin.grails.Grails

class MasterClaim extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	//	private TextField textSKU
	//	private TextField textDescription

	//==============================
	private ComboBox cmbPermit
	private TextArea textDescription
	private TextField textAmount
	private DateField textClaimDate
	private TextField textCode
	//==============================

	private Table table = new Table();
	private BeanItemContainer<Claim> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Maintenance:Claim:"
	//						Constant.MenuName.Item + ":";
	private Subject currentUser
	public MasterClaim() {
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
								def item = new BeanItem<Claim>(tableContainer);
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
								permit:cmbPermit.getValue(),
								description:textDescription.getValue(),
								amount:textAmount.getValue(),
								claimDate:textClaimDate.getValue(),
								username:getSession().getAttribute("user"),
								code:textCode.getValue()
							]

							if (object.id == "")
							{
								object =  Grails.get(ClaimService).createObject(object)
							}
							else
							{
								object =  Grails.get(ClaimService).updateObject(object)
							}


							if (object.errors.hasErrors())
							{
								cmbPermit.setData("permit")
								textDescription.setData("description")
								textAmount.setData("amount")
								textClaimDate.setData("claimDate")
								textCode.setData("code")
								Object[] tv = [cmbPermit,textDescription,textAmount,textClaimDate,textCode]
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
							object = Grails.get(ClaimService).softDeletedObject(object)
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
							object = Grails.get(ClaimService).confirmObject(object)
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
							object = Grails.get(ClaimService).unConfirmObject(object)
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
		textCode.setReadOnly(true)
		layout.addComponent(textCode)
		cmbPermit = new ComboBox("Permit:");
		def beanPermit = new BeanItemContainer<Permit>(Permit.class)
		def permitList = Grails.get(PermitService).getListDeleted()
		beanPermit.addAll(permitList)
		cmbPermit.setContainerDataSource(beanPermit)
		cmbPermit.setItemCaptionPropertyId("constructionType")
		cmbPermit.select(cmbPermit.getItemIds().find{ it.id == item.getItemProperty("permit.id").value})
		cmbPermit.setBuffered(true)
		cmbPermit.setImmediate(false)
		layout.addComponent(cmbPermit)
		textDescription = new TextArea("Description:");
		textDescription.setPropertyDataSource(item.getItemProperty("description"))
		textDescription.setBuffered(true)
		textDescription.setImmediate(false)
		layout.addComponent(textDescription)
		textAmount = new TextField("Amount:");
		textAmount.setValue(item.getItemProperty("amount").toString())
		textAmount.setBuffered(true)
		textAmount.setImmediate(false)
		layout.addComponent(textAmount)
		textClaimDate = new DateField("ClaimDate");
		textClaimDate.setPropertyDataSource(item.getItemProperty("claimDate"))
		textClaimDate.setBuffered(true)
		textClaimDate.setImmediate(false)
		layout.addComponent(textClaimDate)
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
		textCode = new TextField("Code:");
		textCode.setReadOnly(true)
		layout.addComponent(textCode)
		cmbPermit = new ComboBox("Permit:")
		def beanPermit = new BeanItemContainer<Permit>(Permit.class)
		def permitList = Grails.get(PermitService).getListDeleted()
		beanPermit.addAll(permitList)
		cmbPermit.setContainerDataSource(beanPermit)
		cmbPermit.setItemCaptionPropertyId("constructionType")
		layout.addComponent(cmbPermit)
		textDescription = new TextArea("Description:")
		layout.addComponent(textDescription)
		textAmount = new TextField("Amount:")
		layout.addComponent(textAmount)
		textClaimDate = new DateField("Claim Date :")
		layout.addComponent(textClaimDate)
		def horizontal = new HorizontalLayout()
		layout.addComponent(horizontal)
		horizontal.addComponent(createSaveButton())
		horizontal.addComponent(createCancelButton())
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
		tableContainer = new BeanItemContainer<Claim>(Claim.class);
		//fillTableContainer(tableContainer);
		itemlist = Grails.get(ClaimService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("permit.id")
		tableContainer.addNestedContainerProperty("permit.constructionType")
		tableContainer.addNestedContainerProperty("createdBy.id")
		tableContainer.addNestedContainerProperty("createdBy.username")
		tableContainer.addNestedContainerProperty("updatedBy.id")
		tableContainer.addNestedContainerProperty("updatedBy.username")
		tableContainer.addNestedContainerProperty("confirmedBy.id")
		tableContainer.addNestedContainerProperty("confirmedBy.username")
		table.setContainerDataSource(tableContainer);
//		table.setColumnHeader("createdBy.username","CreatedBy")
//		table.setColumnHeader("updatedBy.username","UpdatedBy")
		table.visibleColumns = ["id","code","permit.constructionType","description","amount","claimDate","isConfirmed","confirmationDate","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username","confirmedBy.username"]
		table.setSelectable(true)
		table.setImmediate(false)
		//		table.setPageLength(table.size())
		table.setSizeFull()


		//		table.addValueChangeListener(new Property.ValueChangeListener() {
		//			public void valueChange(ValueChangeEvent event) {
		//				selectedRow = table.getValue()
		//			}
		//		});

	}

	private void windowPrint(String caption){
		ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
				new ConfirmDialog.Listener() {
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
							object = Grails.get(ClaimService).printObject(object)
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
													DataBeanMaker dataBeanMaker = new DataBeanMaker()
													object = Grails.get(ClaimService).getList()
													ArrayList dataBeanList = dataBeanMaker.getDataBeanList(object);
													JRBeanCollectionDataSource beanColDataSource = new
															JRBeanCollectionDataSource(dataBeanList);
													Map parameters = new HashMap();
													b = JasperRunManager.runReportToPdf(getClass().
															getClassLoader().getResourceAsStream("reports/Claim.jasper"),
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
								StreamResource resource = new StreamResource(source, "Claim${now}.pdf");
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
			ArrayList<ClaimReportModel> dataBeanList = new ArrayList<ClaimReportModel>();
			for(data in object)
			{
				dataBeanList.add(produce(data.code,data.permit.code,
						data.permit.constructionType, data.permit.vendor.name, data.permit.home.name,
						data.permit.home.address,
						data.amount, data.description, data.claimDate));
			}
			return dataBeanList
		}

		private ClaimReportModel produce(
				String code,
				String permitCode,
				String constructionType,
				String vendorName,
				String name,
				String address,
				Double amount,
				String description,
				Date claimDate
		) {

			ClaimReportModel dataBean = new ClaimReportModel();

			dataBean.setCode(code);
			dataBean.setPermitCode(permitCode)
			dataBean.setConstructionType(constructionType)
			dataBean.setVendorName(vendorName)
			dataBean.setName(name)
			dataBean.setAddress(address)
			dataBean.setAmount(amount)
			dataBean.setDescription(description)
			dataBean.setClaimDate(claimDate)

			return dataBean;
		}
	}

}

