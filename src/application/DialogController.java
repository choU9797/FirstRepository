package application;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;

public class DialogController implements Initializable {
	Report report;
	ReportImage reportImage;
	ReportImageController reportimagecontroller;
	@FXML
	Button ok_button;
	@FXML
	Button cancel_button;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		ok_button.setOnAction(e -> {
			//reportimagecontroller.print();
			});
		cancel_button.setOnAction(e -> {
			Report.stage.setScene(Report.scene);
			ReportImage.stage.getScene().getWindow().hide();
		});
	}

}
