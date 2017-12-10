package fr.doctorwho.commands.punish;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.doctorwho.enums.EnumPrefix;
import fr.doctorwho.enums.EnumRank;
import fr.doctorwho.service.API;
import fr.doctorwho.service.PlayerSQL;
import fr.doctorwho.service.PunishSQL;

public class UnMuteCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		// Console send Command
		if(!(sender instanceof Player)){
			Bukkit.getConsoleSender().sendMessage("§4Erreur: Vous ne pouvez pas utiliser cette commande ici!");
			return true;
		}
		
		PlayerSQL playersql = PlayerSQL.playersql.get(player);
		
		if(playersql.getRank().getPower() < EnumRank.MODERATEUR.getPower()){
			player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§cVous n'avez pas la permission de l'utiliser");
			return true;
		}
		
		if(args.length == 1){
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			PunishSQL punish = new PunishSQL();
			
			if(target == null){
				player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§cLe joueur est introuvable");
				return true;
			}
			
			if(!punish.hasPunish(PlayerSQL.getPlayerSQL(target.getPlayer()), "mute")){
				player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§cLe joueur n'est pas Mute");
				return true;
			}
			
			punish.delete(getPunish(PlayerSQL.getPlayerSQL(target.getPlayer())).getID());
			
		}else{
			player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§a/unmute <Player>");
		}
		return false;
	}
	
	public PunishSQL getPunish(PlayerSQL sql){
		PunishSQL punish = null;
		for(int x = 1; x < API.getDatabase().getAllID("punish", "ID") + 1; x++)
		{
			punish = punish.getPunish(x);
			if(punish == null) continue;
			
			if(punish.getUserID() == sql.getID() && punish.getPunishType().equalsIgnoreCase("ban")) break;
		}
		return punish;
	}
}
