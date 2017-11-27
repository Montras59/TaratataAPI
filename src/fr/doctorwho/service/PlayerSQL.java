package fr.doctorwho.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.doctorwho.enums.EnumRank;

public class PlayerSQL{

    	private int ID;
    	private String uuid;
    	private String pseudo;
    	private EnumRank rank;
    	private int coins;	
    	private String quetes;
	// Map contenant le player ainsi que son SQL
	public static Map<Player, PlayerSQL> playersql = new HashMap<Player, PlayerSQL>();

	// Parametric Constructor
	public PlayerSQL(int ID,String uuid, String pseudo, EnumRank rank,int coins,String quetes) {
		this.ID = ID;
		this.uuid = uuid;
		this.pseudo = pseudo;
		this.rank = rank;
		this.coins = coins;
		this.quetes = quetes;
	}
		
	// Non-Parametric Constructor
	public PlayerSQL() {
		super();
	}
	
	
	
	
	// create Player in the DB if does not exist. 
	public static void createAccount(Player player)
	{
		if (hasAccount(player))
			return;
		try {
			PreparedStatement p = API.getDatabase().prepareStatement("INSERT INTO players(userID,uuid,pseudo) VALUES (?,?,?)");
			p.setInt(1, API.getDatabase().getAllID("players","userID") + 1);
			p.setString(2, player.getUniqueId().toString());
			p.setString(3, player.getName());
			p.execute();
			p.close();
		}
		
		catch (SQLException e)
		{
		    e.printStackTrace();
		}	
	}
		
	// Check if Player already exist
	private static boolean hasAccount(Player player) {
		try {
			PreparedStatement p = API.getDatabase().prepareStatement("SELECT uuid FROM players WHERE uuid = ?");
			p.setString(1, player.getUniqueId().toString());
			
			ResultSet rs = p.executeQuery();
			
			String hasAccount = null;
			
			while (rs.next())
			{
			    hasAccount = rs.getString("uuid");
			}
			
			p.close();
			return hasAccount != null;
			}
			catch (SQLException e)
			{		    
			e.printStackTrace();
			}
		
			return false;
		}
	
		// Gets the existing data of the player
		public static PlayerSQL getPlayerSQL(Player player) {
			PlayerSQL playersql = null;
			try {
				PreparedStatement p = API.getDatabase().prepareStatement("SELECT * FROM players WHERE uuid = ?");
				p.setString(1, player.getUniqueId().toString());
				
				ResultSet rs = p.executeQuery();
				
				while(rs.next())
				{
					playersql = new PlayerSQL(rs.getInt("userID"),rs.getString("uuid"),player.getName(),EnumRank.getRank(rs.getInt("rank")), rs.getInt("coins"),rs.getString("quetes"));
				}
				
				p.close();
			}
			
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			return playersql;
		}

		// Update Player in the DB
		public void update() {
		    int num = 1;
		    try {
			PreparedStatement ps = API.getDatabase().prepareStatement("UPDATE players SET pseudo = ?, rank = ?, coins = ?,quetes = ? WHERE uuid = ?");
				
			ps.setString(num++, pseudo);
			ps.setInt(num++, rank.getPower());
			ps.setInt(num++, coins);
			ps.setString(num++, quetes);
			ps.setString(num++, uuid);
			ps.executeUpdate();
			ps.close();
		
		    }
		    
		    catch (SQLException e)
		    {
			e.printStackTrace();
		    }
		}
		
		// Getters & Setters
		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}		

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getPseudo() {
			return pseudo;
		}

		public void setPseudo(String pseudo) {
			this.pseudo = pseudo;
		}

		public EnumRank getRank() {
			return rank;
		}

		public void setRank(EnumRank rank) {
			this.rank = rank;
		}
		
		public void setRank(int power){
			this.rank = EnumRank.getRank(power);
		}

		public int getCoins() {
			return coins;
		}

		public void setCoins(int coins) {
			this.coins = coins;
		}
		
		public void addCoins(int amount){
			this.coins += amount;
		}
		
		public void removeCoins(int amount){
			this.coins -= amount;
			if(coins < 0) this.coins = 0;
		}

		public String getQuetes() {
			return quetes;
		}

		public void setQuetes(String quetes) {
			this.quetes = quetes;
		}
	}
