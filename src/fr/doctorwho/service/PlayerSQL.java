package fr.doctorwho.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.doctorwho.enums.EnumRank;

public class PlayerSQL {

	private int ID;
	private String uuid;
	private String pseudo;
	private EnumRank rank;
	private int coins;
	private String season;
	private String quete;
	private int lang;
	private String friends;
	// Map contenant le player ainsi que son SQL
	public static Map<Player, PlayerSQL> playersql = new HashMap<Player, PlayerSQL>();

	// Parametric Constructor
	public PlayerSQL(int ID, String uuid, String pseudo, EnumRank rank, int coins, String quetes, int lang,
			String friends) {
		this.ID = ID;
		this.uuid = uuid;
		this.pseudo = pseudo;
		this.rank = rank;
		this.coins = coins;
		this.season = quetes.split(",")[0];
		this.quete = quetes.split(",")[1];
		this.setLang(lang);
		this.friends = friends;
	}

	// Non-Parametric Constructor
	public PlayerSQL() {
		super();
	}

	// This function will be execute has the first connection of a player
	private static void firstConnection(Player p) {
		// execute la commande des choix de langue
		p.performCommand("lang");
	}

	// create Player in the DB if does not exist.
	public static void createAccount(Player player) {
		if (hasAccount(player))
			return;
		try {
			PreparedStatement p = API.getDataBase()
					.prepareStatement("INSERT INTO players(userID,uuid,pseudo) VALUES (?,?,?)");
			p.setInt(1, API.getDataBase().getAllID("players", "userID") + 1);
			p.setString(2, player.getUniqueId().toString());
			p.setString(3, player.getName());
			p.execute();
			p.close();
			firstConnection(player);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Check if Player already exist
	private static boolean hasAccount(Player player) {
		try {
			PreparedStatement p = API.getDataBase().prepareStatement("SELECT uuid FROM players WHERE uuid = ?");
			p.setString(1, player.getUniqueId().toString());

			ResultSet rs = p.executeQuery();

			String hasAccount = null;

			while (rs.next()) {
				hasAccount = rs.getString("uuid");
			}

			p.close();
			return hasAccount != null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Gets the existing data of the player
	public static PlayerSQL getPlayerSQL(Player p) {
		PlayerSQL ps = null;
		if (playersql.containsKey(p)) {
			ps = getPlayerSQL_DB(p);
		} else {
			ps = getPlayerSQL_DB(p);
			if (ps == null) {
				createAccount(p);
				ps = getPlayerSQL_DB(p);
			}
		}
		return ps;
	}

	// Gets the existing data of the player
	public static PlayerSQL getPlayerSQL_DB(Player player) {
		PlayerSQL playerSQL = null;
		try {
			PreparedStatement p = API.getDataBase().prepareStatement("SELECT * FROM players WHERE uuid = ?");
			p.setString(1, player.getUniqueId().toString());

			ResultSet rs = p.executeQuery();

			while (rs.next()) {
				playerSQL = new PlayerSQL(rs.getInt("userID"), rs.getString("uuid"), player.getName(),
						EnumRank.getRank(rs.getInt("rank")), rs.getInt("coins"), rs.getString("quetes"),
						rs.getInt("language"), rs.getString("friends"));
				playersql.put(player, playerSQL);
			}

			p.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return playerSQL;
	}

	// Update Player in the DB
	public void update() {
		int num = 1;
		try {
			PreparedStatement ps = API.getDataBase().prepareStatement(
					"UPDATE players SET pseudo = ?, rank = ?, coins = ?,quetes = ?,language = ?,friends = ? WHERE uuid = ?");
			ps.setString(num++, pseudo);
			ps.setInt(num++, rank.getPower());
			ps.setInt(num++, coins);
			ps.setString(num++, season + "," + quete);
			ps.setInt(num++, lang);
			ps.setString(num++, friends);
			ps.setString(num++, uuid);
			ps.executeUpdate();
			ps.close();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// pour savoir si la lang a été select
	public boolean isSelectLang() {
		if (lang == 0) {
			return false;
		}
		return true;
	}

	// Getters & Setters
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
		update();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		update();
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
		update();
	}

	public EnumRank getRank() {
		return rank;
	}

	public void setRank(EnumRank rank) {
		this.rank = rank;
		update();
	}

	public void setRank(int power) {
		this.rank = EnumRank.getRank(power);
		update();
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
		update();
	}

	public void addCoins(int amount) {
		this.coins += amount;
		update();
	}

	public void removeCoins(int amount) {
		this.coins -= amount;
		if (coins < 0)
			this.coins = 0;
		update();
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getQuete() {
		return quete;
	}

	public void setQuete(String quete) {
		this.quete = quete;
	}

	public int getLang() {
		return lang;
	}

	public void setLang(int lang) {
		this.lang = lang;
		update();
	}
	
	public void addFriend(String name){
		if(friends.equals("")) friends = name;
		else friends += "," + name;
	}
	
	public void removeFriend(String name){
		if(friends.contains(name + ",")) friends.replaceAll(name + ",", "");
		else friends.replaceAll(name, "");
	}
	
	public String[] getFriends(){
		return friends.split(",");
	}
}