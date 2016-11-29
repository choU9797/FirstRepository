package application;



import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.*;
public class RunData {

	private SimpleIntegerProperty no;
	private SimpleStringProperty name;
	private SimpleIntegerProperty rundistance;
	private SimpleIntegerProperty emptydistance;
	private Date date;
	private SimpleStringProperty transport;

	public RunData(SimpleIntegerProperty no, SimpleStringProperty name,
			SimpleIntegerProperty rundistance,
			SimpleIntegerProperty emptydistance, Date date,
			SimpleStringProperty transport) {
		this.no = no;
		this.name = name;
		this.rundistance = rundistance;
		this.emptydistance = emptydistance;
		this.date = date;
		this.transport = transport;
	}

	public Integer getNo() {
		return no.get();
	}

	public String getName() {
		return name.get();
	}
	

	public Integer getRun() {
		return rundistance.get();
	}

	public Integer getExist() {
		return getRun() - emptydistance.get();
	}
	public Integer getEmpty() {
		return emptydistance.get();
	}

	public Date getDate() {
		return date;
	}

	public String getTransport() {
		return transport.get();
	}
}
