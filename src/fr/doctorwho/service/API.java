package fr.doctorwho.service;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.doctorwho.commands.BanCommand;
import fr.doctorwho.commands.MoneyCommand;
import fr.doctorwho.commands.MuteCommand;
import fr.doctorwho.commands.RankCommand;
import fr.doctorwho.commands.TempBanCommand;
import fr.doctorwho.commands.TempMuteCommand;
import fr.doctorwho.commands.UnBanCommand;
import fr.doctorwho.commands.UnMuteCommand;
import fr.doctorwho.events.InventoryClick;
import fr.doctorwho.events.PlayerChat;
import fr.doctorwho.events.PlayerJoin;
import fr.doctorwho.events.PlayerQuit;



/** To prepare the connection of the DataBase */
public class API extends JavaPlugin {

	private static API instance;
	private static Database database;
	
	
	public API() {
		super();
	}

	@Override
	public void onEnable() {
		instance = this;
		
		database = new Database();
		
		registerListener();
		registerCommand();
		
		if(Bukkit.getOnlinePlayers().size() == 0) return;
		
		for(Player player : Bukkit.getOnlinePlayers()){
			PlayerSQL.createAccount(player);
			PlayerSQL playersql = PlayerSQL.getPlayerSQL(player);
			PlayerSQL.playersql.put(player, playersql);
		}
	}
	
	@Override
	public void onDisable() {
		if(Bukkit.getOnlinePlayers().size() == 0) return;
		
		for(Player player : Bukkit.getOnlinePlayers()){
			PlayerSQL.playersql.get(player).update();
		}
		
		database.disconnect();
	}
	
	/** Register All Events  */
	public void registerListener(){
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new InventoryClick(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new PlayerChat(), this);
	}
	
	// Getters & Setters
	/**
	 * Register All Command
	 */
	public void registerCommand(){
		getCommand("rank").setExecutor(new RankCommand());
		getCommand("money").setExecutor(new MoneyCommand());
		getCommand("ban").setExecutor(new BanCommand());
		getCommand("tempban").setExecutor(new TempBanCommand());
		getCommand("unban").setExecutor(new UnBanCommand());
		getCommand("mute").setExecutor(new MuteCommand());
		getCommand("tempmute").setExecutor(new TempMuteCommand());
		getCommand("unmute").setExecutor(new UnMuteCommand());
	}
	
	public static API getInstance(){
		return instance;
	}
	
	public static void setInstance(API instance) {
	    API.instance = instance;
	}
		
	public static Database getDatabase() {
	    return database;
	}
	
	public static void setDatabase(Database database) {
	    API.database = database;
	}


	
	
	
}
