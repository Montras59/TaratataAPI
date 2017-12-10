package fr.doctorwho.commands.server;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.doctorwho.service.API;

public class InstanceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		// Console send Command
		if(!(sender instanceof Player)){
			Bukkit.getConsoleSender().sendMessage("§4Erreur: Vous ne pouvez pas utiliser cette commande ici!");
			return true;
		}
		
		if(args.length != 1 || Integer.parseInt(args[0]) > 3 || Integer.parseInt(args[0]) == 0){
			player.sendMessage("§7/instance <1|2|3>");
			return true;
		}
		
		String serverName = "Instance" + Integer.parseInt(args[0]);
		
		if(player.getServer().getName().equalsIgnoreCase(serverName)){
			player.sendMessage("§4Vous êtes déjà sur le Hub");
			return true;
		}
		
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		 
		 out.writeUTF("Connect");
		 out.writeUTF(serverName);
		 
		 player.sendPluginMessage(API.getInstance(), "BungeeCord", out.toByteArray());
		
		return false;
	}

}
