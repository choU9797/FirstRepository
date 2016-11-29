package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SalesController implements Initializable {
	@FXML
	TableView<SalesData> tableview;
	@FXML
	TableColumn<SalesData, String> name_column;
	@FXML
	TableColumn<SalesData, Integer> profit_column;
	@FXML
	TableColumn<SalesData, Integer> jitu_column;
	@FXML
	TableColumn<SalesData, Integer> riyou_column;
	@FXML
	TableColumn<SalesData, java.sql.Date> date_column;
	@FXML
	TableColumn<SalesData, String> transport_column;
	@FXML
	TextField name;
	@FXML
	TextField profit;
	@FXML
	TextField jitu;
	@FXML
	TextField riyou;
	@FXML
	DatePicker datepicker;
	@FXML
	ComboBox<String> combobox;
	@FXML
	Button add_button;
	@FXML
	Button delete_button;
	@FXML
	Label titlelabel;
	@FXML
	Pane pane;
	private Connection connector = null;
	private ObservableList<SalesData> list = FXCollections
			.observableArrayList();

	private ObservableList<String> combobox_list = FXCollections
			.observableArrayList("北海道", "東北", "北陸信越", "関東", "中部", "近畿", "中国",
					"四国", "九州", "沖縄");
	private String selectquery = "select * from salesdata";
	private SalesData selected_salesdata;
	private PreparedStatement statement = null;
	private ResultSet resultset = null;
	private SimpleStringProperty getName;
	private SimpleIntegerProperty getProfit;
	private SimpleIntegerProperty getJitu;
	private SimpleIntegerProperty getRiyou;
	private SimpleStringProperty getTransport;
	private java.sql.Date getDate;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// tablecolumnの初期化
		name_column
				.setCellValueFactory(new PropertyValueFactory<SalesData, String>(
						"name"));
		profit_column
				.setCellValueFactory(new PropertyValueFactory<SalesData, Integer>(
						"profit"));
		jitu_column
				.setCellValueFactory(new PropertyValueFactory<SalesData, Integer>(
						"jitu"));
		riyou_column
				.setCellValueFactory(new PropertyValueFactory<SalesData, Integer>(
						"riyou"));
		date_column
				.setCellValueFactory(new PropertyValueFactory<SalesData, java.sql.Date>(
						"date"));

		transport_column
				.setCellValueFactory(new PropertyValueFactory<SalesData, String>(
						"transport"));
		// databaseにアクセス
		connector = SQLConnector.connector();
		System.out.println("========Initialize==========");
		try {
			statement = connector.prepareStatement(selectquery);
			resultset = statement.executeQuery();
			while (resultset.next()) {
				getName = new SimpleStringProperty(resultset.getString(1));
				getProfit = new SimpleIntegerProperty(resultset.getInt(2));
				getJitu = new SimpleIntegerProperty(resultset.getInt(3));
				getRiyou = new SimpleIntegerProperty(resultset.getInt(4));
				getTransport = new SimpleStringProperty(resultset.getString(5));
				getDate = resultset.getDate(6);
				// databaseから入手したデータをSalesDataにまとめてlistに格納
				list.add(new SalesData(getName, getProfit, getJitu, getRiyou,
						getTransport, getDate));

				System.out.println(" -- name -- " + resultset.getString(1)
						+ " -- profit -- " + resultset.getInt(2)
						+ " -- jitu -- " + resultset.getInt(3) + " -- riyou --"
						+ resultset.getInt(4) + " -- transport -- "
						+ resultset.getString(5) + " -- date -- "
						+ resultset.getInt(6));
			}

			// tableviewの初期化
			tableview.setOnMouseClicked(e -> {
				selected_salesdata = tableview.getSelectionModel()
						.getSelectedItem();
			});
			// tableviewにデータをセット
			tableview.setItems(list);
			statement.close();
			resultset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// comboboxの初期化
		combobox.setItems(combobox_list);
		// add_buttonの初期化
		add_button.setOnAction(e -> insertData());
		// delete_buttonの初期化
		delete_button.setOnAction(e -> deleteData(selected_salesdata));
		drowCircle();
	}

	private void insertData() {
		String query = "insert into salesdata (name,profit,jitu,riyou,transport,date) values (?,?,?,?,?,?)";
		try {
			// 入力されたデータをdatabaseへ
			statement = connector.prepareStatement(query);
			statement.setString(1, name.getText());
			statement.setInt(2, Integer.parseInt(profit.getText()));
			statement.setInt(3, Integer.parseInt(jitu.getText()));
			statement.setInt(4, Integer.parseInt(riyou.getText()));
			statement.setString(5, combobox.getSelectionModel()
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
			// 入力されたデータをlistへ
			getName = new SimpleStringProperty(name.getText());
			getProfit = new SimpleIntegerProperty(Integer.parseInt(profit
					.getText()));
			getJitu = new SimpleIntegerProperty(
					Integer.parseInt(jitu.getText()));
			getRiyou = new SimpleIntegerProperty(Integer.parseInt(riyou
					.getText()));
			getTransport = new SimpleStringProperty(combobox
					.getSelectionModel().getSelectedItem());

			// databaseから入手したデータをSalesDataにまとめてlistに格納
			list.add(new SalesData(getName, getProfit, getJitu, getRiyou,
					getTransport, d2));

			// detabaseのデータを書き出す
			statement = connector.prepareStatement(selectquery);
			resultset = statement.executeQuery();
			System.out.println("========AFTER_INSERT==========");
			while (resultset.next()) {
				System.out.println(" -- name -- " + resultset.getString(1)
						+ " -- profit -- " + resultset.getInt(2)
						+ " -- jitu -- " + resultset.getInt(3) + " -- riyou --"
						+ resultset.getInt(4) + " -- transport -- "
						+ resultset.getString(5) + " -- date -- "
						+ resultset.getInt(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void drowCircle() {
		pane.getChildren().clear();
		Random random = new Random();
		Timeline timeline = new Timeline();
		Bloom bloom = new Bloom();
		bloom.setThreshold(0);
		
		for (int i = 0; i < 12; i++) {
			Circle c = new Circle(random.nextInt(800), random.nextInt(800),
					30);
			c.setFill(null);
			c.setStrokeWidth(0.1);
			c.setStroke(Color.color(Math.random(),Math.random(),Math.random()));
			pane.getChildren().add(c);
			KeyValue value1 = new KeyValue(c.scaleXProperty(), 80);
			KeyValue value2 = new KeyValue(c.scaleYProperty(), 80);
			KeyFrame keyframe = new KeyFrame(Duration.millis(5700), value1,
					value2);
			timeline.getKeyFrames().add(keyframe);
			timeline.setCycleCount(1);
			
		}
		titlelabel.setEffect(bloom);
		timeline.setOnFinished(e -> {
			drowCircle();
		});
		timeline.play();
		
	}

	private void deleteData(SalesData selected_salesdata) {
		String query = "delete from salesdata where name = ? and profit = ? and jitu = ? and riyou = ? and transport = ? and date = ?";
		try {
			statement = connector.prepareStatement(query);
			statement.setString(1, selected_salesdata.getName());
			statement.setInt(2, selected_salesdata.getProfit());
			statement.setInt(3, selected_salesdata.getJitu());
			statement.setInt(4, selected_salesdata.getRiyou());
			statement.setString(5, selected_salesdata.getTransport());
			statement.setDate(6, selected_salesdata.getDate());

			statement.executeUpdate();
			statement = connector.prepareStatement(selectquery);
			resultset = statement.executeQuery();
			System.out.println("========AFTER_DELETE==========");
			while (resultset.next()) {
				System.out.println(" -- name -- " + resultset.getString(1)
						+ " -- profit -- " + resultset.getInt(2)
						+ " -- jitu -- " + resultset.getInt(3) + " -- riyou --"
						+ resultset.getInt(4) + " -- transport -- "
						+ resultset.getString(5) + " -- date -- "
						+ resultset.getInt(6));
			}
			list.remove(selected_salesdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
