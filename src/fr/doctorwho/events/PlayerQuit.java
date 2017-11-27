package fr.doctorwho.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.doctorwho.service.PlayerSQL;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		PlayerSQL playersql = PlayerSQL.playersql.get(player);
		playersql.update();
		
		event.setQuitMessage(null);
	}
}
