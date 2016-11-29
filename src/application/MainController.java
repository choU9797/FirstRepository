package application;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainController implements Initializable {
	@FXML
	Button worker;
	@FXML
	Button run;
	@FXML
	Button report;
	@FXML
	Button sales;
	@FXML
	Pane pane;
	@FXML
	StackPane stack;
	@FXML 
	Label titlelabel;
	ReportImageController reportimagecontroller;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO Auto-generated method stub
		worker.setOnAction(e -> new Worker());
		run.setOnAction(e -> new Run());
		report.setOnAction(e -> {
			new Report();
			new ReportImage();
		});
		sales.setOnAction(e -> new Sales());
		titlelabel.setVisible(false);
		drowLine();
	}

	private void drowLine() {
		final Line line1 = new Line(0,254,700,40);
		line1.setStroke(Color.DARKORANGE);
		line1.setStrokeWidth(6.0);
		
		final Line line2 = new Line(0,257,700,42);
		line2.setStroke(Color.BEIGE);
		line2.setStrokeWidth(4.0);

		final Line line3 = new Line(0,260,700,45);
		line3.setStroke(Color.AQUAMARINE);
		line3.setStrokeWidth(6.0);

		final Line line4 = new Line(0,266,700,47);
		line4.setStroke(Color.WHITE);
		line4.setStrokeWidth(4.0);

		final Line line5 = new Line(0,270,700,49);
		line5.setStroke(Color.DARKBLUE);
		line5.setStrokeWidth(5.0);

		final Line line6 = new Line(0,275,700,52);
		line6.setStroke(Color.CRIMSON);
		line6.setStrokeWidth(7.0);

		final Line line7 = new Line(0,279,700,55);
		line7.setStroke(Color.BISQUE);
		line7.setStrokeWidth(6.0);

		final Line line8 = new Line(0,285,700,58);
		line8.setStroke(Color.CORNFLOWERBLUE);
		line8.setStrokeWidth(1.0);

		final Line line9 = new Line(0,289,700,61);
		line9.setStroke(Color.DARKSEAGREEN);
		line9.setStrokeWidth(5.0);

		final Line line10 = new Line(0,294,700,65);
		line10.setStroke(Color.GOLD);
		line10.setStrokeWidth(10.0);

		final Line line11 = new Line(0,297,700,68);
		line11.setStroke(Color.DEEPPINK);
		line11.setStrokeWidth(3.0);

		final Line line12 = new Line(0,299,700,70);
		line12.setStroke(Color.DIMGREY);
		line12.setStrokeWidth(3.0);

		TranslateTransition tt  = new TranslateTransition();
		Rectangle rect  = new Rectangle(0,0,600,600);
		rect.setFill(Color.YELLOW);
		tt.setNode(rect);
		tt.setDuration(Duration.millis(2000));
		tt.setFromX(0);
		tt.setToX(600);
		tt.play();
		pane.getChildren().addAll(line1, line2, line3, line4, line5, line6,
				line7, line8, line9, line10, line11, line12,rect,titlelabel);

		
		KeyFrame fadein_start = new KeyFrame(Duration.ZERO,new KeyValue(titlelabel.opacityProperty(),0.0));
		KeyFrame fadein_end = new KeyFrame(new Duration(1000),new KeyValue(titlelabel.opacityProperty(),1.0));
		Timeline timeline2 = new Timeline();
		timeline2.getKeyFrames().addAll(fadein_start,fadein_end);
		timeline2.setCycleCount(Animation.INDEFINITE);
		timeline2.setAutoReverse(true);
		tt.setOnFinished(e -> {
			titlelabel.setVisible(true);
			timeline2.play();
		});
		
		
		

	}
}
