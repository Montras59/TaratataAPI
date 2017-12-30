package fr.doctorwho.utils;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ChatCompenent {

	private TextComponent compenent;
	private String message;
	private Player player;
	
	public ChatCompenent(String message,Player player) {
		this.message = message;
		this.player = player;
		
		compenent = new TextComponent(message);
	}
	
	public void setEvent(ClickEvent.Action action,String value){
		compenent.setClickEvent(new ClickEvent(action, value));
	}
	
	public void setHover(HoverEvent.Action action,String value){
		compenent.setHoverEvent(new HoverEvent(action, new ComponentBuilder(value).create()));
	}
	
	public void addExtras(ChatCompenent value){
		compenent.addExtra(value.compenent);
	}
	
	public void sendMessage(){
		player.spigot().sendMessage(compenent);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
}
