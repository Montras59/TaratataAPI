package fr.doctorwho.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.doctorwho.service.PlayerSQL;
import fr.doctorwho.utils.ChatCompenent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class LangSelect implements CommandExecutor {
	private Plugin plugin;
	public LangSelect(Plugin plugin) {
		this.setPlugin(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] list) {
		if(!(sender instanceof Player)){
			System.out.println("[ERREUR]:Cette commande est utilisable que par des joueurs!");
			return false;
		}
		Player p = (Player) sender;
		PlayerSQL ps = PlayerSQL.getPlayerSQL(p);
		if(list.length != 1){
			messageTellraw(p);
			return true;
		}
		switch(list[0]){
			case "fr":
				ps.setLang(0);
				p.sendMessage("§aVous avez choisi la langue française cela à bien été pris en compte.");
				break;
			case "en":
				ps.setLang(1);
				p.sendMessage("§aYou chose the English language it in well taken into account.");
				break;
			default:
				messageTellraw(p);
				break;
		}
		return true;
	}
	//message par default
	private void messageTellraw(Player p){
		p.sendMessage("§eVous pouvez sélectionner une Langue dans le menu §4\"Menu Principal\"§e ou en cliquant ci-dessous:");
	
		ChatCompenent fr = new ChatCompenent("§e- §dFrançais/French §e: §aCliquez sur ce message pour avoir les §atextes en français", p);
		fr.setEvent(Action.RUN_COMMAND, "/lang fr");
		fr.sendMessage();
		
		ChatCompenent en = new ChatCompenent("§e- §dAnglais/English §e: §aClick on this message to have the texts in §aEnglish", p);
		en.setEvent(Action.RUN_COMMAND, "/lang en");
		en.sendMessage();
	}
	
	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

}
