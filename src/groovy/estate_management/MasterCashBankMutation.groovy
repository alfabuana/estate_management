package estate_management

import java.awt.event.ItemEvent;
import estate_management.widget.GeneralFunction
import estate_management.widget.Constant
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils



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
import com.vaadin.ui.CheckBox
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
import estate_management.CashBankMutationService
import grails.converters.JSON





import com.vaadin.grails.Grails

class MasterCashBankMutation extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	//	private TextField textSKU
	//	private TextField textDescription

	//==============================
	private ComboBox cmbSourceCashBank
	private ComboBox cmbTargetCashBank
	private TextField textAmount
	private TextField textCode
	//==============================

	private Table table = new Table();
	private BeanItemContainer<CashBankMutation> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Finance:CashBankMutation:"
	//						Constant.MenuName.Item + ":";

	private Subject currentUser
	public MasterCashBankMutation() {
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
								def item = new BeanItem<CashBankMutation>(tableContainer);
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
								sourceCashBank:cmbSourceCashBank.getValue(),
								targetCashBank:cmbTargetCashBank.getValue(),
								amount:textAmount.getValue(),
								code:textCode.getValue(),
								username:getSession().getAttribute("user")
							]

							if (object.id == "")
							{
								object =  Grails.get(CashBankMutationService).createObject(object)
							}
							else
							{
								object =  Grails.get(CashBankMutationService).updateObject(object)
							}

							if (object.errors.hasErrors())
							{
								cmbSourceCashBank.setData("sourceCashBank")
								cmbTargetCashBank.setData("targetCashBank")
								textAmount.setData("amount")
								textCode.setData("code")
								Object[] tv = [cmbSourceCashBank,cmbTargetCashBank,textAmount,textCode]
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
							object  = Grails.get(CashBankMutationService).softDeletedObject(object)
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
							object = Grails.get(CashBankMutationService).confirmObject(object)
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
						"Anda tidak memiliki izin untuk Konfirmasi Record",
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
							object = Grails.get(CashBankMutationService).unConfirmObject(object)
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
		cmbSourceCashBank = new ComboBox("Source Cash Bank:");
		def beanCashBank = new BeanItemContainer<CashBank>(CashBank.class)
		def cashBankList = Grails.get(CashBankService).getListDeleted()
		beanCashBank.addAll(cashBankList)
		cmbSourceCashBank.setContainerDataSource(beanCashBank)
		cmbSourceCashBank.setItemCaptionPropertyId("name")
		cmbSourceCashBank.select(cmbSourceCashBank.getItemIds().find{ it.id == item.getItemProperty("sourceCashBank.id").value})
		cmbSourceCashBank.setBuffered(true)
		cmbSourceCashBank.setImmediate(false)
		layout.addComponent(cmbSourceCashBank)
		cmbTargetCashBank = new ComboBox("Target Cash Bank:");
//		def beanCashBank = new BeanItemContainer<CashBank>(CashBank.class)
//		def cashBankList = Grails.get(CashBankService).getList()
		beanCashBank.addAll(cashBankList)
		cmbTargetCashBank.setContainerDataSource(beanCashBank)
		cmbTargetCashBank.setItemCaptionPropertyId("name")
		cmbTargetCashBank.select(cmbTargetCashBank.getItemIds().find{ it.id == item.getItemProperty("targetCashBank.id").value})
		cmbTargetCashBank.setBuffered(true)
		cmbTargetCashBank.setImmediate(false)
		layout.addComponent(cmbTargetCashBank)
		textAmount = new TextField("Amount:");
		//			textAmount.setPropertyDataSource(item.getItemProperty("amount"))
		textAmount.setValue(item.getItemProperty("amount").toString())
		textAmount.setBuffered(true)
		textAmount.setImmediate(false)
		layout.addComponent(textAmount)
		
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
		
		cmbSourceCashBank = new ComboBox("Source Cash Bank:")
		def beanCashBank = new BeanItemContainer<CashBank>(CashBank.class)
		def cashBankList = Grails.get(CashBankService).getListDeleted()
		beanCashBank.addAll(cashBankList)
		cmbSourceCashBank.setContainerDataSource(beanCashBank)
		cmbSourceCashBank.setItemCaptionPropertyId("name")
		layout.addComponent(cmbSourceCashBank)
		cmbTargetCashBank = new ComboBox("Target Cash Bank:")
//		def beanCashBank = new BeanItemContainer<CashBank>(CashBank.class)
//		def cashBankList = Grails.get(CashBankService).getList()
		beanCashBank.addAll(cashBankList)
		cmbTargetCashBank.setContainerDataSource(beanCashBank)
		cmbTargetCashBank.setItemCaptionPropertyId("name")
		layout.addComponent(cmbTargetCashBank)
		textAmount = new TextField("Amount:")
		layout.addComponent(textAmount)
		
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

	void updateTable() {
		//		if (table.size() > MAX_PAGE_LENGTH) {
		//		table.setPageLength(MAX_PAGE_LENGTH);
		//		} else {
		//		table.setPageLength(table.size());
		//		}
		//		table.markAsDirtyRecursive();
	}

	void initTable() {
		tableContainer = new BeanItemContainer<CashBankMutation>(CashBankMutation.class);
		//fillTableContainer(tableContainer);
		itemlist = Grails.get(CashBankMutationService).getList()
		tableContainer.addAll(itemlist)
//		tableContainer.addNestedContainerProperty("cashBank.id")
		
		tableContainer.addNestedContainerProperty("createdBy.username")
		tableContainer.addNestedContainerProperty("updatedBy.username")
		tableContainer.addNestedContainerProperty("confirmedBy.username")
		tableContainer.addNestedContainerProperty("sourceCashBank.name")
		tableContainer.addNestedContainerProperty("sourceCashBank.id")
		tableContainer.addNestedContainerProperty("targetCashBank.name")
		tableContainer.addNestedContainerProperty("targetCashBank.id")
		
		//		tableContainer.addNestedContainerProperty("customer1.id")

		table.setContainerDataSource(tableContainer);
//		table.setColumnHeader("cashBank.name","Cash Bank Name")
//		table.setColumnHeader("adjustmentDate","Adjustment Date")
		table.visibleColumns = ["id","sourceCashBank.name","targetCashBank.name","amount","code","isConfirmed","confirmationDate","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username","confirmedBy.username"]
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



}

