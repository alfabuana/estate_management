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
import com.vaadin.ui.DateField
import com.vaadin.ui.Field
import com.vaadin.ui.FormLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.MenuBar
import com.vaadin.ui.Notification
import com.vaadin.ui.PasswordField
import com.vaadin.ui.Table
import com.vaadin.ui.TextArea
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import com.vaadin.ui.MenuBar.MenuItem
import estate_management.UserService
import grails.converters.JSON





import com.vaadin.grails.Grails

class MasterUser extends VerticalLayout{
	def selectedRow
	def itemlist

	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	private TextField textSKU
	private TextField textDescription

	//==============================
	private TextField textUserName
	private PasswordField textPassword
	private ComboBox cmbRole
	//	private TextField textEmail

	//==============================

	private Table table = new Table();
	private Table tableDetail = new Table()
	private BeanItemContainer<ShiroUser> tableContainer;
	private BeanItemContainer tableDetailContainer
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "Master:User:"
	//						Constant.MenuName.Item + ":";
	private Subject currentUser
	public MasterUser() {
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
								def item = new BeanItem<ShiroUser>(tableContainer);
								windowAdd("Add");
								break
							case "Edit Password":
								if (table.getValue() != null)
									windowEditPassword(tableContainer.getItem(table.getValue()),"Edit Password");
								break;
							case "Edit Roles":
								if (table.getValue() != null)
									windowEditRoles(tableContainer.getItem(table.getValue()),"Edit Roles");
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
		MenuItem updatePasswordMenu = menuBar.addItem("Edit Password", mycommand)
		MenuItem updateRolesMenu = menuBar.addItem("Edit Roles", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")
		//	END BUTTON MENU

		addComponent(table)
		addComponent(tableDetail)
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
								username:textUserName.getValue(),
								passwordHash:textPassword.getValue(),
								roles:cmbRole.getValue()
								//								  email:textEmail.getValue()
							]

							if (object.id == "")
							{
								object =  Grails.get(UserService).createObject(object)
							}
							else
							{
								object =  Grails.get(UserService).updateObject(object)
							}


							if (object.errors.hasErrors())
							{
								textUserName.setData("username")
								textPassword.setData("passwordHash")
								cmbRole.setData("roles")
								//						textEmail.setData("email")
								Object[] tv = [textUserName,textPassword,cmbRole] //textEmail]
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

	private Button createSavePasswordButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {

					void buttonClick(Button.ClickEvent event) {
						try{
							def object = [id:textId.getValue(),
								username:textUserName.getValue(),
								passwordHash:textPassword.getValue(),
							]

							if (object.id == "")
							{
								object =  Grails.get(UserService).createObject(object)
							}
							else
							{
								object =  Grails.get(UserService).updatePassword(object)
							}


							if (object.errors.hasErrors())
							{
								textUserName.setData("username")
								textPassword.setData("passwordHash")
								Object[] tv = [textUserName,textPassword] //textEmail]
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


	private Button createSaveRolesButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {

					void buttonClick(Button.ClickEvent event) {
						try{
							def object = [id:textId.getValue(),
								username:textUserName.getValue(),
								roles:cmbRole.getValue()
							]

							if (object.id == "")
							{
								object =  Grails.get(UserService).createObject(object)
							}
							else
							{
								object =  Grails.get(UserService).updateRoles(object)
							}


							if (object.errors.hasErrors())
							{
								textUserName.setData("username")
								cmbRole.setData("roles")
								Object[] tv = [textUserName,cmbRole] //textEmail]
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
								object = Grails.get(UserService).softDeleteObject(object)
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
	//WINDOW EDIT PASSWORD
	//	========================================
	//@RequiresPermissions("Master:Item:Edit")
	private void windowEditPassword(def item,String caption) {
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
			textUserName = new TextField("Username:");
			textUserName.setPropertyDataSource(item.getItemProperty("username"))
			textUserName.setBuffered(true)
			textUserName.setImmediate(false)
			textUserName.setReadOnly(true)
			layout.addComponent(textUserName)
			textPassword = new PasswordField("Password:");
			//		textPassword.setPropertyDataSource(item.getItemProperty("passwordHash"))
			textPassword.setBuffered(true)
			textPassword.setImmediate(false)
			layout.addComponent(textPassword)
			//		cmbRole = new ComboBox("Role:");
			//		def beanRole = new BeanItemContainer<ShiroRole>(ShiroRole.class)
			//		def roleList = Grails.get(RoleService).getListDeleted()
			//		beanRole.addAll(roleList)
			//		cmbRole.setContainerDataSource(beanRole)
			//		cmbRole.setItemCaptionPropertyId("name")
			//		cmbRole.select(cmbRole.getItemIds().find{ it.id == item.getItemProperty("roles.id").value})
			//		cmbRole.setBuffered(true)
			//		cmbRole.setImmediate(false)
			//		layout.addComponent(cmbRole)
			def horizontal = new HorizontalLayout()
			layout.addComponent(horizontal)
			horizontal.addComponent(createSavePasswordButton())
			horizontal.addComponent(createCancelButton())
			getUI().addWindow(window);
		} else {
			Notification.show("Access Denied\n",
					"Anda tidak memiliki izin untuk Mengubah Record",
					Notification.Type.ERROR_MESSAGE);
		}
	}

	//	========================================
	//WINDOW EDIT ROLES
	//	========================================
	//@RequiresPermissions("Master:Item:Edit")
	private void windowEditRoles(def item,String caption) {
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
			textUserName = new TextField("Username:");
			textUserName.setPropertyDataSource(item.getItemProperty("username"))
			textUserName.setBuffered(true)
			textUserName.setImmediate(false)
			textUserName.setReadOnly(true)
			layout.addComponent(textUserName)
			//		textPassword = new PasswordField("Password:");
			////		textPassword.setPropertyDataSource(item.getItemProperty("passwordHash"))
			//		textPassword.setBuffered(true)
			//		textPassword.setImmediate(false)
			//		layout.addComponent(textPassword)
			cmbRole = new ComboBox("Role:");
			def beanRole = new BeanItemContainer<ShiroRole>(ShiroRole.class)
			def roleList = Grails.get(RoleService).getListDeleted()
			beanRole.addAll(roleList)
			cmbRole.setContainerDataSource(beanRole)
			cmbRole.setItemCaptionPropertyId("name")
			cmbRole.select(cmbRole.getItemIds().find{ it.id == item.getItemProperty("roles.id").value})
			cmbRole.setBuffered(true)
			cmbRole.setImmediate(false)
			layout.addComponent(cmbRole)
			def horizontal = new HorizontalLayout()
			layout.addComponent(horizontal)
			horizontal.addComponent(createSaveRolesButton())
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
			textUserName = new TextField("Username:")
			layout.addComponent(textUserName)
			textPassword = new PasswordField("PasswordHash:")
			layout.addComponent(textPassword)
			cmbRole = new ComboBox("Role:")
			def beanRole = new BeanItemContainer<ShiroRole>(ShiroRole.class)
			def roleList = Grails.get(RoleService).getListDeleted()
			beanRole.addAll(roleList)
			cmbRole.setContainerDataSource(beanRole)
			cmbRole.setItemCaptionPropertyId("name")
			layout.addComponent(cmbRole)

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
		tableContainer = new BeanItemContainer<ShiroUser>(ShiroUser.class);
		//fillTableContainer(tableContainer);
		itemlist = Grails.get(UserService).getList()
		tableContainer.addAll(itemlist)
		tableContainer.addNestedContainerProperty("roles.id")
		tableContainer.addNestedContainerProperty("roles.name")
		//		tableContainer.addNestedContainerProperty("customer1.id")

		table.setContainerDataSource(tableContainer);
		table.setColumnHeader("username","User Name")
		table.setColumnHeader("passwordHash","Password")
		table.visibleColumns = ["id","username","passwordHash","roles.name","dateCreated","lastUpdated","isDeleted"]
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
							//					menuBarDetail.setVisible(false)
						}
					}
				})

	}
	void initTableDetail() {
		tableDetailContainer = new BeanItemContainer<HomeDetail>(HomeDetail.class);
		def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
		def itemListDetail = Grails.get(HomeDetailService).getListForMasterUser(ind)
		tableDetailContainer.addNestedContainerProperty("home.name")
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.id");
		//					tableDetailContainer.addNestedContainerProperty("salesOrderDetail.item.sku");
		//		tableDetailContainer.addNestedContainerProperty("deliveryOrder.id");
		tableDetailContainer.addAll(itemListDetail)
		// tableDetail.setColumnHeader("invoice.id","Invoice Id")
		tableDetail.setContainerDataSource(tableDetailContainer);
		tableDetail.visibleColumns = ["id","home.name","isDeleted","dateCreated","lastUpdated"]
		tableDetail.setSelectable(true)
		tableDetail.setImmediate(false)
		tableDetail.setVisible(true)
		tableDetail.setSizeFull()
		// menuBarDetail.setVisible(true)
	}


	//		table.addValueChangeListener(new Property.ValueChangeListener() {
	//			public void valueChange(ValueChangeEvent event) {
	//				selectedRow = table.getValue()
	//			}
	//		});

}



