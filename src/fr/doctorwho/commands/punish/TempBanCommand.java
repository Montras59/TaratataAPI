package fr.doctorwho.commands.punish;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.doctorwho.enums.EnumPrefix;
import fr.doctorwho.enums.EnumRank;
import fr.doctorwho.service.PlayerSQL;
import fr.doctorwho.service.PunishSQL;
import fr.doctorwho.utils.DateUnit;

public class TempBanCommand implements CommandExecutor {

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
		
		if(args.length > 3){
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			String reason = getReason(args);
			long end = System.currentTimeMillis() + DateUnit.getDate(args[2], Integer.parseInt(args[1]));
			Date resultdate = new Date(end);
			
			if(target == null){
				player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§cLe joueur est introuvable");
				return true;
			}
			
			if(target == player){
				player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§cTu ne peux pas t'autoBan");
				return true;
			}
			
			if(System.currentTimeMillis() > end){
				player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§cLa commande ne connait pas le " + args[2]);
				return true;
			}
			
			PunishSQL punish = new PunishSQL();
			
			if(punish.hasPunish(PlayerSQL.getPlayerSQL(target.getPlayer()), "ban")){
				player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§c" + target.getName() + " est déjà banni");
				return true;
			}
			
			punish.create(player, PlayerSQL.getPlayerSQL(target.getPlayer()), "ban", reason, resultdate);
			Bukkit.broadcastMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§a" + player.getName() + " à banni " + target.getName() + " pour " + reason);
			
			if(target.isOnline()){
				Player targetOnline = target.getPlayer();
				targetOnline.kickPlayer("§aVous avez été ban pour:\n" + reason);
			}
			
		}else{
			player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§a/tempban <Player> <number> <date> <Raison>");
		}
		return false;
	}

	/**
	 * Gets Reason
	 * @param args
	 * @return
	 */
	public String getReason(String[] args){
		StringBuilder builder = new StringBuilder();
		for(int x = 3; x < args.length; x++){
			builder.append(args[x] +" ");
		}
		if(builder.toString() == null) return "Aucune Raison";
		else return builder.toString();
	}
}
