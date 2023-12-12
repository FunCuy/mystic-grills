package view.Admin;

import java.util.ArrayList;
import java.util.stream.Collectors;

import controller.MenuItemController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import model.MenuItems;
import model.Order;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminPanel extends Stage {
	
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    TableView<MenuItems>menuItemTable;
    TableView<User>accountsTable;
    
    public AdminPanel() {
    	super(StageStyle.DECORATED);

        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);

        menuBar = new MenuBar();
        Menu UserManagement = new Menu("User Management");
        MenuItem UserManagementItem= new MenuItem("Show");

        UserManagement.getItems().addAll(UserManagementItem);
        menuBar.getMenus().addAll(UserManagement);
        
        Menu MenuItemManagement = new Menu("Menu Item Management");
        MenuItem MenuItemManagementItem = new MenuItem("Show");

        MenuItemManagement.getItems().addAll(MenuItemManagementItem);
        menuBar.getMenus().addAll(MenuItemManagement);
        
        root.setTop(menuBar);
        
        UserManagementItem.setOnAction(e -> {
        	openNewPageAccount();
        });
        MenuItemManagementItem.setOnAction(e -> {
        	openNewPageMenuItem();
        });

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        VBox contentDivide = new VBox(15);
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll( contentArea);
        root.setCenter(contentDivide);
    }
    
    private void openNewPageMenuItem() {
    	TextField menuItemId = new TextField();
    	TextField menuItemName = new TextField();
    	TextField menuItemDescription = new TextField();
    	TextField menuItemPrice = new TextField();
    	
    	contentArea.getChildren().clear();
    	menuItemTable = createMenuItemTableView();
		contentArea.getChildren().add(menuItemTable);
		
		menuItemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	menuItemId.setText(newSelection.getMenuItemId()+"");
            	menuItemName.setText(newSelection.getMenuItemName());
            	menuItemDescription.setText(String.valueOf(newSelection.getMenuItemDescription()));
            	menuItemPrice.setText(String.valueOf(newSelection.getMenuItemPrice()+""));
            }
        });
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
		Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button addButton = new Button("Add");
        
        form.add(new Label("MenuItem ID:"), 0, 0);
        menuItemId.setDisable(true);
        form.add(menuItemId, 1, 0);
        form.add(new Label("MenuItem Name:"), 0, 1);
        form.add(menuItemName, 1, 1); 
        form.add(new Label("MenuItem Description:"), 0, 2);
        form.add(menuItemDescription, 1, 2);
        form.add(new Label("MenuItem Price:"), 0, 3);
        form.add(menuItemPrice, 1, 3);
        
        form.add(updateButton, 0, 4);
        form.add(deleteButton, 1, 4);
        form.add(addButton, 3, 4);
        
        updateButton.setOnAction(e ->
        {
        	int id= Integer.parseInt(menuItemId.getText());
			String name = menuItemName.getText();
			String description = menuItemDescription.getText();
			double price = Double.parseDouble(menuItemPrice.getText());
            
            
            String result = MenuItemController.updateMenuItem(id, name, description, price);
            
            if ("Success Update A Menu Item".equals(result)) {
                showSuccessDialog("Update success");
                loadMenuItemData();
            } else {
                showErrorDialog(result);
                loadMenuItemData();
            }
        });
        
        deleteButton.setOnAction(e ->
        {
        	int id= Integer.parseInt(menuItemId.getText());
			MenuItemController.deleteMenuItem(id);
			
			showSuccessDialog("Delete Success");
			loadMenuItemData();
        });
        
        addButton.setOnAction(e ->
        {
			String name = menuItemName.getText();
			String description = menuItemDescription.getText();
			double price = Double.parseDouble(menuItemPrice.getText());
			String result = MenuItemController.createMenuItem(name, description, price);
			
			if ("Success Create A New Menu Item".equals(result)) {
                showSuccessDialog("Add success");
                loadMenuItemData();
            } else {
                showErrorDialog(result);
                loadMenuItemData();
            }
        });
        
        contentArea.getChildren().add(form);
		
    }
    
	private void loadUserData() {
		ArrayList<User> users = UserController.getAllUsers();
		accountsTable.getItems().setAll(users);
	}
	
	private void loadMenuItemData() {
		ArrayList<MenuItems> menuItems = MenuItemController.getAllMenuItem();
		menuItemTable.getItems().setAll(menuItems);
	}
	
    
    private void showSuccessDialog(String successMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }
    
    
    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
    
    private void openNewPageAccount() {
    	TextField userId = new TextField();
    	TextField userName = new TextField();
    	ObservableList<String> options = FXCollections.observableArrayList("Admin", "Chef", "Waiter", "Cashier", "Customer");
    	ComboBox<String> userRole= new ComboBox<String>(options);
//    	TextField userRole = new TextField();
    	TextField userEmail = new TextField();
    	TextField userPassword = new TextField();
    	
    	contentArea.getChildren().clear();
    	accountsTable = createAccountsTableView();
		contentArea.getChildren().add(accountsTable); 
		
		accountsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	userId.setText(newSelection.getUserId()+"");
            	userName.setText(newSelection.getUserName());
            	userRole.setValue(String.valueOf(newSelection.getUserRole()));
//            	userRole.setText(String.valueOf(newSelection.getUserRole()));
            	userEmail.setText(String.valueOf(newSelection.getUserEmail()));
            	userPassword.setText(String.valueOf(newSelection.getUserPassword()));
            }
        });
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
		Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        
        
        form.add(new Label("User ID:"), 0, 0);
        userId.setDisable(true);
        form.add(userId, 1, 0, 2, 1);
        form.add(new Label("User Name:"), 0, 1);
        form.add(userName, 1, 1, 2, 1);
        userName.setDisable(true);
        form.add(new Label("User Role:"), 0, 2);
        form.add(userRole, 1, 2, 2, 1);
        form.add(new Label("User Email:"), 0, 3);
        form.add(userEmail, 1, 3, 2, 1);
        userEmail.setDisable(true);
        form.add(new Label("User Password:"), 0, 4);
        form.add(userPassword, 1, 4, 2, 1);
        userPassword.setDisable(true);
        
        form.add(updateButton, 0, 5);
        form.add(deleteButton, 1, 5);
        
        updateButton.setOnAction(e ->
        {
			int id= Integer.parseInt(userId.getText());
			String role = userRole.getValue();
//			String role = userRole.getText();
			String name = userName.getText();
			String email = userEmail.getText();
			String password = userPassword.getText();
            UserController.updateUser(id, role, name, email, password);
            
            showSuccessDialog("Update Success");
            loadUserData();
        });
        
        deleteButton.setOnAction(e ->
        {
			int id= Integer.parseInt(userId.getText());
			UserController.deleteUser(id);
			
			showSuccessDialog("Delete Success");
			loadUserData();
        });
        
        ColumnConstraints col1Constraints = new ColumnConstraints();
        col1Constraints.setPercentWidth(5);
        ColumnConstraints col2Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(5);
        form.getColumnConstraints().addAll(col1Constraints, col2Constraints);

        
        contentArea.getChildren().add(form);
    }
    
    
    private TableView<MenuItems> createMenuItemTableView() {
    	TableView<MenuItems> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<MenuItems, Integer> menuItemIdColumn = new TableColumn<>("ID");
        menuItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        menuItemIdColumn.setPrefWidth(150);
    	    
        TableColumn<MenuItems, String> menuItemNameColumn = new TableColumn<>("Name");
        menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
        menuItemNameColumn.setPrefWidth(150);
        
        TableColumn<MenuItems, String> menuItemDescriptionColumn = new TableColumn<>("Description");
        menuItemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemDescription"));
        menuItemDescriptionColumn.setPrefWidth(150);
        
        TableColumn<MenuItems, Integer> menuItemPriceColumn = new TableColumn<>("Price");
        menuItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemPrice"));
        menuItemPriceColumn.setPrefWidth(150);       
        
        tableView.getColumns().addAll(menuItemIdColumn, menuItemNameColumn, menuItemDescriptionColumn, menuItemPriceColumn);
        tableView.setItems(FXCollections.observableArrayList(MenuItemController.getAllMenuItem()));
        
        return tableView;
    }
    
    private TableView<User> createAccountsTableView() {
    	TableView<User> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	    
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        usernameColumn.setPrefWidth(150);
        
        TableColumn<User, String> userroleColumn = new TableColumn<>("Role");
        userroleColumn.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        userroleColumn.setPrefWidth(150);
        
        TableColumn<User, Integer> userId = new TableColumn<>("ID");
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userId.setPrefWidth(150);
        
        TableColumn<User, String> userEmailColumn = new TableColumn<>("Email");
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        userEmailColumn.setPrefWidth(150);
        
        TableColumn<User, String> userPasswordColumn = new TableColumn<>("Password");
        userPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
        userPasswordColumn.setPrefWidth(150);
            
        
        tableView.getColumns().addAll(usernameColumn, userroleColumn, userId, userEmailColumn, userPasswordColumn);
        tableView.setItems(FXCollections.observableArrayList(UserController.getAllUsers()));
        
        return tableView;
    }
}
