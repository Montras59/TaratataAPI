package fr.doctorwho.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import fr.doctorwho.utils.InventoryVirtual;
import fr.doctorwho.utils.PlayerInventoryVirtual;

public class InventoryClick implements Listener {
    

	@EventHandler
	public void onClickEvent(InventoryClickEvent event){
	    if (event.getWhoClicked() == null || event.getClickedInventory() == null)   
	    {
	    	return;
	    }
	    
	    Player player = (Player) event.getWhoClicked();
	    Inventory inventory = event.getClickedInventory();
	    int slot = event.getSlot();
	    
	    if(inventory == null || inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) return;
	    
	    if(InventoryVirtual.getVirtualMenu(inventory) != null &&
		    InventoryVirtual.getVirtualMenu(inventory).getItems(slot) != null)
	    {
			InventoryVirtual.getVirtualMenu(inventory).getItems(slot).use(player);
	    }
	    
	    if(PlayerInventoryVirtual.playerInventory.get(player) != null || PlayerInventoryVirtual.playerInventory.get(player).getItems(slot) != null){
	    	PlayerInventoryVirtual.playerInventory.get(player).getItems(slot).use(player);
	    }
	    
	    if(player.getGameMode() == GameMode.CREATIVE && player.getInventory() == inventory) event.setCancelled(false);
	    else event.setCancelled(true);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		if(event.getItem() == null) return;
		
		if(PlayerInventoryVirtual.playerInventory.get(player) == null || PlayerInventoryVirtual.playerInventory.get(player).getItems(player.getInventory().getHeldItemSlot()) == null) return;
		
		PlayerInventoryVirtual.playerInventory.get(player).getItems(player.getInventory().getHeldItemSlot()).use(player);
	}
}
