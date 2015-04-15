package estate_management

import java.awt.event.ItemEvent;

import estate_management.reportModel.CashBankReportModel
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

class MasterCashBank extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	//	private TextField textSKU
	//	private TextField textDescription

	//==============================
	private TextField textName
	private TextArea textDescription
	private TextField textAmount
	private CheckBox chkBank
	//==============================

	private Table table = new Table();
	private BeanItemContainer<CashBank> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Cash Bank"
	//						Constant.MenuName.Item + ":";
	private Subject currentUser
	public MasterCashBank() {
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
								def item = new BeanItem<CashBank>(tableContainer);
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
							case "Print":
								if (table.getValue() != null)
									windowPrint();
								break;
						}
					}
				};
		//	END EVENT CLICK

		// UNTUK BUTTON MENU
		MenuItem saveMenu =  menuBar.addItem("Add",mycommand)
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
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
								name:textName.getValue(),
								description:textDescription.getValue(),
								amount:textAmount.getValue(),
								isBank:chkBank.getValue(),
								username:getSession().getAttribute("user")
							]

							if (object.id == "")
							{
								object =  Grails.get(CashBankService).createObject(object)
							}
							else
							{
								object =  Grails.get(CashBankService).updateObject(object)
							}


							if (object.errors.hasErrors())
							{
								textName.setData("name")
								textDescription.setData("description")
								textAmount.setData("amount")
								chkBank.setData("isBank")
								Object[] tv = [textName,textDescription,chkBank]
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
							object = Grails.get(CashBankService).softDeletedObject(object)
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
		textDescription = new TextArea("Description:");
		textDescription.setPropertyDataSource(item.getItemProperty("description"))
		textDescription.setBuffered(true)
		textDescription.setImmediate(false)
		layout.addComponent(textDescription)
		textAmount = new TextField("Amount:");
		//			textAmount.setPropertyDataSource(item.getItemProperty("amount"))
		textAmount.setValue(item.getItemProperty("amount").toString())
		textAmount.setBuffered(true)
		textAmount.setImmediate(false)
		textAmount.setReadOnly(true)
		layout.addComponent(textAmount)
		chkBank = new CheckBox("isBank");
		chkBank.setPropertyDataSource(item.getItemProperty("isBank"))
		chkBank.setBuffered(true)
		chkBank.setImmediate(false)
		layout.addComponent(chkBank)
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
		textDescription = new TextArea("Description:")
		layout.addComponent(textDescription)
		textAmount = new TextField("Amount:")
		textAmount.setReadOnly(true)
		layout.addComponent(textAmount)
		chkBank = new CheckBox("isBank")
		layout.addComponent(chkBank)
		//			def textArea = new TextArea("Text Area")
		//			layout.addComponent(textArea)
		//			def dateField = new DateField("Date Field")
		//			layout.addComponent(dateField)
		//			def comboBox = new ComboBox("combo Box")
		//			comboBox.addItem("test")
		//			comboBox.addItem("test2")
		//			layout.addComponent(comboBox)
		//
		def horizontal = new HorizontalLayout()
		layout.addComponent(horizontal)
		//			===================
		//TOMBOL SAVE
		//			===================
		horizontal.addComponent(createSaveButton())
		//			==================

		//			===================
		//			TOMBOL CANCEL
		//			===================
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
		tableContainer = new BeanItemContainer<CashBank>(CashBank.class);
		//fillTableContainer(tableContainer);
		itemlist = Grails.get(CashBankService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("createdBy.username")
		tableContainer.addNestedContainerProperty("updatedBy.username")
		//		tableContainer.addNestedContainerProperty("customer1.id")

		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("createdBy.username","CreatedBy")
		table.setColumnHeader("updatedBy.username","UpdatedBy")
		table.visibleColumns = ["id","name","description","amount","isBank","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username"]
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

	private void windowPrint(){
		final Map map = new HashMap();

		StreamResource.StreamSource source = new StreamResource.StreamSource() {

					public InputStream getStream() {
						byte[] b = null;
						try {
							DataBeanMaker dataBeanMaker = new DataBeanMaker();
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
							ArrayList dataBeanList = dataBeanMaker.getDataBeanList(object.id);
							JRBeanCollectionDataSource beanColDataSource = new
									JRBeanCollectionDataSource(dataBeanList);
							Map parameters = new HashMap();

							b = JasperRunManager.runReportToPdf(getClass().
									getClassLoader().getResourceAsStream("reports/testing.jasper"),
									map,beanColDataSource);
						} catch (JRException ex) {
							ex.printStackTrace();
						}

						return new ByteArrayInputStream(b);
					}
				};

		StreamResource resource = new StreamResource(source, "testing.pdf");
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


	private class DataBeanMaker {
		public ArrayList getDataBeanList(def Object) {
			ArrayList<CashBankReportModel> dataBeanList = new ArrayList<CashBankReportModel>();
//			CashBank data = Grails.get(CashBankService).getList()
			for(data in Grails.get(CashBankService).getList())
			{
				dataBeanList.add(produce(data.id.toInteger(),data.name,
					data.description,data.amount,data.isBank));
			
			}
			return dataBeanList;
		}

		private CashBankReportModel produce(
				Integer id,
				String name,
				String description,
				Double amount,
				Boolean isBank
		) {

			CashBankReportModel dataBean = new CashBankReportModel();

			dataBean.setId(id);
			dataBean.setName(name)
			dataBean.setDescription(description)
			dataBean.setAmount(amount)
			dataBean.setIsBank(isBank)

			return dataBean;
		}
	}

}

