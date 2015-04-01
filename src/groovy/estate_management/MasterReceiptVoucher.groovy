package estate_management

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
import estate_management.ReceiptVoucherService





import com.vaadin.grails.Grails

class MasterReceiptVoucher extends VerticalLayout{
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
	private ComboBox cmbUser
	private ComboBox cmbCashBank
	private TextField textCode
	private DateField textReceiptDate
	private CheckBox chkIsGBCH
//	private DateField textDueDate
	private TextField textTotalAmount
	
	private TextField textIdDetail
	private TextField textCodeDetail
	private TextField textAmountDetail
	private TextField textDescriptionDetail
	
	//==============================
	
	private Table table = new Table();
	private Table tableDetail = new Table()
	private BeanItemContainer<ReceiptVoucher> tableContainer;
	private BeanItemContainer tableDetailContainer
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Receipt Request"
//						Constant.MenuName.Item + ":";
	
	public MasterReceiptVoucher() {
//		currentUser = SecurityUtils.getSubject();
		
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
						def item = new BeanItem<ReceiptVoucher>(tableContainer);
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
		MenuItem uncofirmMenu = menuBar.addItem("Unconfirm", mycommand)
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
								  username:cmbUser.getValue(),
								  cashBank:cmbCashBank.getValue(),
								  code:textCode.getValue(),
								  receiptDate:textReceiptDate.getValue(),
								  isGBCH:chkIsGBCH.getValue(),
//								  dueDate:textDueDate.getValue().toString(),
								  totalAmount:textTotalAmount.getValue()
								  ]
					
					if (object.id == "")
					{
						object =  Grails.get(ReceiptVoucherService).createObject(object)
					}
					else
					{
						object =  Grails.get(ReceiptVoucherService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						cmbUser.setData("username")
						cmbCashBank.setData("cashBank")
						textCode.setData("code")
						textReceiptDate.setData("receiptDate")
						chkIsGBCH.setData("isGBCH")
//						textDueDate.setData("dueDate")
						textTotalAmount.setData("totalAmount")
						Object[] tv = [cmbUser,cmbCashBank,textCode,textReceiptDate,chkIsGBCH,textTotalAmount]
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
						Grails.get(ReceiptVoucherService).softDeletedObject(object)
						initTable()
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
							def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
							Grails.get(ReceiptVoucherService).confirmObject(object)
							initTable()
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
								Grails.get(ReceiptVoucherService).unConfirmObject(object)
								initTable()
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
			cmbUser = new ComboBox("User:");
			def beanUser = new BeanItemContainer<ShiroUser>(ShiroUser.class)
			def userList = Grails.get(UserService).getList()
			beanUser.addAll(userList)
			cmbUser.setContainerDataSource(beanUser)
			cmbUser.setItemCaptionPropertyId("username")
			cmbUser.select(cmbUser.getItemIds().find{ it.id == item.getItemProperty("username.id").value})
			cmbUser.setBuffered(true)
			cmbUser.setImmediate(false)
			layout.addComponent(cmbUser)
			cmbCashBank = new ComboBox("Cash Bank:");
			def beanCashBank = new BeanItemContainer<CashBank>(CashBank.class)
			def cashBankList = Grails.get(CashBankService).getList()
			beanCashBank.addAll(cashBankList)
			cmbCashBank.setContainerDataSource(beanCashBank)
			cmbCashBank.setItemCaptionPropertyId("name")
			cmbCashBank.select(cmbCashBank.getItemIds().find{ it.id == item.getItemProperty("cashBank.id").value})
			cmbCashBank.setBuffered(true)
			cmbCashBank.setImmediate(false)
			layout.addComponent(cmbCashBank)
			textCode = new TextField("Code:");
			textCode.setPropertyDataSource(item.getItemProperty("code"))
			textCode.setBuffered(true)
			textCode.setImmediate(false)
			layout.addComponent(textCode)
			textReceiptDate = new DateField("Receipt Date:");
			textReceiptDate.setPropertyDataSource(item.getItemProperty("receiptDate"))
			textReceiptDate.setBuffered(true)
			textReceiptDate.setImmediate(false)
			layout.addComponent(textReceiptDate)
			chkIsGBCH = new CheckBox("is GBCH");
			chkIsGBCH.setPropertyDataSource(item.getItemProperty("isGBCH"))
			chkIsGBCH.setBuffered(true)
			chkIsGBCH.setImmediate(false)
			layout.addComponent(chkIsGBCH)
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
			window = new Window(caption);
			window.setModal(true);
			def layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Id:");
			textId.setReadOnly(true)
			layout.addComponent(textId)
			cmbUser = new ComboBox("User:")
			def beanUser = new BeanItemContainer<ShiroUser>(ShiroUser.class)
			def userList = Grails.get(UserService).getList()
			beanUser.addAll(userList)
			cmbUser.setContainerDataSource(beanUser)
			cmbUser.setItemCaptionPropertyId("username")
			layout.addComponent(cmbUser)
			cmbCashBank = new ComboBox("Cash Bank:")
			def beanCashBank = new BeanItemContainer<CashBank>(CashBank.class)
			def cashBankList = Grails.get(CashBankService).getList()
			beanCashBank.addAll(cashBankList)
			cmbCashBank.setContainerDataSource(beanCashBank)
			cmbCashBank.setItemCaptionPropertyId("name")
			layout.addComponent(cmbCashBank)
			textCode = new TextField("Code:")
			layout.addComponent(textCode)
			textReceiptDate = new DateField("Receipt Date:")
			layout.addComponent(textReceiptDate)
			chkIsGBCH = new CheckBox("Is GBCH")
			layout.addComponent(chkIsGBCH)
//			textDueDate = new DateField("Due Date:")
//			layout.addComponent(textDueDate)
			textTotalAmount = new TextField("Total Amount:")
			layout.addComponent(textTotalAmount)
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
	
	 void updateTable() {
//		if (table.size() > MAX_PAGE_LENGTH) {
//		table.setPageLength(MAX_PAGE_LENGTH);
//		} else {
//		table.setPageLength(table.size());
//		}
//		table.markAsDirtyRecursive();
		}
	 
	 void initTable() {
		tableContainer = new BeanItemContainer<ReceiptVoucher>(ReceiptVoucher.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(ReceiptVoucherService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("username.id")
		tableContainer.addNestedContainerProperty("username.username")
		tableContainer.addNestedContainerProperty("cashBank.id")
		tableContainer.addNestedContainerProperty("cashBank.name")
		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("username.username","Username")
		table.setColumnHeader("cashBank.name","Cash Bank Name")
		table.setColumnHeader("receiptDate","Receipt Date")
		table.setColumnHeader("totalAmount","Total Amount")
//		table.setColumnHeader("durasi","Duration")
//		table.setColumnHeader("dateStartUsing","Date Start Using")
//		table.setColumnHeader("dateEndUsing","Date End Using")
		table.visibleColumns = ["username.username","cashBank.name","code","receiptDate","isGBCH","dueDate","isReconciled","reconciliationDate","totalAmount","isConfirmed","confirmationDate","dateCreated","lastUpdated","isDeleted"]
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

//		table.addValueChangeListener(new Property.ValueChangeListener() {
//			public void valueChange(ValueChangeEvent event) {
//				selectedRow = table.getValue()
//			}
//		});

	}
	
	 void initTableDetail() {
		 tableDetailContainer = new BeanItemContainer<ReceiptVoucherDetail>(ReceiptVoucherDetail.class);
		 def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
		 def itemListDetail = Grails.get(ReceiptVoucherDetailService).getList(ind)
		 tableDetailContainer.addNestedContainerProperty("receiptVoucher.id")
		 //					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.id");
		 //					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.sku");
		 //		tableDetailContainer.addNestedContainerProperty("deliveryOrder.id");
		 tableDetailContainer.addAll(itemListDetail)
		 tableDetail.setColumnHeader("receiptVoucher.id","Receipt Voucher Id")
		 tableDetail.setContainerDataSource(tableDetailContainer);
		 tableDetail.visibleColumns = ["receiptVoucher.id","receivable","code","amount","description","isConfirmed","confirmationDate","isDeleted","dateCreated","lastUpdated"]
		 tableDetail.setSelectable(true)
		 tableDetail.setImmediate(false)
		 tableDetail.setVisible(true)
		 tableDetail.setSizeFull()
		 menuBarDetail.setVisible(true)
	 }
 
	
}
