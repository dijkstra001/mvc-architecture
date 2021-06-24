package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import database.dbException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerFormController implements Initializable{
	
	private Seller seller;
	
	private SellerService service;
	
	private DepartmentService departmentService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private ComboBox<Department> cbDepartment;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Label labelErrorBaseSalary;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnCancel;
	
	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if (seller == null) {
			throw new IllegalStateException("A entidade vendedor não foi instanciada.");
		}
		if (service == null) {
			throw new IllegalStateException("O serviço não foi instanciado.");
		}
		try {
			seller = getFormData();
			service.saveOrUpdate(seller);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (dbException exception) {
			Alerts.showAlert("Erro ao salvar os dados", null, exception.getMessage(), AlertType.WARNING);
		}
		
		catch (ValidationException exception) {
			setErrorMessage(exception.getErrors());
		}	
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	private ObservableList<Department> departments;
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public void setServices(SellerService service, DepartmentService departmentService) {
		this.service = service;
		this.departmentService = departmentService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 100);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
	}
	
	public void updateFormData() {
		if (seller == null) {
			throw new IllegalStateException("A entidade vendedor não foi instanciada.");
		}
		txtId.setText(String.valueOf(seller.getId()));
		txtName.setText(seller.getName());
		txtEmail.setText(seller.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f",seller.getBaseSalary()));
		
		
		if (seller.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(seller.getBirthDate().toInstant(), ZoneId.systemDefault()));	
		}
		
		if (seller.getDepartment() == null) {
			cbDepartment.getSelectionModel().selectFirst();
		}else {
			cbDepartment.setValue(seller.getDepartment());
		}
	}
	
	public void loadAssociationsObjects() {
		if (departmentService == null) {
			throw new IllegalStateException("O departmentService estava nulo.");
		}
		
		List<Department> list = departmentService.findAll();
		
		departments = FXCollections.observableArrayList(list);
		cbDepartment.setItems(departments);
	}
	
	private Seller getFormData() {
		Seller seller = new Seller();
		
		ValidationException validation = new ValidationException("Exceção de validação.");
		
		seller.setId(Utils.tryParseToInteger(txtId.getText()));
		seller.setName(txtName.getText());
		
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			validation.addError("name", "O campo não pode estar vazio!");
		}
		
		if (validation.getErrors().size() > 0) {
			throw validation;
		}
		return seller;
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}
	
	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		cbDepartment.setCellFactory(factory);
		cbDepartment.setButtonCell(factory.call(null));
	}
}
