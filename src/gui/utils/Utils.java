package gui.utils;

import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(javafx.event.ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static Integer tryParseToInteger(String text) {
		try {
			return Integer.parseInt(text);
		}
		catch(NumberFormatException exception) {
			return null;
		}
	}
}
