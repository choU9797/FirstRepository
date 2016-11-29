package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesData {

	private SimpleStringProperty name;
	private SimpleIntegerProperty profit;
	private SimpleIntegerProperty jitu;
	private SimpleIntegerProperty riyou;
	private SimpleStringProperty transport;
	private java.sql.Date date;

	public SalesData(SimpleStringProperty name, SimpleIntegerProperty profit,
			SimpleIntegerProperty jitu, SimpleIntegerProperty riyou,
			SimpleStringProperty transport, java.sql.Date date) {

		this.name = name;
		this.profit = profit;
		this.jitu = jitu;
		this.riyou = riyou;
		this.transport = transport;
		this.date = date;
	}

	public Integer getProfit() {
		return profit.get();
	}
	public Integer getJitu() {
		return jitu.get();
	}
	public Integer getRiyou() {
		return riyou.get();
	}
	public String getName() {
		return name.get();
	}
	public String getTransport() {
		return transport.get();
	}
	public java.sql.Date getDate(){
		return date;
	}

}
