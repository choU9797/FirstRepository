package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sales {

	Sales() {
		loadFXML();
	}

	void loadFXML() {
		Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/Sales.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(
					getClass().getResource("/css/Sales.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("売上表編集");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
