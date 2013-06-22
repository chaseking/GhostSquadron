package com.chasechocolate.ghostsquadron.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.scheduler.BukkitRunnable;

import com.chasechocolate.ghostsquadron.GhostSquadron;

public class SQLUtils {
	private static GhostSquadron plugin;
	
	private static MySQL sql;
	private static Connection conn;
	
	private static boolean enabled;
	private static boolean connected = false;
	
	public static void setup(final GhostSquadron instance){
		plugin = instance;
		new BukkitRunnable(){
			@Override
			public void run(){
				enabled = plugin.getConfig().getBoolean("mysql.enabled");
				
				String hostname = plugin.getConfig().getString("mysql.hostname");
				int port = plugin.getConfig().getInt("mysql.port");
				String database = plugin.getConfig().getString("mysql.database");
				String username = plugin.getConfig().getString("mysql.username");
				String password = plugin.getConfig().getString("mysql.password");
				
				if(enabled){
					try{
						sql = new MySQL(hostname, port, database, username, password);
						conn = sql.open();
						
						Statement statement = conn.createStatement();
						String createTable = "CREATE TABLE IF NOT EXISTS player_stats(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), username TEXT, total_kills TEXT, total_deaths TEXT, highest_killstreak TEXT, games_played TEXT, times_on_red TEXT, times_on_blue TEXT, time_in_game TEXT);";
						statement.executeUpdate(createTable);
						connected = true;
					} catch(Exception e){
						connected = false;
						plugin.getLogger().severe("Failed to connect to the MySQL database! Please check the database details found in the config.yml file!");
						plugin.getServer().getPluginManager().disablePlugin(plugin);
					}
				}
			}
		}.runTaskAsynchronously(plugin);
	}
	
	public static MySQL getMySQL(){
		return sql;
	}
	
	public static Connection getConnection(){
		return conn;
	}
	
	public static boolean isConnected(){
		return connected;
	}
	
	public static void executeQuery(String query){
		Statement statement;
		ResultSet rs;
		
		try{
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			rs.next();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void executeUpdate(String update){
		Statement statement;
		
		try{
			statement = conn.createStatement();
			statement.executeUpdate(update);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static ResultSet getResultSet(String query){
		if(connected){
			ResultSet rs = null;
			Statement statement;
			
			try{
				statement = conn.createStatement();
				rs = statement.executeQuery(query);
				return rs;
			} catch(SQLException e){
				//Do nothing
			}
			
			return rs;
		} else {
			return null;
		}
	}
}