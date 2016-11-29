package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
	private SimpleIntegerProperty no;
	private SimpleStringProperty name;
	private SimpleStringProperty transport;

	public Employee(SimpleIntegerProperty no, SimpleStringProperty name,
			SimpleStringProperty transport) {
		this.no = no;
		this.name = name;
		this.transport = transport;
	}

	
	public Integer getNo(){
		return no.get();
	}
	public String getName(){
		return name.get();
	}
	public String getTransport(){
		return transport.get();
	}
}
