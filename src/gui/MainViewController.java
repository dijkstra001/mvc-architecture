package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("Esse aplicativo tem como objetivo:\n"
				+ "1. Apresentar o modelo de padrão de arquitetura de software M.V.C.;\n"
				+ "2. Exibir a aplicabilidade do padrão em aplicações desktop; e\n"
				+ "3. Elencar os principais benefícios desse padrão de arquitetura.");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}
}
