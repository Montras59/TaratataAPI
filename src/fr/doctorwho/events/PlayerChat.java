package fr.doctorwho.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.doctorwho.enums.EnumPrefix;
import fr.doctorwho.service.API;
import fr.doctorwho.service.PlayerSQL;
import fr.doctorwho.service.PunishSQL;
import fr.doctorwho.utils.DateUnit;

public class PlayerChat implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		PlayerSQL playersql = PlayerSQL.playersql.get(player);
		String message = event.getMessage();
		PunishSQL punish = new PunishSQL();
		
		if(punish.hasPunish(playersql, "mute")){
			for(int x = 1; x < API.getDataBase().getAllID("punish", "ID") + 1; x++)
			{
				punish = punish.getPunish(x);
				if(punish == null) continue;
				
				if(punish.getUserID() == playersql.getID() && punish.getPunishType().equalsIgnoreCase("mute")) break;
			}
			
			if(System.currentTimeMillis() >= punish.getEndMillis(punish.getEnd())){
				punish.delete(punish.getID());
				return;
			}
			player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§cLe joueur " + punish.getOwner() + " vous à mute pour §a" + punish.getReason() + getEndMessage(punish));
			event.setCancelled(true);
		}
		
		event.setFormat(playersql.getRank().getRankPrefix() + player.getName() + " :§f " + message);
	}
	
	
	/**
	 * Gets End
	 * @param sql
	 * @return
	 */
	public String getEndMessage(PunishSQL punish){
		// IF Punish End > 1 year
		if((System.currentTimeMillis() - punish.getEndMillis(punish.getEnd())) > DateUnit.getDate("y", 1)) return "";
		else return "§cet vous serez demute le §a" + punish.getEnd();
	}
}
