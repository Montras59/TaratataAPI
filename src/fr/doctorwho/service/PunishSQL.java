package fr.doctorwho.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;

// Punish Database
public class PunishSQL {

	private int ID;
	private int userID;
	private String owner;
	private String PunishType;
	private String reason;
	private String end;
	
	private String format = "MMM dd,yyyy HH:mm:ss";
	
	public PunishSQL(int ID,int userID,String owner,String PunishType,String reason,String end) {
		this.ID = ID;
		this.userID = userID;
		this.owner = owner;
		this.PunishType = PunishType;
		this.reason = reason;
		this.end = end;
	}

	
	public PunishSQL() {
		super();
	}
	
	/**
	 * Create Punish
	 * @param targetSQL
	 * @param type
	 * @param reason
	 * @param end
	 */
	public void create(Player owner,PlayerSQL targetSQL,String type,String reason,Date end)
	{
		try
		{
			PreparedStatement p = API.getDatabase().prepareStatement("INSERT INTO punish(id,userID,owner,type,reason,end) VALUES (?,?,?,?,?,?)");
			p.setInt(1, API.getDatabase().getAllID("punish", "ID") + 1);
			p.setInt(2, targetSQL.getID());
			p.setString(3, owner.getName());
			p.setString(4, type);
			p.setString(5, reason);
			p.setString(6, getDate(end.getTime()));
			p.execute();
			p.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Detect IF Player is Punish
	 * @param target
	 * @param type
	 * @return
	 */
	public boolean hasPunish(PlayerSQL target,String type)
	{
		for(int x = 1; x < API.getDatabase().getAllID("punish", "ID") + 1; x++)
		{
			PunishSQL punish = getPunish(x);
			if(punish.getUserID() == target.getID() && punish.getPunishType().equalsIgnoreCase(type)) return true;
		}
		return false;
	}
	
	
	/**
	 * Delete Punish
	 * @param target
	 * @param ID
	 */
	public void delete(int ID)
	{
		try
		{
			PreparedStatement p = API.getDatabase().prepareStatement("DELETE FROM punish WHERE ID = ?");
			p.setInt(1, ID);
			p.executeUpdate();
			p.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get Punish
	 * @param ID
	 * @return
	 */
	public PunishSQL getPunish(int ID){
		PunishSQL punishsql = null;
		try {
			PreparedStatement p = API.getDatabase().prepareStatement("SELECT * FROM punish WHERE id = ?");
			p.setInt(1, ID);
			
			ResultSet rs = p.executeQuery();
			
			while(rs.next())
			{
				punishsql = new PunishSQL(rs.getInt("ID"),rs.getInt("userID"),rs.getString("owner"),rs.getString("type"),rs.getString("reason"),rs.getString("end"));
			}
			
			p.close();
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return punishsql;
	}
	
	/**
	 * Gets End to Millis
	 * @param args
	 * @return
	 */
	public long getEndMillis(String args)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(args);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * Gets Date to String
	 * @param millis
	 * @return
	 */
	public String getDate(long millis)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(millis);
		return sdf.format(date);
	}
	
	// GETTERS AND SETTERS
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPunishType() {
		return PunishType;
	}

	public void setPunishType(String punishType) {
		PunishType = punishType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	
}
