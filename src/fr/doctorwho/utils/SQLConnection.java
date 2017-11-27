package fr.doctorwho.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Connection BDD
 */

public class SQLConnection {

	private Connection connection;
	private String urlbase, host, database, user, pass;

	public SQLConnection(String urlBase, String host, String database, String username, String password) {
		this.urlbase = urlBase;
		this.host = host;
		this.database = database;
		this.user = username;
		this.pass = password;
	}

	public void connect() {
		if (!isConnected())
			try {
				this.connection = DriverManager.getConnection(this.urlbase + this.host + "/" + this.database, this.user,this.pass);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
	}

	public void disconnect() {
		if (isConnected())
			try {
				getConnection().close();
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public boolean isConnected() {
		try {
			if ((getConnection() == null) || (getConnection().isClosed()) || (getConnection().isValid(5))) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Connection getConnection() {
		if (this.connection != null) {
			return this.connection;
		}
		return this.connection;
	}
}
