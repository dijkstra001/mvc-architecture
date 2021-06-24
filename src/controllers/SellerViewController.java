package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import database.dbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerViewController implements Initializable, DataChangeListener{
	
	private SellerService service;
	
	@FXML
	private TableView<Seller> tableViewSeller;
	
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Seller, String> tableColumnName;
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
	
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;
	
	@FXML
	private TableColumn<Seller, Seller> tableColumnEdit;
	
	@FXML
	private TableColumn<Seller, Seller> tableColumnRemove;
	
	@FXML
	private Button buttonSeller;
	
	private ObservableList<Seller> obsList;
	
	@FXML
	public void onButtonClick(ActionEvent event) {
		Stage parentStage = gui.utils.Utils.currentStage(event);
		Seller seller = new Seller();
		createDialogForm(seller, "/gui/SellerForm.fxml", parentStage);
	}
	
	public void setSellerService(SellerService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("O serviço estava nulo.");
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
	
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void createDialogForm(Seller seller, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			SellerFormController controller = loader.getController();
			controller.setSeller(seller);
			controller.setServices(new SellerService(), new DepartmentService());
			controller.loadAssociationsObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Informe os dados do vendedor:");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}
		catch(IOException exception) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a view", exception.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}
	
	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(oaram -> new TableCell<Seller, Seller>(){
			private final Button btn = new Button("Editar");
			
			@Override
			protected void updateItem(Seller seller, boolean empty) {
				super.updateItem(seller, empty);
				
				if (seller == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(btn);
				btn.setOnAction(event -> createDialogForm(seller, "/gui/SellerForm.fxml", 
						Utils.currentStage(event)));
				}
		});
	}
	
	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(oaram -> new TableCell<Seller, Seller>(){
			private final Button btn = new Button("Remover");
			
			@Override
			protected void updateItem(Seller seller, boolean empty) {
				super.updateItem(seller, empty);
				
				if (seller == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(btn);
				btn.setOnAction(event -> removeSeller(seller));
				}
		});
	}
	
	private void removeSeller(Seller seller) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmar exclusão:", "Tem certeza que quer deletar?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("O serviço não foi instanciado.");
			}
			try {
				service.remove(seller);
				updateTableView();
			}
			catch (dbIntegrityException exception) {
				Alerts.showAlert("Error removing object", exception.getMessage(), null, AlertType.ERROR);
			}
		}
	}
}
