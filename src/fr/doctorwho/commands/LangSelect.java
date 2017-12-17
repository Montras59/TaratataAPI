package fr.doctorwho.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.doctorwho.service.PlayerSQL;

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
				ps.setLang(1);
				p.sendMessage("§aVous avez choisi la langue française cela à bien été pris en compte.");
				break;
			case "en":
				ps.setLang(2);
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
		p.sendMessage("§6§nLangue §o(Language)§6§n:");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw "+p.getName()+" [\"\",{\"text\":\" \",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/lang fr\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Langue Française\"}},{\"text\":\"-FR:\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/lang fr\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Langue Française\"}},{\"text\":\"Clic sur ce message si tu veux les textes en français.\",\"color\":\"light_purple\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/lang fr\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Langue Française\"}}]");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw "+p.getName()+" [\"\",{\"text\":\" \",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/lang en\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Language English\"}},{\"text\":\"-EN:\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/lang en\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Language English\"}},{\"text\":\"Click on this message if you want texts in English.\",\"color\":\"light_purple\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/lang en\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Language English\"}}]");
	}
	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

}
