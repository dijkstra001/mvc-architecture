package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import database.dbException;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{
	
	private Department department;
	
	private DepartmentService service;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnCancel;
	
	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if (department == null) {
			throw new IllegalStateException("A entidade departamento não foi instanciada.");
		}
		if (service == null) {
			throw new IllegalStateException("O serviço não foi instanciado.");
		}
		try {
			department = getFormData();
			service.saveOrUpdate(department);
			Utils.currentStage(event).close();
		}
		catch (dbException exception) {
			Alerts.showAlert("Erro ao salvar os dados", null, exception.getMessage(), AlertType.WARNING);
		}
		
	}
	
	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 40);
	}
	
	public void updateFormData() {
		if (department == null) {
			throw new IllegalStateException("A entidade departamento não foi instanciada.");
		}
		txtId.setText(String.valueOf(department.getId()));
		txtName.setText(department.getName());
	}
	
	private Department getFormData() {
		Department department = new Department();
		department.setId(Utils.tryParseToInteger(txtId.getText()));
		department.setName(txtName.getText().toUpperCase());
		return department;
	}
}
