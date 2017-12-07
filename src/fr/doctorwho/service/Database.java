package fr.doctorwho.service;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import fr.doctorwho.utils.SQLConnection;



/* DataBase management */
public class Database extends SQLConnection implements Runnable{
	
	private static BukkitTask task;
	
	/**
	 * Gets the database information for the connection
	 * @return Map<String, String> Info to database connection
	 */
	@SuppressWarnings("deprecation")
	private static Map<String, String> dbConnector(){	
	    Map<Integer, String> infoToConnection = new HashMap<>();
	    int i = 0;
	    
	    try
	    {
		// get the file information
		for(String line: FileUtils.readLines(new File("dbconnector.txt"))) {
		    
		    // Put the line on a map and increment the key
		    infoToConnection.put(i, line);
		    i++;
		}
	    }
	    catch (IOException e)
	    {
		e.printStackTrace();
	    }

	    // put the values on a new map with a key more understandable
	    Map<String, String> dbConnection = new HashMap<>();
	    dbConnection.put("URL", infoToConnection.get(0));
	    dbConnection.put("HOST", infoToConnection.get(1));
	    dbConnection.put("DATABASE", infoToConnection.get(2));
	    dbConnection.put("USER", infoToConnection.get(3));
	    dbConnection.put("PASSWORD", infoToConnection.get(4));
	    
	    // return the db
	    return dbConnection;	    	    
}
		
	/**
	 * Reconnect to the DB every hour
	 */
	public Database() {
	    super(dbConnector().get("URL"), dbConnector().get("HOST"), dbConnector().get("DATABASE"), dbConnector().get("USER"), dbConnector().get("PASSWORD"));
		 // 20 tick * 60 Seconde * 60 minute
		task = Bukkit.getScheduler().runTaskTimer(API.getInstance(), this, 0, 20 * 60 * 60);
	}
	
	private static void connector() {
	    
	}
	
	
	// reload every hour
	@Override
	public void run() {
		disconnect();
		connect();
	}
	
	
	public int getAllID(String table,String nameID){
		int value = 0;
		try{
			PreparedStatement ps = prepareStatement("SELECT " + nameID + " FROM " + table);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				value = rs.getInt(nameID);
			}
			ps.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return value;
	}
	
	public PreparedStatement prepareStatement(String value){
		try {
			return getConnection().prepareStatement(value);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
