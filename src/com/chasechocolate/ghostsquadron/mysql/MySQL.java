package com.chasechocolate.ghostsquadron.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class MySQL extends Database { //Credits to -_Husky_- for this class, although I edited it to my liking ;D
	private String username;
	private String database;
	private String password;
	private String hostname;
	private String url;
	private int port;
	private Connection conn = null;

	public MySQL(String hostname, int port, String database, String username, String password){
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		this.url = "jdbc:mysql://"+ this.hostname + ":" + this.port + "/" + this.database;
	}

	public Connection open(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(this.url, this.username, this.password);
			
			return conn;
		} catch(SQLException e){
			Bukkit.getLogger().warning("Could not connect to MySQL server! Reason: " + e.getMessage());
		} catch(ClassNotFoundException e){
			Bukkit.getLogger().warning("JDBC Driver not found!");
		}
		
		return this.conn;
	}

	public boolean checkConnection(){
		if(this.conn != null){
			return true;
		} else {			
			return false;
		}
	}

	public Connection getConn(){
		return this.conn;
	}

	public void closeConnection(Connection c){
		c = null;
	}
}