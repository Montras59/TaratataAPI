package fr.doctorwho.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

// create Inventory Virtual
public abstract class InventoryVirtual {

	private static Inventory inventory;
	
	private String title;
	private int size;
	private Player player;
	
	public static Map<Integer, ItemMenu> itemPlayer = new HashMap<>();
	
	public InventoryVirtual(String title,int rows,Player player) {
		this.title = title;
		this.size = rows * 9;
		this.player = player;
		
		inventory = Bukkit.createInventory(null, size, title);
	}
	
	/**
	 * Gets All items
	 * @return Map<Integer,ItemVirtual>
	 */
	public abstract Map<Integer,ItemMenu> registerItems();
	
	/**
	 * Open Inventory
	 */
	public void open(){
		// Puts the item in the inventory
		for(Integer slot : registerItems().keySet())
		{
			inventory.setItem(slot, registerItems().get(slot).build());
		}
		
		inventories.put(inventory, this);
		player.openInventory(inventory);
	}
	
	/**
	 * Gets ItemMenu
	 * @param slot
	 * @return
	 */
	public ItemMenu getItems(int slot){
		return registerItems().get(slot);
	}
	
	private static Map<Inventory, InventoryVirtual> inventories = new HashMap<>();
	
	public static InventoryVirtual getVirtualMenu(Inventory inventory)
	{
		return inventories.get(inventory);
	}
	
	/**
	 * Getters and Setters standard
	 */
	
	public static Inventory getInventory() {
		return inventory;
	}

	public static void setInventory(Inventory inventory) {
		InventoryVirtual.inventory = inventory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
