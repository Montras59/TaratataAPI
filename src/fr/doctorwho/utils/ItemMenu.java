package fr.doctorwho.utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

// Create Item
public abstract class ItemMenu 
{
	
	Material material;
	String title;
	
	ItemStack stack;
	ItemMeta meta;
	SkullMeta skull;
	BannerMeta banner;
	
	public ItemMenu(Material material,String title) {
		this.material = material;
		this.title = title;
		
		// Init ItemStack
		stack = new ItemStack(material);
		if(material == Material.SKULL_ITEM){
			skull = (SkullMeta) stack.getItemMeta();
			skull.setDisplayName(title);
			setData(3);
		}
		if(material == Material.BANNER){
			banner = (BannerMeta) stack.getItemMeta();
			banner.setDisplayName(title);
		}
		else{
			meta = stack.getItemMeta();
			meta.setDisplayName(title);
		}
	}
	
	public abstract void use(Player player);
	
	public void setLore(String... lore)
	{
		if(material == Material.SKULL_ITEM) skull.setLore(Arrays.asList(lore));
		if(material == Material.BANNER) banner.setLore(Arrays.asList(lore));
		else meta.setLore(Arrays.asList(lore));
	}
	
	public void setAmount(int amount)
	{
		stack.setAmount(amount);
	}
	
	public void setData(int data)
	{
		stack.setDurability((short) data);
	}
	
	public void setOwner(String player)
	{
		skull.setOwner(player);
	}
	
	public void addEnchant(Enchantment enchant, int level){
		stack.addEnchantment(enchant, level);
	}
	
	public void addFlag(ItemFlag flag)
	{
		if(material == Material.SKULL_ITEM) skull.addItemFlags(flag);
		else meta.addItemFlags(flag);
	}
	
	public ItemStack build()
	{
		if(material == Material.SKULL_ITEM) stack.setItemMeta(skull);
		if(material == Material.BANNER) stack.setItemMeta(banner);
		else stack.setItemMeta(meta);
		return stack;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	public ItemMeta getMeta() {
		return meta;
	}

	public void setMeta(ItemMeta meta) {
		this.meta = meta;
	}

	public SkullMeta getSkull() {
		return skull;
	}

	public void setSkull(SkullMeta skull) {
		this.skull = skull;
	}

	public BannerMeta getBanner() {
		return banner;
	}

	public void setBanner(BannerMeta banner) {
		this.banner = banner;
	}
}
