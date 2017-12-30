package fr.doctorwho.utils;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class Title{

	private String line;
	private String subLine;
	private Player player;
	
	private int fadeIn = 20;
	private int stay = 20;
	private int fadeOut = 20;

	public Title(String line,String subLine, Player player) {
		this.line = line;
		this.subLine = subLine;
		this.player = player;
	}

	public void sendTitle() {
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\""+line+"\"}"), fadeIn, stay, fadeOut);
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\""+subLine+"\"}"), fadeIn, stay, fadeOut);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(subtitle);
	}
	
	public void setSettings(int fadeIn,int stay,int fadeOut){
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public String getSubLine() {
		return subLine;
	}

	public void setSubLine(String subLine) {
		this.subLine = subLine;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getFadeIn() {
		return fadeIn;
	}

	public void setFadeIn(int fadeIn) {
		this.fadeIn = fadeIn;
	}

	public int getStay() {
		return stay;
	}

	public void setStay(int stay) {
		this.stay = stay;
	}

	public int getFadeOut() {
		return fadeOut;
	}

	public void setFadeOut(int fadeOut) {
		this.fadeOut = fadeOut;
	}
}
