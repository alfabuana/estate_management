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
import estate_management.VendorService





import com.vaadin.grails.Grails
import estate_management.widget.Constant
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
class MasterVendor extends VerticalLayout{
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
	private TextField textTelephone
	private TextField textFax
	private TextField textEmail
	
	//==============================
	
	private Table table = new Table();
	private BeanItemContainer<Vendor> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Master:Vendor:"
//						Constant.MenuName.Item + ":";
	private Subject currentUser
	public MasterVendor() {
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
						def item = new BeanItem<Vendor>(tableContainer);
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
								  telephone:textTelephone.getValue(),
								  fax:textFax.getValue(),
								  email:textEmail.getValue(),
								  username:getSession().getAttribute("user")
								  ]
					
					if (object.id == "")
					{
						object =  Grails.get(VendorService).createObject(object)
					}
					else
					{
						object =  Grails.get(VendorService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						textName.setData("name")
						textDescription.setData("description")
						textTelephone.setData("telephone")
						textFax.setData("fax")
						textEmail.setData("email")
						Object[] tv = [textName,textDescription,textTelephone,textFax,textEmail]
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
						object = Grails.get(VendorService).softDeletedObject(object)
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
			textTelephone = new TextField("Telephone:");
			textTelephone.setPropertyDataSource(item.getItemProperty("telephone"))
			textTelephone.setBuffered(true)
			textTelephone.setImmediate(false)
			layout.addComponent(textTelephone)
			textFax = new TextField("Fax:");
			textFax.setPropertyDataSource(item.getItemProperty("fax"))
			textFax.setBuffered(true)
			textFax.setImmediate(false)
			layout.addComponent(textFax)
			textEmail = new TextField("Email:");
			textEmail.setPropertyDataSource(item.getItemProperty("email"))
			textEmail.setBuffered(true)
			textEmail.setImmediate(false)
			layout.addComponent(textEmail)
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
			textTelephone = new TextField("Telephone:")
			layout.addComponent(textTelephone)
			textFax = new TextField("Fax:")
			layout.addComponent(textFax)
			textEmail = new TextField("Email:")
			layout.addComponent(textEmail)
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
		tableContainer = new BeanItemContainer<Vendor>(Vendor.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(VendorService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("createdBy.id")
		tableContainer.addNestedContainerProperty("createdBy.username")
		tableContainer.addNestedContainerProperty("updatedBy.id")
		tableContainer.addNestedContainerProperty("updatedBy.username")
//		tableContainer.addNestedContainerProperty("facility1.id")
//		tableContainer.addNestedContainerProperty("facility1.nama")
//		tableContainer.addNestedContainerProperty("customer1.id")
		
		table.setContainerDataSource(tableContainer);
//		table.setColumnHeader("name","Name")
//		table.setColumnHeader("address","Address")
		table.visibleColumns = ["id","name","description","telephone","fax","email","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username"]
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

