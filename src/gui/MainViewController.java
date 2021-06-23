package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("Menu Vendedor");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		System.out.println("Menu Vendedor");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/AboutView.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
	
	private void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Label text1 = new Label("Sobre o aplicativo:");
			text1.setFont(new Font("Arial", 13));
			text1.setStyle("-fx-font-weight: bold");
			Label text2 = new Label("Esse aplicativo tem como objetivo:");
			Label text3 = new Label("1. Apresentar o modelo de padrão de arquitetura de software M.V.C.;");
			Label text4 = new Label("2. Exibir a aplicabilidade do padrão em aplicações desktop; e");
			Label text5 = new Label("3. Elencar os principais benefícios desse padrão de arquitetura.");
			
			Scene mainScene = Main.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(text1, text2, text3, text4, text5);
			mainVbox.getChildren().addAll(newVbox.getChildren());
		}
		catch(IOException exception) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a página", exception.getMessage(), AlertType.ERROR);
		}
	}
}
