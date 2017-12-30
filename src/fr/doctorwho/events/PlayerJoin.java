package fr.doctorwho.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.doctorwho.bossbar.BossBarPlayer;
import fr.doctorwho.enums.EnumRank;
import fr.doctorwho.service.API;
import fr.doctorwho.service.PlayerSQL;
import fr.doctorwho.service.PunishSQL;

public class PlayerJoin implements Listener {

	@EventHandler(priority= EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		PlayerSQL playersql = PlayerSQL.getPlayerSQL(player);
		
		PunishBan(player, playersql);
		
		if(playersql.getRank() != EnumRank.JOUEUR) event.setJoinMessage(playersql.getRank().getRankPrefix() + " " + player.getName() + " à rejoint le serveur");
		else event.setJoinMessage(null);
		
		new BossBarPlayer(player);
	}
	
	
	/**
	 * Detect IF Player is Punish >> Kick Server
	 * @param player
	 * @param sql
	 */
	public void PunishBan(Player player,PlayerSQL sql)
	{
		PunishSQL punish = new PunishSQL();
		
		// Player isn't Punish
		if(!punish.hasPunish(sql, "ban")) return;
		
		
		for(int x = 1; x < API.getDataBase().getAllID("punish", "ID") + 1; x++)
		{
			punish = punish.getPunish(x);
			if(punish == null) continue;
			
			if(punish.getUserID() == sql.getID() && punish.getPunishType().equalsIgnoreCase("ban")) break;
		}
		
		if(System.currentTimeMillis() >= punish.getEndMillis(punish.getEnd())){
			punish.delete(punish.getID());
			return;
		}
		
		player.kickPlayer("Vous §tes ban");
	}
}
