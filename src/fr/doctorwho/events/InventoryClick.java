package fr.doctorwho.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import fr.doctorwho.utils.InventoryVirtual;
import fr.doctorwho.utils.ItemMenu;

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
	    
	    if(InventoryVirtual.getVirtualMenu(inventory) != null &&
		    InventoryVirtual.getVirtualMenu(inventory).getItems(slot) != null)
	    {
			InventoryVirtual.getVirtualMenu(inventory).getItems(slot).use(player);
	    }
	    
	    event.setCancelled(true);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		if(event.getItem() == null) return;
		
		if(ItemMenu.getItemsPlayer().get(event.getItem().getItemMeta().getDisplayName()) == null ||ItemMenu.getItemsPlayer().get(event.getItem().getItemMeta().getDisplayName()).getMaterial() != event.getItem().getType()) return;
		
		ItemMenu.getItemsPlayer().get(event.getItem().getItemMeta().getDisplayName()).use(player);
	}
}
