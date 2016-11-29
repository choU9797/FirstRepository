package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Report {
	static Stage stage = new Stage();
	static Scene scene;
	static Scene dialog;

	Report() {
		loadFXML();
	}

	void loadFXML() {

		Parent root;
		Parent dialog_root;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/Report.fxml"));
			dialog_root = FXMLLoader.load(getClass().getResource(
					"/FXML/Dialog.fxml"));
			scene = new Scene(root);
			scene.getStylesheets().add(
					getClass().getResource("/css/Report.css").toExternalForm());
			
			dialog = new Scene(dialog_root);
			dialog.getStylesheets().add(
					getClass().getResource("/css/dialog.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("報告書印刷");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
