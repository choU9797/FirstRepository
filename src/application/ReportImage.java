package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class ReportImage {
	static Parent root;
	static Stage stage = new Stage();
	ReportImage() {
		loadFXML();
	}

	void loadFXML() {
		
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/ReportImage.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("報告書プレビュー");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
