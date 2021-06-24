package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import database.dbException;
import gui.listeners.DataChangeListener;
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
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable{
	
	private Seller seller;
	
	private SellerService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public void setSellerService(SellerService service) {
		this.service = service;
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
		Constraints.setTextFieldMaxLength(txtName, 40);
	}
	
	public void updateFormData() {
		if (seller == null) {
			throw new IllegalStateException("A entidade vendedor não foi instanciada.");
		}
		txtId.setText(String.valueOf(seller.getId()));
		txtName.setText(seller.getName());
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
}
