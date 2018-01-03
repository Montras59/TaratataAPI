package fr.doctorwho.bossbar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.doctorwho.service.API;

public class BossBarPlayer implements Runnable{

	public static List<BossBar> bossbars = new ArrayList<>();
	
	private BukkitTask task;
	
	private Player player;
	private BossBar bossbar;
	private int number = 0;
	private double progress = 1;
	
	public BossBarPlayer(Player player) {
		this.player = player;
		this.bossbar = Bukkit.createBossBar(API.getBossbar().getMessage(number), BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
		bossbar.addPlayer(player);
		bossbars.add(bossbar);
		this.task = Bukkit.getScheduler().runTaskTimer(API.getInstance(), this, 0, 1);
	}

	@Override
	public void run() {
		bossbar.setProgress(progress);
		
		if(progress <= 0.01){
			number++;
			if(API.getBossbar().getMessage(number) == null) number = 0;
			bossbar.setTitle(API.getBossbar().getMessage(number));
			progress = 1;
			bossbar.setProgress(progress);
		}
		
		progress -= 0.01;
	}
}
