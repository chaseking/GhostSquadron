package com.chasechocolate.ghostsquadron.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.chasechocolate.ghostsquadron.GhostSquadron;

public class StatUtils {
	private static HashMap<String, PlayerStats> playerStats = new HashMap<String, PlayerStats>();
	
	public static PlayerStats getPlayerStats(Player player){
		if(playerStats.containsKey(player.getName())){
			PlayerStats stats = playerStats.get(player.getName());
			
			return stats;
		} else {
			PlayerStats stats = new PlayerStats(player);
			stats.load();
			playerStats.put(player.getName(), stats);
			
			return stats;
		}
	}
	
	public static void pushAllStats(){
		for(Player player : PlayerUtils.getAllActivePlayers()){
			if(playerStats.containsKey(player.getName())){
				PlayerStats stats = playerStats.get(player.getName());
				stats.pushStats();
			} else {
				PlayerStats stats = new PlayerStats(player);
				stats.load();
				playerStats.put(player.getName(), stats);
			}
		}
	}
	
	public static void startStatSender(final GhostSquadron plugin, final long delay){
		new BukkitRunnable(){
			@Override
			public void run(){
				if(plugin.isEnabled()){
					pushAllStats();
				}
			}
		}.runTaskTimerAsynchronously(plugin, delay, delay);
	}
}