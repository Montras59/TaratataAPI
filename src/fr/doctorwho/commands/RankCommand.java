package fr.doctorwho.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.doctorwho.enums.EnumPrefix;
import fr.doctorwho.enums.EnumRank;
import fr.doctorwho.service.PlayerSQL;

public class RankCommand implements CommandExecutor {

	/**
	 * Command Executor
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		// Console send Command
		if(!(sender instanceof Player)){
			Bukkit.getConsoleSender().sendMessage("§4Erreur: Vous ne pouvez pas utiliser cette commande ici!");
			return true;
		}
		
		// Detect IF Player is Admin
		if(PlayerSQL.playersql.get(player) == null || PlayerSQL.playersql.get(player).getRank().getPower() < EnumRank.CM.getPower()){
			player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§4Vous n'avez pas la permission de exécuter la commande");
			return true;
		}
		
		if(args.length == 1){
			sendListRank(player);
			return true;
		}
		
		if(args.length == 3){
			if(args[0].equalsIgnoreCase("set")){
				setRankTarget(player,Bukkit.getPlayerExact(args[1]), args[2]);
				return true;
			}
		}
		
		player.sendMessage(new String[]{
				"§7/rank <list>",
				"§7/rank set <Pseudo> <Rank>"
		});
		return false;
	}

	/**
	 * Sets Rank target
	 * @param target
	 */
	public void setRankTarget(Player player,Player target,String rankName){
		if(!hasRankExist(player, rankName)){
			player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§aLe rank " + rankName + " n'existe pas");
			return;
		}
		
		PlayerSQL playersql = PlayerSQL.playersql.get(target);
		if(playersql == null){
			player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§aLe joueur " + target.getName() + " n'est pas en ligne");
			return;
		}
		
		playersql.setRank(EnumRank.getRank(rankName));
		
		player.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§aLe joueur " + target.getName() + " est maintenant " + rankName);
		target.sendMessage(EnumPrefix.DOCTORWHORP.getMessage() + "§aLe joueur " + player.getName() + " vous à mit le grade " + rankName);
	}
	
	/**
	 * Send List Rank
	 * @param player
	 */
	public void sendListRank(Player player){
		for(EnumRank rank : EnumRank.values()){
			if(rank == EnumRank.JOUEUR) player.sendMessage("§7Joueur §f> " + rank.getRankName());
			else player.sendMessage(rank.getRankPrefix() + " §f> " + rank.getRankName());
		}
	}
	
	/**
	 * Detect IF rank exist ELSE send message to player
	 * @param player
	 * @param rankName
	 * @return
	 */
	public boolean hasRankExist(Player player,String rankName){
		for(EnumRank rank : EnumRank.values()){
			if(rank.getRankName().equalsIgnoreCase(rankName)) return true;
		}
		return false;
	}
}
