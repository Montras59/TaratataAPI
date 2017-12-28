package fr.doctorwho.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class PlayerInventoryVirtual {

	public static Map<Player, PlayerInventoryVirtual> playerInventory = new HashMap<>();
	
	private Player player;
	public Map<Integer, ItemMenu> items = new HashMap<>();
	
	public PlayerInventoryVirtual(Player player) {
		this.player = player;
		player.getInventory().clear();
	}
	
	public void open(){
		for(Integer slot : items.keySet()) player.getInventory().setItem(slot, items.get(slot).build());
		
		playerInventory.put(player, this);
		player.updateInventory();
	}
	
	public void addItem(Integer slot,ItemMenu menu){
		items.put(slot, menu);
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ItemMenu getItems(int slot) {
		return items.get(slot);
	}

	public void setItems(Map<Integer, ItemMenu> items) {
		this.items = items;
	}
}
