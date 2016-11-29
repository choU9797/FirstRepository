package application;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrintQuality;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class ReportController implements Initializable {
	DialogController dialogController = new DialogController();
	@FXML
	RadioButton ippan;
	@FXML
	RadioButton tokuseki;
	@FXML
	RadioButton riyoubutton;
	@FXML
	RadioButton reikyu;
	@FXML
	TextField jigyousya;
	@FXML
	TextField adress;
	@FXML
	TextField business;
	@FXML
	TextField represent;
	@FXML
	TextField role;
	@FXML
	TextField tell;
	@FXML
	TextField employee;
	@FXML
	TextField car;
	@FXML
	TextField driver;
	@FXML
	TextField check9_field;
	@FXML
	RadioButton check1;
	@FXML
	RadioButton check2;
	@FXML
	RadioButton check3;
	@FXML
	RadioButton check4;
	@FXML
	RadioButton check5;
	@FXML
	RadioButton check6;
	@FXML
	RadioButton check7;
	@FXML
	RadioButton check8;
	@FXML
	RadioButton check9;
	@FXML
	ComboBox<String> combobox;
	@FXML
	TextField zituzai;
	@FXML
	TextField zitudou;
	@FXML
	TextField soukou;
	@FXML
	TextField jisya;
	@FXML
	TextField jitu;
	@FXML
	TextField riyou;
	@FXML
	TextField eigyou;
	@FXML
	TextField car_accident;
	@FXML
	TextField heavy_accident;
	@FXML
	TextField dead;
	@FXML
	TextField injuries;
	@FXML
	TabPane tabpane;
	PreparedStatement statement = null;
	ResultSet resultset = null;

	private ObservableList<String> transport_list = FXCollections
			.observableArrayList("北海道", "東北", "北陸信越", "関東", "中部", "近畿", "中国",
					"四国", "九州", "沖縄");
	String selected_transport;
	Connection connector;
	Calendar cal;
	private java.util.Date preYear;
	private java.util.Date afterYear;
	private java.sql.Date preyear;
	private java.sql.Date afteryear;

	// Queryを格納するための配列
	private String[] jituzai_queries = new String[10];
	private String[] jitudou_queries = new String[10];
	private String[] soukou_queries = new String[10];
	private String[] jisya_queries = new String[10];
	private String[] jitu_queries = new String[10];
	private String[] riyou_queries = new String[10];
	private String[] eigyou_queries = new String[10];
	// Queryで取得したデータを格納する
	private int[] jituzai_Data = new int[10];
	private int[] jitudou_Data = new int[10];
	private int[] soukou_Data = new int[10];
	private int[] jisya_Data = new int[10];
	private int[] jitu_Data = new int[10];
	private int[] riyou_Data = new int[10];
	private int[] eigyou_Data = new int[10];

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// 画像の読み込み
		connector = SQLConnector.connector();
		cal = Calendar.getInstance();
		// 指定したい数値-1で日付を指定する
		cal.set(cal.get(Calendar.YEAR), 3, 1);
		preYear = cal.getTime();
		cal.set(cal.get(Calendar.YEAR) + 1, 2, 31);
		afterYear = cal.getTime();

		cal.setTime(preYear);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		preyear = new java.sql.Date(cal.getTimeInMillis());
		cal.setTime(afterYear);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		afteryear = new java.sql.Date(cal.getTimeInMillis());
		initQueries();
		initData();
		initCompo();
		combobox.setItems(transport_list);
		combobox.setOnAction(e -> {
			textData(combobox.getSelectionModel().getSelectedItem());
		});
		// ReportImageControllerの配列に格納
		// 北海道
		ReportImageController.hokaido[0] = "" + jituzai_Data[0];
		ReportImageController.hokaido[1] = "" + jitudou_Data[0];
		ReportImageController.hokaido[2] = "" + soukou_Data[0];
		ReportImageController.hokaido[3] = "" + jisya_Data[0];
		ReportImageController.hokaido[4] = "" + jitu_Data[0];
		ReportImageController.hokaido[5] = "" + riyou_Data[0];
		ReportImageController.hokaido[6] = "" + eigyou_Data[0];
		// 東北
		ReportImageController.tohoku[0] = "" + jituzai_Data[1];
		ReportImageController.tohoku[1] = "" + jitudou_Data[1];
		ReportImageController.tohoku[2] = "" + soukou_Data[1];
		ReportImageController.tohoku[3] = "" + jisya_Data[1];
		ReportImageController.tohoku[4] = "" + jitu_Data[1];
		ReportImageController.tohoku[5] = "" + riyou_Data[1];
		ReportImageController.tohoku[6] = "" + eigyou_Data[1];
		// 北陸信越
		ReportImageController.hokuriku[0] = "" + jituzai_Data[2];
		ReportImageController.hokuriku[1] = "" + jitudou_Data[2];
		ReportImageController.hokuriku[2] = "" + soukou_Data[2];
		ReportImageController.hokuriku[3] = "" + jisya_Data[2];
		ReportImageController.hokuriku[4] = "" + jitu_Data[2];
		ReportImageController.hokuriku[5] = "" + riyou_Data[2];
		ReportImageController.hokuriku[6] = "" + eigyou_Data[2];
		// 関東
		ReportImageController.kanto[0] = "" + jituzai_Data[3];
		ReportImageController.kanto[1] = "" + jitudou_Data[3];
		ReportImageController.kanto[2] = "" + soukou_Data[3];
		ReportImageController.kanto[3] = "" + jisya_Data[3];
		ReportImageController.kanto[4] = "" + jitu_Data[3];
		ReportImageController.kanto[5] = "" + riyou_Data[3];
		ReportImageController.kanto[6] = "" + eigyou_Data[3];
		// 中部
		ReportImageController.tyubu[0] = "" + jituzai_Data[4];
		ReportImageController.tyubu[1] = "" + jitudou_Data[4];
		ReportImageController.tyubu[2] = "" + soukou_Data[4];
		ReportImageController.tyubu[3] = "" + jisya_Data[4];
		ReportImageController.tyubu[4] = "" + jitu_Data[4];
		ReportImageController.tyubu[5] = "" + riyou_Data[4];
		ReportImageController.tyubu[6] = "" + eigyou_Data[4];
		// 近畿
		ReportImageController.kinki[0] = "" + jituzai_Data[5];
		ReportImageController.kinki[1] = "" + jitudou_Data[5];
		ReportImageController.kinki[2] = "" + soukou_Data[5];
		ReportImageController.kinki[3] = "" + jisya_Data[5];
		ReportImageController.kinki[4] = "" + jitu_Data[5];
		ReportImageController.kinki[5] = "" + riyou_Data[5];
		ReportImageController.kinki[6] = "" + eigyou_Data[5];
		// 中国
		ReportImageController.tyugoku[0] = "" + jituzai_Data[6];
		ReportImageController.tyugoku[1] = "" + jitudou_Data[6];
		ReportImageController.tyugoku[2] = "" + soukou_Data[6];
		ReportImageController.tyugoku[3] = "" + jisya_Data[6];
		ReportImageController.tyugoku[4] = "" + jitu_Data[6];
		ReportImageController.tyugoku[5] = "" + riyou_Data[6];
		ReportImageController.tyugoku[6] = "" + eigyou_Data[6];
		// 四国
		ReportImageController.sikoku[0] = "" + jituzai_Data[7];
		ReportImageController.sikoku[1] = "" + jitudou_Data[7];
		ReportImageController.sikoku[2] = "" + soukou_Data[7];
		ReportImageController.sikoku[3] = "" + jisya_Data[7];
		ReportImageController.sikoku[4] = "" + jitu_Data[7];
		ReportImageController.sikoku[5] = "" + riyou_Data[7];
		ReportImageController.sikoku[6] = "" + eigyou_Data[7];
		// 九州
		ReportImageController.kyusyu[0] = "" + jituzai_Data[8];
		ReportImageController.kyusyu[1] = "" + jitudou_Data[8];
		ReportImageController.kyusyu[2] = "" + soukou_Data[8];
		ReportImageController.kyusyu[3] = "" + jisya_Data[8];
		ReportImageController.kyusyu[4] = "" + jitu_Data[8];
		ReportImageController.kyusyu[5] = "" + riyou_Data[8];
		ReportImageController.kyusyu[6] = "" + eigyou_Data[8];
		// 沖縄
		ReportImageController.okinawa[0] = "" + jituzai_Data[9];
		ReportImageController.okinawa[1] = "" + jitudou_Data[9];
		ReportImageController.okinawa[2] = "" + soukou_Data[9];
		ReportImageController.okinawa[3] = "" + jisya_Data[9];
		ReportImageController.okinawa[4] = "" + jitu_Data[9];
		ReportImageController.okinawa[5] = "" + riyou_Data[9];
		ReportImageController.okinawa[6] = "" + eigyou_Data[9];
		// 合計
		for (int i = 0; i < jituzai_Data.length; i++) {
			ReportImageController.goukei[0] = ReportImageController.goukei[0]
					+ jituzai_Data[i];
		}
		for (int i = 0; i < jitudou_Data.length; i++) {
			ReportImageController.goukei[1] = ReportImageController.goukei[1]
					+ jitudou_Data[i];
		}
		for (int i = 0; i < soukou_Data.length; i++) {
			ReportImageController.goukei[2] = ReportImageController.goukei[2]
					+ soukou_Data[i];
		}
		for (int i = 0; i < jisya_Data.length; i++) {
			ReportImageController.goukei[3] = ReportImageController.goukei[3]
					+ jisya_Data[i];
		}
		for (int i = 0; i < jitu_Data.length; i++) {
			ReportImageController.goukei[4] = ReportImageController.goukei[4]
					+ jitu_Data[i];
		}
		for (int i = 0; i < riyou_Data.length; i++) {
			ReportImageController.goukei[5] = ReportImageController.goukei[5]
					+ riyou_Data[i];
		}
		for (int i = 0; i < eigyou_Data.length; i++) {
			ReportImageController.goukei[6] = ReportImageController.goukei[6]
					+ eigyou_Data[i];
		}
		tabpane.setId("tabpane");
	}

	private void initCompo() {
		jigyousya.textProperty().addListener(
				e -> {
					ReportImageController.jigyousya = jigyousya.textProperty()
							.getValue();
					ReportImageController.drow();
				});
		adress.textProperty().addListener(e -> {
			ReportImageController.adress = adress.textProperty().getValue();
			ReportImageController.drow();
		});

		business.textProperty().addListener(
				e -> {
					ReportImageController.business = business.textProperty()
							.getValue();
					ReportImageController.drow();
				});
		represent.textProperty().addListener(
				e -> {
					ReportImageController.represent = represent.textProperty()
							.getValue();
					ReportImageController.drow();
				});
		// role.textProperty().addListener(e -> {
		// ReportImageController.role = role.textProperty().getValue();
		// ReporetImageController.drow();
		// });
		tell.textProperty().addListener(e -> {
			ReportImageController.tell = tell.textProperty().getValue();
			ReportImageController.drow();
		});
		employee.textProperty().addListener(
				e -> {
					ReportImageController.employee = employee.textProperty()
							.getValue();
					ReportImageController.drow();
				});
		car.textProperty().addListener(e -> {
			ReportImageController.car = car.textProperty().getValue();
			ReportImageController.drow();
		});
		driver.textProperty().addListener(e -> {
			ReportImageController.driver = driver.textProperty().getValue();
			ReportImageController.drow();
		});
		check9_field.textProperty().addListener(
				e -> {
					ReportImageController.check9_field = check9_field
							.textProperty().getValue();
					ReportImageController.drow();
				});
		car_accident.textProperty().addListener(
				e -> {
					ReportImageController.car_accident = car_accident
							.textProperty().getValue();
					ReportImageController.drow();
				});
		heavy_accident.textProperty().addListener(
				e -> {
					ReportImageController.heavy_accident = heavy_accident
							.textProperty().getValue();
					ReportImageController.drow();
				});
		dead.textProperty().addListener(e -> {
			ReportImageController.dead = dead.textProperty().getValue();
			ReportImageController.drow();
		});
		injuries.textProperty().addListener(
				e -> {
					ReportImageController.injuries = injuries.textProperty()
							.getValue();
					ReportImageController.drow();
				});
		ippan.setOnAction(e -> {
			if (!ReportImageController.ippan) {
				ReportImageController.ippan = true;
			} else {
				ReportImageController.ippan = false;
			}
			ReportImageController.drow();
		});
		tokuseki.setOnAction(e -> {
			if (!ReportImageController.tokuseki) {
				ReportImageController.tokuseki = true;
			} else {
				ReportImageController.tokuseki = false;
			}
			ReportImageController.drow();
		});
		riyoubutton.setOnAction(e -> {
			if (!ReportImageController.riyoubutton) {
				ReportImageController.riyoubutton = true;
			} else {
				ReportImageController.riyoubutton = false;
			}
			ReportImageController.drow();
		});
		reikyu.setOnAction(e -> {
			if (!ReportImageController.reikyu) {
				ReportImageController.reikyu = true;
			} else {
				ReportImageController.reikyu = false;
			}
			ReportImageController.drow();
		});
		check1.setOnAction(e -> {
			if (!ReportImageController.check1) {
				ReportImageController.check1 = true;
			} else {
				ReportImageController.check1 = false;
			}
			ReportImageController.drow();
		});
		check2.setOnAction(e -> {
			if (!ReportImageController.check2) {
				ReportImageController.check2 = true;
			} else {
				ReportImageController.check2 = false;
			}
			ReportImageController.drow();
		});
		check3.setOnAction(e -> {
			if (!ReportImageController.check3) {
				ReportImageController.check3 = true;
			} else {
				ReportImageController.check3 = false;
			}
			ReportImageController.drow();
		});
		check4.setOnAction(e -> {
			if (!ReportImageController.check4) {
				ReportImageController.check4 = true;
			} else {
				ReportImageController.check4 = false;
			}
			ReportImageController.drow();
		});
		check5.setOnAction(e -> {
			if (!ReportImageController.check5) {
				ReportImageController.check5 = true;
			} else {
				ReportImageController.check5 = false;
			}
			ReportImageController.drow();
		});
		check6.setOnAction(e -> {
			if (!ReportImageController.check6) {
				ReportImageController.check6 = true;
			} else {
				ReportImageController.check6 = false;
			}
			ReportImageController.drow();
		});
		check7.setOnAction(e -> {
			if (!ReportImageController.check7) {
				ReportImageController.check7 = true;
			} else {
				ReportImageController.check7 = false;
			}
			ReportImageController.drow();
		});
		check8.setOnAction(e -> {
			if (!ReportImageController.check8) {
				ReportImageController.check8 = true;
			} else {
				ReportImageController.check8 = false;
			}
			ReportImageController.drow();
		});
		check9.setOnAction(e -> {
			if (!ReportImageController.check9) {
				ReportImageController.check9 = true;
			} else {
				ReportImageController.check9 = false;
			}
			ReportImageController.drow();
		});
	}

	private void initData() {
		for (int i = 0; i < jituzai_queries.length; i++) {
			try {
				statement = connector.prepareStatement(jituzai_queries[i]);
				resultset = statement.executeQuery();
				while (resultset.next()) {
					jituzai_Data[i] = resultset.getInt(1);
					// System.out.println("jituzai_Data[" + i + "] = "
					// + jituzai_Data[i]);
				}
				statement.close();
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < jitudou_queries.length; i++) {
			try {
				statement = connector.prepareStatement(jitudou_queries[i]);
				statement.setDate(1, preyear);
				statement.setDate(2, afteryear);
				resultset = statement.executeQuery();
				while (resultset.next()) {
					jitudou_Data[i] = resultset.getInt(1);
					// System.out.println("jitudou_Data[" + i + "] = "
					// + jitudou_Data[i]);
				}
				statement.close();
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < soukou_queries.length; i++) {
			try {
				statement = connector.prepareStatement(soukou_queries[i]);
				statement.setDate(1, preyear);
				statement.setDate(2, afteryear);
				resultset = statement.executeQuery();
				while (resultset.next()) {
					soukou_Data[i] = resultset.getInt(1);
					// System.out.println("soukou_Data[" + i + "] = "
					// + soukou_Data[i]);
				}
				statement.close();
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < jisya_queries.length; i++) {
			try {
				statement = connector.prepareStatement(jisya_queries[i]);
				statement.setDate(1, preyear);
				statement.setDate(2, afteryear);
				resultset = statement.executeQuery();
				while (resultset.next()) {
					jisya_Data[i] = resultset.getInt(1);
					// System.out.println("jisya_Data[" + i + "] = "
					// + jisya_Data[i]);
				}
				statement.close();
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < jitu_queries.length; i++) {
			try {
				statement = connector.prepareStatement(jitu_queries[i]);
				statement.setDate(1, preyear);
				statement.setDate(2, afteryear);
				resultset = statement.executeQuery();
				while (resultset.next()) {
					jitu_Data[i] = resultset.getInt(1);
					// System.out
					// .println("jitu_Data[" + i + "] = " + jitu_Data[i]);
				}
				statement.close();
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < riyou_queries.length; i++) {
			try {
				statement = connector.prepareStatement(riyou_queries[i]);
				statement.setDate(1, preyear);
				statement.setDate(2, afteryear);
				resultset = statement.executeQuery();
				while (resultset.next()) {
					riyou_Data[i] = resultset.getInt(1);
					// System.out.println("riyou_Data[" + i + "] = "
					// + riyou_Data[i]);
				}
				statement.close();
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < eigyou_queries.length; i++) {
			try {
				statement = connector.prepareStatement(eigyou_queries[i]);
				statement.setDate(1, preyear);
				statement.setDate(2, afteryear);
				resultset = statement.executeQuery();
				while (resultset.next()) {
					eigyou_Data[i] = resultset.getInt(1);
					// System.out.println("eigyou_Data[" + i + "] = "
					// + eigyou_Data[i]);
				}
				statement.close();
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void initQueries() {
		// 延実在車両数: Query
		jituzai_queries[0] = "select count(no) * 365 from employee  where transport = '北海道'";
		jituzai_queries[1] = "select count(no) * 365 from employee  where transport = '東北'";
		jituzai_queries[2] = "select count(no) * 365 from employee  where transport = '北陸信越'";
		jituzai_queries[3] = "select count(no) * 365 from employee  where transport = '関東'";
		jituzai_queries[4] = "select count(no) * 365 from employee where  transport = '中部'";
		jituzai_queries[5] = "select count(no) * 365 from employee where  transport = '近畿'";
		jituzai_queries[6] = "select count(no) * 365 from employee where  transport = '中国'";
		jituzai_queries[7] = "select count(no) * 365 from employee where  transport = '四国'";
		jituzai_queries[8] = "select count(no) * 365 from employee where  transport = '九州'";
		jituzai_queries[9] = "select count(no) * 365 from employee where  transport = '沖縄'";
		// 延実働車両数: Query
		jitudou_queries[0] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '北海道'";
		jitudou_queries[1] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '東北'";
		jitudou_queries[2] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '北陸信越'";
		jitudou_queries[3] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '関東'";
		jitudou_queries[4] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '中部'";
		jitudou_queries[5] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '近畿'";
		jitudou_queries[6] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '中国'";
		jitudou_queries[7] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '四国'";
		jitudou_queries[8] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '九州'";
		jitudou_queries[9] = "select count(no) * 365 from rundata where date >= ? and date <= ? and transport = '沖縄'";
		// 走行キロ: Query
		soukou_queries[0] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '北海道'";
		soukou_queries[1] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '東北'";
		soukou_queries[2] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '北陸信越'";
		soukou_queries[3] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '関東'";
		soukou_queries[4] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '中部'";
		soukou_queries[5] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '近畿'";
		soukou_queries[6] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '中国'";
		soukou_queries[7] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '四国'";
		soukou_queries[8] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '九州'";
		soukou_queries[9] = "select sum(run) from rundata where date >= ? and date <= ? and transport = '沖縄'";
		// 実車キロ:Query
		jisya_queries[0] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '北海道'";
		jisya_queries[1] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '東北'";
		jisya_queries[2] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '北陸信越'";
		jisya_queries[3] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '関東'";
		jisya_queries[4] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '中部'";
		jisya_queries[5] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '近畿'";
		jisya_queries[6] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '中国'";
		jisya_queries[7] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '四国'";
		jisya_queries[8] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '九州'";
		jisya_queries[9] = "select sum(run) - sum(empty) from rundata where date >= ? and date <= ? and transport = '沖縄'";
		// 実運送: Query
		jitu_queries[0] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '北海道'";
		jitu_queries[1] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '東北'";
		jitu_queries[2] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '北陸信越'";
		jitu_queries[3] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '関東'";
		jitu_queries[4] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '中部'";
		jitu_queries[5] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '近畿'";
		jitu_queries[6] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '中国'";
		jitu_queries[7] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '四国'";
		jitu_queries[8] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '九州'";
		jitu_queries[9] = "select sum(jitu) from salesdata where date >= ? and date <= ? and transport = '沖縄'";
		// 利用運送: Query
		riyou_queries[0] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '北海道'";
		riyou_queries[1] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '東北'";
		riyou_queries[2] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '北陸信越'";
		riyou_queries[3] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '関東'";
		riyou_queries[4] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '中部'";
		riyou_queries[5] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '近畿'";
		riyou_queries[6] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '中国'";
		riyou_queries[7] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '四国'";
		riyou_queries[8] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '九州'";
		riyou_queries[9] = "select sum(riyou) from salesdata where date >= ? and date <= ? and transport = '沖縄'";
		// 営業利益: Query
		eigyou_queries[0] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '北海道'";
		eigyou_queries[1] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '東北'";
		eigyou_queries[2] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '北陸信越'";
		eigyou_queries[3] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '関東'";
		eigyou_queries[4] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '中部'";
		eigyou_queries[5] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '近畿'";
		eigyou_queries[6] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '中国'";
		eigyou_queries[7] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '四国'";
		eigyou_queries[8] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '九州'";
		eigyou_queries[9] = "select sum(profit) from salesdata where date >= ? and date <= ? and transport = '沖縄'";

	}

	private void textData(String str) {
		if (str == null) {
			System.out.println("str is Null");
		} else
			switch (str) {
			case "北海道":
				zituzai.setText("" + jituzai_Data[0]);
				zitudou.setText("" + jitudou_Data[0]);
				soukou.setText("" + soukou_Data[0]);
				jisya.setText("" + jisya_Data[0]);
				jitu.setText("" + jitu_Data[0]);
				riyou.setText("" + riyou_Data[0]);
				eigyou.setText("" + eigyou_Data[0]);
				break;
			case "東北":
				zituzai.setText("" + jituzai_Data[1]);
				zitudou.setText("" + jitudou_Data[1]);
				soukou.setText("" + soukou_Data[1]);
				jisya.setText("" + jisya_Data[1]);
				jitu.setText("" + jitu_Data[1]);
				riyou.setText("" + riyou_Data[1]);
				eigyou.setText("" + eigyou_Data[1]);
				break;
			case "北陸信越":
				zituzai.setText("" + jituzai_Data[2]);
				zitudou.setText("" + jitudou_Data[2]);
				soukou.setText("" + soukou_Data[2]);
				jisya.setText("" + jisya_Data[2]);
				jitu.setText("" + jitu_Data[2]);
				riyou.setText("" + riyou_Data[2]);
				eigyou.setText("" + eigyou_Data[2]);
				break;
			case "関東":
				zituzai.setText("" + jituzai_Data[3]);
				zitudou.setText("" + jitudou_Data[3]);
				soukou.setText("" + soukou_Data[3]);
				jisya.setText("" + jisya_Data[3]);
				jitu.setText("" + jitu_Data[3]);
				riyou.setText("" + riyou_Data[3]);
				eigyou.setText("" + eigyou_Data[3]);
				break;
			case "中部":
				zituzai.setText("" + jituzai_Data[4]);
				zitudou.setText("" + jitudou_Data[4]);
				soukou.setText("" + soukou_Data[4]);
				jisya.setText("" + jisya_Data[4]);
				jitu.setText("" + jitu_Data[4]);
				riyou.setText("" + riyou_Data[4]);
				eigyou.setText("" + eigyou_Data[4]);
				break;
			case "近畿":
				zituzai.setText("" + jituzai_Data[5]);
				zitudou.setText("" + jitudou_Data[5]);
				soukou.setText("" + soukou_Data[5]);
				jisya.setText("" + jisya_Data[5]);
				jitu.setText("" + jitu_Data[5]);
				riyou.setText("" + riyou_Data[5]);
				eigyou.setText("" + eigyou_Data[5]);
				break;
			case "中国":
				zituzai.setText("" + jituzai_Data[6]);
				zitudou.setText("" + jitudou_Data[6]);
				soukou.setText("" + soukou_Data[6]);
				jisya.setText("" + jisya_Data[6]);
				jitu.setText("" + jitu_Data[6]);
				riyou.setText("" + riyou_Data[6]);
				eigyou.setText("" + eigyou_Data[6]);
				break;
			case "四国":
				zituzai.setText("" + jituzai_Data[7]);
				zitudou.setText("" + jitudou_Data[7]);
				soukou.setText("" + soukou_Data[7]);
				jisya.setText("" + jisya_Data[7]);
				jitu.setText("" + jitu_Data[7]);
				riyou.setText("" + riyou_Data[7]);
				eigyou.setText("" + eigyou_Data[7]);
				break;
			case "九州":
				zituzai.setText("" + jituzai_Data[8]);
				zitudou.setText("" + jitudou_Data[8]);
				soukou.setText("" + soukou_Data[8]);
				jisya.setText("" + jisya_Data[8]);
				jitu.setText("" + jitu_Data[8]);
				riyou.setText("" + riyou_Data[8]);
				eigyou.setText("" + eigyou_Data[8]);
				break;
			case "沖縄":
				zituzai.setText("" + jituzai_Data[9]);
				zitudou.setText("" + jitudou_Data[9]);
				soukou.setText("" + soukou_Data[9]);
				jisya.setText("" + jisya_Data[9]);
				jitu.setText("" + jitu_Data[9]);
				riyou.setText("" + riyou_Data[9]);
				eigyou.setText("" + eigyou_Data[9]);
				break;
			}
	}
}
