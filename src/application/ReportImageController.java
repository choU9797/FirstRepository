package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

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
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.FileChooser;

public class ReportImageController implements Initializable {
	static String jigyousya;
	static String adress;
	static String business;
	static String represent;
	static String role;
	static String tell;
	static String employee;
	static String car;
	static String driver;
	static String check9_field;
	static String zituzai;
	static String zitudou;
	static String soukou;
	static String jisya;
	static String jitu;
	static String riyou;
	static String eigyou;
	static String car_accident;
	static String heavy_accident;
	static String dead;
	static String injuries;
	static boolean ippan;
	static boolean tokuseki;
	static boolean riyoubutton;
	static boolean reikyu;
	static boolean check1;
	static boolean check2;
	static boolean check3;
	static boolean check4;
	static boolean check5;
	static boolean check6;
	static boolean check7;
	static boolean check8;
	static boolean check9;
	static String[] hokaido = new String[7];
	static String[] tohoku = new String[7];
	static String[] hokuriku = new String[7];
	static String[] kanto = new String[7];
	static String[] tyubu = new String[7];
	static String[] kinki = new String[7];
	static String[] tyugoku = new String[7];
	static String[] sikoku = new String[7];
	static String[] kyusyu = new String[7];
	static String[] okinawa = new String[7];
	static int[] goukei = new int[7];
	@FXML
	AnchorPane pane;
	@FXML
	ImageView imageview;
	@FXML
	Button saveButton;
	static Canvas canvas = new Canvas(400,600);
	static GraphicsContext gc;
	static Image image = new Image("img/jisseki.jpg");

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gc = canvas.getGraphicsContext2D();
		gc.setFontSmoothingType(FontSmoothingType.LCD);
		gc.setFill(Color.BLACK);
		saveButton.setOnAction(e->{
			saveImage();
		});
		pane.getChildren().addAll(canvas);
		drow();
	}

	// // 印刷の処理
	// public void print() {
	// PrinterJob job = PrinterJob.createPrinterJob();
	// Printer printer = Printer.getDefaultPrinter();
	// PageLayout layout = printer.createPageLayout(Paper.A4,
	// PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
	// job.getJobSettings().setJobName("報告書");
	// job.getJobSettings().setPrintQuality(PrintQuality.HIGH);
	// System.out.println(job);
	// if (job != null) {
	// job.showPrintDialog(Report.stage);
	// boolean success = job.printPage(layout, ReportImage.root);
	// if (success) {
	// job.endJob();
	// }
	// }
	// }
	public void saveImage(){
		WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Save Image");
		filechooser.setInitialFileName("image.png");
		File file = filechooser.showSaveDialog(Report.stage);
		System.out.println("file = "+file);
		if (file != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null),"png",file);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	static void drow() {
		gc.clearRect(0, 0, 400, 600);
		gc.drawImage(image, 0, 0, 400, 600);
		// 特定区分
		// 一般
		if (ippan) {
			gc.strokeOval(60, 50, 43, 9);
		}
		// 特積　
		if (tokuseki) {
			gc.strokeOval(55, 60, 16, 8);
		}
		// 利用
		if (riyoubutton) {
			gc.strokeOval(73, 60, 16, 8);
		}
		// 霊柩
		if (reikyu) {
			gc.strokeOval(90, 60, 16, 8);
		}
		// 事業者番号
		if (jigyousya != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 8.0));
			gc.fillText(jigyousya, 300, 46, 90);
		}
		// 住所
		if (adress != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 8.0));
			gc.fillText(adress, 217, 113, 200);
		}
		// 事業者名
		if (business != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 8.0));
			gc.fillText(business, 217, 122, 200);
		}
		// 代表者名
		if (represent != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 8.0));
			gc.fillText(represent, 217, 131, 200);
		}
		// // 役職及び氏名
		// if (role != null) {
		// gc2.setFont(new javafx.scene.text.Font("Verdana", 8.0));
		// gc2.fillText(role, 217, 140, 200);
		// }
		// 電話番号
		if (tell != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 8.0));
			gc.fillText(tell, 217, 140, 200);
		}
		// 従業員数
		if (employee != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 10.0));
			gc.fillText(employee, 210, 173, 25);
		}
		// 事業用自動車数
		if (car != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 10.0));
			gc.fillText(car, 100, 173, 25);
		}
		// 運転者数
		if (driver != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 10.0));
			gc.fillText(driver, 310, 173, 25);
		}
		// 交通事故件数
		if (car_accident != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 10.0));
			gc.fillText(car_accident, 87, 470, 25);
		}
		// 重大事故件数
		if (heavy_accident != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 10.0));
			gc.fillText(heavy_accident, 174, 470, 25);
		}
		// 死亡者数
		if (dead != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 10.0));
			gc.fillText(dead, 246, 470, 25);
		}
		// 負傷者数
		if (injuries != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 10.0));
			gc.fillText(injuries, 320, 470, 25);
		}
		// その他
		if (check9_field != null) {
			gc.setFont(new javafx.scene.text.Font("Verdana", 9.0));
			gc.fillText(check9_field, 225, 247, 150);
		}
		// ダンプによる土砂等輸送
		if (check1) {
			gc.strokeOval(40, 197, 7, 7);
		}

		// 冷凍、冷蔵輸送
		if (check2) {
			gc.strokeOval(191, 197, 7, 7);
		}
		// 基準緩和認定車両による長大物品輸送
		if (check3) {
			gc.strokeOval(40, 208, 7, 7);
		}
		// 原木、製材輸送
		if (check4) {
			gc.strokeOval(191, 208, 7, 7);
		}
		// 国際海上コンテナ輸送
		if (check5) {
			gc.strokeOval(40, 219, 7, 7);
		}
		// 引っ越し輸送
		if (check6) {
			gc.strokeOval(191, 219, 7, 7);
		}
		// コンクリートミキサー車による生コンクリート輸送
		if (check7) {
			gc.strokeOval(40, 232, 7, 7);
		}
		// 危険物等輸送
		if (check8) {
			gc.strokeOval(40, 253, 7, 7);
		}
		// その他
		if (check9) {
			gc.strokeOval(189, 242, 6, 6);
		}
		// 輸送実績
		gc.setFont(new javafx.scene.text.Font("Verdana", 6.0));
		for (int i = 0; i < hokaido.length; i++) {
			gc.fillText(hokaido[i], 83 + i * 41, 320, 25);
		}
		for (int i = 0; i < tohoku.length; i++) {
			gc.fillText(tohoku[i], 83 + i * 41, 333, 25);
		}
		for (int i = 0; i < hokuriku.length; i++) {
			gc.fillText(hokuriku[i], 83 + i * 41, 345, 25);
		}
		for (int i = 0; i < kanto.length; i++) {
			gc.fillText(kanto[i], 83 + i * 41, 356, 25);
		}
		for (int i = 0; i < tyubu.length; i++) {
			gc.fillText(tyubu[i], 83 + i * 41, 368, 25);
		}
		for (int i = 0; i < kinki.length; i++) {
			gc.fillText(kinki[i], 83 + i * 41, 379, 25);
		}
		for (int i = 0; i < tyugoku.length; i++) {
			gc.fillText(tyugoku[i], 83 + i * 41, 391, 25);
		}
		for (int i = 0; i < sikoku.length; i++) {
			gc.fillText(sikoku[i], 83 + i * 41, 402, 25);
		}
		for (int i = 0; i < kyusyu.length; i++) {
			gc.fillText(kyusyu[i], 83 + i * 41, 415, 25);
		}
		for (int i = 0; i < okinawa.length; i++) {
			gc.fillText(okinawa[i], 83 + i * 41, 425, 25);
		}
		for (int i = 0; i < goukei.length; i++) {
			gc.fillText("" + goukei[i], 83 + i * 41, 437, 25);
		}
		gc.closePath();
	}
}
