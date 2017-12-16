package fr.doctorwho.service;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import fr.doctorwho.commands.LangSelect;
import fr.doctorwho.commands.MoneyCommand;
import fr.doctorwho.commands.RankCommand;
import fr.doctorwho.commands.punish.BanCommand;
import fr.doctorwho.commands.punish.MuteCommand;
import fr.doctorwho.commands.punish.TempBanCommand;
import fr.doctorwho.commands.punish.TempMuteCommand;
import fr.doctorwho.commands.punish.UnBanCommand;
import fr.doctorwho.commands.punish.UnMuteCommand;
import fr.doctorwho.commands.server.HubCommand;
import fr.doctorwho.commands.server.InstanceCommand;
import fr.doctorwho.events.InventoryClick;
import fr.doctorwho.events.PlayerChat;
import fr.doctorwho.events.PlayerJoin;
import fr.doctorwho.events.PlayerQuit;
import fr.doctorwho.file.lang.LangFile;



/** To prepare the connection of the DataBase */
public class API extends JavaPlugin implements PluginMessageListener {

	private static API instance;
	private static Database database;
	private static LangFile lang;
	
	public API() {
		super();
	}

	@Override
	public void onEnable() {
		instance = this;
		
		database = new Database();
		database.connect();
		
		registerListener();
		registerCommand();
		
		lang = new LangFile();
		lang.load();
		
		if(Bukkit.getOnlinePlayers().size() == 0) return;
		
		for(Player player : Bukkit.getOnlinePlayers()){
			PlayerSQL.createAccount(player);
			PlayerSQL playersql = PlayerSQL.getPlayerSQL(player);
			PlayerSQL.playersql.put(player, playersql);
		}
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
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
		getCommand("hub").setExecutor(new HubCommand());
		getCommand("instance").setExecutor(new InstanceCommand());
		getCommand("lang").setExecutor(new LangSelect(this));
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("SomeSubChannel")) {
			// Use the code sample in the 'Response' sections below to read
			// the data.
		}
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

	public static LangFile getLang() {
		return lang;
	}


	
	
	
}
