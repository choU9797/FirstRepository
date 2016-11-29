package application;

import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.w3c.dom.css.Rect;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class RunController implements Initializable {
	@FXML
	TableView<RunData> tableview;
	@FXML
	ComboBox<Integer> no_combobox;
	@FXML
	ComboBox<String> transport_combobox;
	@FXML
	Label name;
	@FXML
	TextField rundistance;
	@FXML
	TextField emptydistance;
	@FXML
	DatePicker datepicker;
	@FXML
	Button add_button;
	@FXML
	Button delete_button;
	@FXML
	TableColumn<RunData, Integer> no_column;
	@FXML
	TableColumn<RunData, String> name_column;
	@FXML
	TableColumn<RunData, Integer> run_column;
	@FXML
	TableColumn<RunData, Integer> exist_column;
	@FXML
	TableColumn<RunData, Date> date_column;
	@FXML
	TableColumn<RunData, String> transport_column;
	@FXML
	Label titlelabel;
	@FXML
	Pane pane;

	private Connection connector = null;
	private RunData selected_rundata;
	private ObservableList<RunData> list = FXCollections.observableArrayList();
	public static ObservableList<Integer> no_list;
	private ObservableList<String> transport_list = FXCollections
			.observableArrayList("北海道", "東北", "北陸信越", "関東", "中部", "近畿", "中国",
					"四国", "九州", "沖縄");
	private String initquery = "select * from RunData";
	private ResultSet resultset = null;
	private PreparedStatement statement = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		connector = SQLConnector.connector();
		init_nolist();
		try {
			statement = connector.prepareStatement(initquery);
			resultset = statement.executeQuery();
			System.out.println("========INITIALIZE==========");
			while (resultset.next()) {
				// resultsetのレコードをlistに格納
				SimpleIntegerProperty getno = new SimpleIntegerProperty(
						resultset.getInt(1));
				SimpleStringProperty getname = new SimpleStringProperty(
						resultset.getString(2));
				SimpleIntegerProperty getrundistance = new SimpleIntegerProperty(
						resultset.getInt(3));
				SimpleIntegerProperty getemptydistance = new SimpleIntegerProperty(
						resultset.getInt(4));
				SimpleStringProperty gettransport = new SimpleStringProperty(
						resultset.getString(5));
				Date getdate = resultset.getDate(6);
				list.add(new RunData(getno, getname, getrundistance,
						getemptydistance, getdate, gettransport));

				System.out.println("no -- " + resultset.getInt(1)
						+ " -- name -- " + resultset.getString(2)
						+ " -- run -- " + resultset.getInt(3) + " -- empty"
						+ " -- " + resultset.getInt(4) + " -- transport -- "
						+ resultset.getString(5) + "-- Date -- "
						+ resultset.getDate(6));
			}
			// tableviewの初期化
			tableview.setOnMouseClicked(event -> {
				selected_rundata = tableview.getSelectionModel()
						.getSelectedItem();
			});
			tableview.setItems(list);

			statement.close();
			resultset.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		no_column
				.setCellValueFactory(new PropertyValueFactory<RunData, Integer>(
						"No"));
		name_column
				.setCellValueFactory(new PropertyValueFactory<RunData, String>(
						"Name"));
		run_column
				.setCellValueFactory(new PropertyValueFactory<RunData, Integer>(
						"Run"));
		exist_column
				.setCellValueFactory(new PropertyValueFactory<RunData, Integer>(
						"Exist"));
		date_column
				.setCellValueFactory(new PropertyValueFactory<RunData, Date>(
						"Date"));
		transport_column
				.setCellValueFactory(new PropertyValueFactory<RunData, String>(
						"Transport"));
		transport_combobox.setItems(transport_list);
		add_button.setOnAction(e -> insertData());
		delete_button.setOnAction(e -> deleteData(selected_rundata));
		no_combobox.setOnAction(e -> {
			String query = "select no,name from employee where no = ?";
			try {
				statement = connector.prepareStatement(query);
				statement.setInt(1, no_combobox.getValue());
				resultset = statement.executeQuery();
				name.setText("Driver: " + resultset.getString(2));
				statement.close();
				resultset.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		delete_button.setId("delete_button");
		drowLine();
	}

	private void drowLine() {
		ArrayList<Line> gold_line = new ArrayList<Line>();
		for (int i = 0; i < 15; i++) {
			Line line = new Line();
			line.setStartX(0);
			line.setStartY(i * 5 + 40);
			line.setEndX(800);
			line.setEndY(i * 50 * 0.02 + 520);
			line.setStroke(Color.color(Math.random(), Math.random(),
					Math.random()));
			line.setStrokeWidth(8.0);
			gold_line.add(line);
		}
		ArrayList<Line> gold_line2 = new ArrayList<Line>();
		for (int i = 0; i < 10; i++) {
			Line line = new Line();
			line.setStartX(i * 0.2 + 230);
			line.setStartY(0);
			line.setEndX(i * 3.5 + 740);
			line.setEndY(500);
			line.setStroke(Color.color(Math.random(), Math.random(),
					Math.random()));
			line.setStrokeWidth(2.0);
			gold_line2.add(line);
		}
		Rectangle rect = new Rectangle(0,0, 800,500);
		rect.setFill(Color.WHITE);
		FadeTransition ft = new FadeTransition();
		ft.setNode(rect);
		ft.setFromValue(2.0);
		ft.setToValue(0);
		ft.setDuration(Duration.millis(2500));
		ft.play();
		pane.getChildren().addAll(gold_line2);
		pane.getChildren().addAll(gold_line);
		pane.getChildren().add(rect);
	}

	public void init_nolist() {

		String query = "select * from employee";
		no_list = FXCollections.observableArrayList();
		try {
			statement = connector.prepareStatement(query);
			resultset = statement.executeQuery();
			while (resultset.next()) {
				no_list.add(resultset.getInt(1));
			}
			no_combobox.setItems(no_list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertData() {

		PreparedStatement statement = null;

		String query = "insert into rundata (no,name,run,empty,transport,date) values (?,?,?,?,?,?)";
		ResultSet resultset;
		try {
			// RunDataに入力されたデータをレコードに変換
			statement = connector.prepareStatement(query);
			statement.setInt(1, no_combobox.getSelectionModel()
					.getSelectedItem());
			statement.setString(2, name.getText());
			statement.setInt(3, Integer.parseInt(rundistance.getText()));
			statement.setInt(4, Integer.parseInt(emptydistance.getText()));
			statement.setString(5, transport_combobox.getSelectionModel()
					.getSelectedItem());
			LocalDate localDate = datepicker.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId
					.systemDefault()));
			java.util.Date date = Date.from(instant);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			java.sql.Date d2 = new java.sql.Date(cal.getTimeInMillis());
			statement.setDate(6, d2);
			statement.executeUpdate();
			statement.close();
			// 入力されたデータをRunDataにしてListに格納
			SimpleIntegerProperty getno = new SimpleIntegerProperty(no_combobox
					.getSelectionModel().getSelectedItem());
			SimpleStringProperty getname = new SimpleStringProperty(
					name.getText());
			SimpleIntegerProperty getrundistance = new SimpleIntegerProperty(
					Integer.parseInt(rundistance.getText()));
			SimpleIntegerProperty getemptydistance = new SimpleIntegerProperty(
					Integer.parseInt(emptydistance.getText()));
			SimpleStringProperty gettransport = new SimpleStringProperty(
					transport_combobox.getSelectionModel().getSelectedItem());
			list.add(new RunData(getno, getname, getrundistance,
					getemptydistance, d2, gettransport));

			statement = connector.prepareStatement(initquery);
			// detabaseのデータを書き出す
			resultset = statement.executeQuery();
			System.out.println("========AFTER_INSERT==========");
			while (resultset.next()) {
				System.out.println("no -- " + resultset.getInt(1)
						+ " -- name -- " + resultset.getString(2)
						+ " -- run -- " + resultset.getInt(3) + " -- empty -- "
						+ resultset.getInt(4) + " -- transport -- "
						+ resultset.getString(5) + "-- Date -- "
						+ resultset.getDate(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void deleteData(RunData rundata) {

		PreparedStatement statement = null;
		ResultSet resultset;
		String query = "delete from rundata where no = ? and name = ? and run = ? and empty = ? and transport = ? and date = ?";
		try {
			statement = connector.prepareStatement(query);
			statement.setInt(1, rundata.getNo());
			statement.setString(2, rundata.getName());
			statement.setInt(3, rundata.getRun());
			statement.setInt(4, rundata.getEmpty());
			statement.setString(5, rundata.getTransport());
			statement.setDate(6, rundata.getDate());
			statement.executeUpdate();
			list.remove(rundata);
			statement = connector.prepareStatement(initquery);

			// detabaseのデータを書き出す
			resultset = statement.executeQuery();
			System.out.println("========AFTER_DELETE==========");
			while (resultset.next()) {
				System.out.println("no -- " + resultset.getInt(1)
						+ " -- name -- " + resultset.getString(2)
						+ " -- run -- " + resultset.getInt(3) + " -- empty -- "
						+ resultset.getInt(4) + " -- transport -- "
						+ resultset.getString(5) + "-- Date -- "
						+ resultset.getDate(6));
				;
			}
			RunController.no_list.remove(rundata);

			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
