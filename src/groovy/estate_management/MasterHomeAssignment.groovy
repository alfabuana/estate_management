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
import estate_management.HomeAssignmentService
import grails.converters.JSON





import com.vaadin.grails.Grails

class MasterHomeAssignment extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	private TextField textSKU
//	private TextField textDescription
	
	//==============================
	private ComboBox cmbHome
	private ComboBox cmbUser
	private DateField textAssignDate
	
	//==============================
	
	private Table table = new Table();
	private BeanItemContainer<HomeAssignment> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Home Assignment"
//						Constant.MenuName.Item + ":";
	
	public MasterHomeAssignment() {
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
						def item = new BeanItem<HomeAssignment>(tableContainer);
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
								  home:cmbHome.getValue(),
								  user:cmbUser.getValue(),
								  username:String.valueOf(getSession().getAttribute("user")),
								  assignDate:textAssignDate.getValue(),
								  ]
					
					if (object.id == "")
					{
						object =  Grails.get(HomeAssignmentService).createObject(object)
					}
					
					else
					{
						object =  Grails.get(HomeAssignmentService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						cmbHome.setData("home")
						cmbUser.setData("username")
						textAssignDate.setData("assignDate")
						Object[] tv = [cmbHome,cmbUser,textAssignDate]
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
						object = Grails.get(HomeAssignmentService).softDeletedObject(object)
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
							Grails.get(HomeAssignmentService).confirmObject(object)
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
								Grails.get(HomeAssignmentService).unConfirmObject(object)
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
			cmbUser = new ComboBox("User:");
			def beanUser = new BeanItemContainer<ShiroUser>(ShiroUser.class)
			def userList = Grails.get(UserService).getListDeleted()
			beanUser.addAll(userList)
			cmbUser.setContainerDataSource(beanUser)
			cmbUser.setItemCaptionPropertyId("username")
			cmbUser.select(cmbUser.getItemIds().find{ it.id == item.getItemProperty("user.id").value})
			cmbUser.setBuffered(true)
			cmbUser.setImmediate(false)
			layout.addComponent(cmbUser)
			textAssignDate = new DateField("Assign Date:");
			textAssignDate.setPropertyDataSource(item.getItemProperty("assignDate"))
			textAssignDate.setBuffered(true)
			textAssignDate.setImmediate(false)
			layout.addComponent(textAssignDate)
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
			cmbHome = new ComboBox("Home:")
			def beanHome = new BeanItemContainer<Home>(Home.class)
			def homeList = Grails.get(HomeService).getListDeleted()
			beanHome.addAll(homeList)
			cmbHome.setContainerDataSource(beanHome)
			cmbHome.setItemCaptionPropertyId("name")
			layout.addComponent(cmbHome)
			cmbUser = new ComboBox("User:")
			def beanUser = new BeanItemContainer<ShiroUser>(ShiroUser.class)
			def userList = Grails.get(UserService).getListDeleted()
			beanUser.addAll(userList)
			cmbUser.setContainerDataSource(beanUser)
			cmbUser.setItemCaptionPropertyId("username")
			layout.addComponent(cmbUser)
			textAssignDate = new DateField("Assign Date:")
			layout.addComponent(textAssignDate)
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
		tableContainer = new BeanItemContainer<HomeAssignment>(HomeAssignment.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(HomeAssignmentService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("createdBy.id")
		tableContainer.addNestedContainerProperty("updatedBy.id")
		tableContainer.addNestedContainerProperty("confirmedBy.id")
		tableContainer.addNestedContainerProperty("createdBy.username")
		tableContainer.addNestedContainerProperty("updatedBy.username")
		tableContainer.addNestedContainerProperty("confirmedBy.username")
		tableContainer.addNestedContainerProperty("home.id")
		tableContainer.addNestedContainerProperty("home.name")
		tableContainer.addNestedContainerProperty("user.id")
		tableContainer.addNestedContainerProperty("user.username")
		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("home.name","Home Name")
		table.setColumnHeader("user.username","Username")
		table.setColumnHeader("assignDate","Assign Date")
//		table.setColumnHeader("durasi","Duration")
//		table.setColumnHeader("dateStartUsing","Date Start Using")
//		table.setColumnHeader("dateEndUsing","Date End Using")
		table.visibleColumns = ["id","home.name","user.username","assignDate","isConfirmed","confirmationDate","dateCreated","lastUpdated","isDeleted","createdBy.username","updatedBy.username","confirmedBy.username"]
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

