package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
	
	Parent root;
	Scene scene;
	@Override
	public void start(Stage primaryStage) {
		try {
			root = FXMLLoader.load(getClass().getResource(
					"/FXML/Main.fxml"));
			scene = new Scene(root);
			scene.getStylesheets().add(
					getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("MainMenu");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
