package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.sql.*;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

public class WorkerController implements Initializable {
	@FXML
	TextField no;
	@FXML
	TextField name;
	@FXML
	ComboBox<String> combobox;
	@FXML
	Button add_button;
	@FXML
	Button delete_button;
	@FXML
	TableView<Employee> tableview;
	@FXML
	TableColumn<Employee, Integer> no_column;
	@FXML
	TableColumn<Employee, String> name_column;
	@FXML
	TableColumn<Employee, String> transport_column;
	@FXML
	Label titlelabel;
	@FXML
	Pane pane;
	private Connection connector = null;
	private ObservableList<Employee> list = FXCollections.observableArrayList();
	private ObservableList<String> combobox_list = FXCollections
			.observableArrayList("北海道", "東北", "北陸信越", "関東", "中部", "近畿", "中国",
					"四国", "九州", "沖縄");
	private String selectquery = "select * from employee";
	private Employee selected_employee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// SQLiteのコネクター
		connector = SQLConnector.connector();
		// TODO Auto-generated method stub
		no_column
				.setCellValueFactory(new PropertyValueFactory<Employee, Integer>(
						"No"));
		name_column
				.setCellValueFactory(new PropertyValueFactory<Employee, String>(
						"Name"));
		transport_column
				.setCellValueFactory(new PropertyValueFactory<Employee, String>(
						"Transport"));
		tableview.setItems(list);
		// add_buttonの処理
		add_button.setOnAction(e -> {
			insertData();
		});
		// delete_buttonの処理
		delete_button.setOnAction(e -> {
			deleteData(selected_employee);
		});
		// comboboxの初期化
		combobox.setItems(combobox_list);
		// tableviewの初期化
		tableview
				.setOnMouseClicked(e -> {
					selected_employee = tableview.getSelectionModel()
							.getSelectedItem();
				});
		PreparedStatement statement = null;
		try {
			statement = connector.prepareStatement(selectquery);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				// mydatabaseのemployeeのレコードをEmployeeに変換
				SimpleIntegerProperty getno = new SimpleIntegerProperty(
						resultset.getInt(1));
				SimpleStringProperty getname = new SimpleStringProperty(
						resultset.getString(2));
				SimpleStringProperty gettransport = new SimpleStringProperty(
						resultset.getString(3));
				list.add(new Employee(getno, getname, gettransport));
				System.out.println("no -- " + resultset.getInt(1)
						+ " -- name -- " + resultset.getString(2)
						+ " -- transport -- " + resultset.getString(3));
			}
			delete_button.setId("delete_button");
			titlelabel.setId("titlelabel");
			statement.close();
			resultset.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		drowLine();
	}

	private void drowLine() {
		Line line1 = new Line(30, 0, 30, 490);
		line1.setStroke(Color.CHARTREUSE);
		line1.setStrokeWidth(6.0);

		Line line2 = new Line(32, 0, 32, 490);
		line2.setStroke(Color.ALICEBLUE);
		line2.setStrokeWidth(4.0);

		Line line3 = new Line(35, 0, 35, 490);
		line3.setStroke(Color.CORAL);
		line3.setStrokeWidth(7.0);

		Line line4 = new Line(38, 0, 38, 490);
		line4.setStroke(Color.DARKORANGE);
		line4.setStrokeWidth(6.0);

		Line line5 = new Line(43, 0, 43, 490);
		line5.setStroke(Color.GOLD);
		line5.setStrokeWidth(8.0);

		Line line6 = new Line(46, 0, 46, 490);
		line6.setStroke(Color.AQUAMARINE);
		line6.setStrokeWidth(6.0);

		Line line7 = new Line(47, 0, 47, 480);
		line7.setStroke(Color.GAINSBORO);
		line7.setStrokeWidth(5.0);

		Line line8 = new Line(49, 0, 49, 480);
		line8.setStroke(Color.GHOSTWHITE);
		line8.setStrokeWidth(4.0);

		Line line9 = new Line(54, 0, 54, 480);
		line9.setStroke(Color.SKYBLUE);
		line9.setStrokeWidth(4.0);

		Line point = new Line(30, 0, 30, 490);
		point.setStroke(Color.YELLOW);
		point.setStrokeWidth(55.0);

		TranslateTransition tt = new TranslateTransition();
		tt.setNode(point);
		tt.setDuration(Duration.millis(5000));
		tt.setByY(550);
		pane.getChildren().addAll(line1, line2, line3, line4, line5, line6,
				line7, line8, line9, point);
		// tt.setAutoReverse(true);
		// tt.setCycleCount(Animation.INDEFINITE);
		tt.play();
		// line1.endXProperty().bind(line1.startXProperty().subtract(60));
		// KeyFrame keyframe1 = new KeyFrame(new Duration(1500),new
		// KeyValue(line1.scaleYProperty(),200));
		//
		// timeline1.setAutoReverse(true);
		// timeline1.setCycleCount(Animation.INDEFINITE);
		// timeline1.getKeyFrames().addAll(keyframe1);
		// timeline1.play();
	}

	private void insertData() {
		PreparedStatement statement = null;

		String query = "insert into employee (no,name,transport) values (?,?,?)";
		ResultSet resultset;
		try {
			statement = connector.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(no.getText()));
			statement.setString(2, name.getText());
			statement.setString(3, combobox.getSelectionModel()
					.getSelectedItem());
			statement.executeUpdate();
			statement.close();
			// textfieldから入手したデータをEmployeeにしてListに格納
			SimpleIntegerProperty getno = new SimpleIntegerProperty(
					Integer.parseInt(no.getText()));
			SimpleStringProperty getname = new SimpleStringProperty(
					name.getText());
			SimpleStringProperty gettransport = new SimpleStringProperty(
					combobox.getSelectionModel().getSelectedItem());
			list.add(new Employee(getno, getname, gettransport));

			statement = connector.prepareStatement(selectquery);
			// detabaseのデータを書き出す
			resultset = statement.executeQuery();
			System.out.println("========AFTER_INSERT==========");
			while (resultset.next()) {
				System.out.println("no -- " + resultset.getInt(1)
						+ " -- name -- " + resultset.getString(2)
						+ " -- transport -- " + resultset.getString(3));
			}
			RunController.no_list.add(Integer.parseInt(no.getText()));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteData(Employee employee) {
		PreparedStatement statement = null;
		ResultSet resultset;
		String query = "delete from employee where no = ? and name = ? and transport = ?";
		try {
			statement = connector.prepareStatement(query);
			statement.setInt(1, employee.getNo());
			statement.setString(2, employee.getName());
			statement.setString(3, employee.getTransport());
			statement.executeUpdate();
			list.remove(employee);
			statement = connector.prepareStatement(selectquery);

			// detabaseのデータを書き出す
			resultset = statement.executeQuery();
			System.out.println("========AFTER_DELETE==========");
			while (resultset.next()) {
				System.out.println("no -- " + resultset.getInt(1)
						+ " -- name -- " + resultset.getString(2)
						+ " -- transport -- " + resultset.getString(3));
			}
			RunController.no_list.remove(employee);

			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
