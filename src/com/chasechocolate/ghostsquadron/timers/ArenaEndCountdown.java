package com.chasechocolate.ghostsquadron.timers;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.arena.Arena;

public class ArenaEndCountdown {
	private Arena arena;
	
	private int length;
	
	private BukkitRunnable task;
	
	private GhostSquadron plugin;
	
	public ArenaEndCountdown(Arena arena, int length){
		this.arena = arena;
		this.length = length;
		this.plugin = GhostSquadron.getInstance();
	}
	
	public void startEndCountdown(){
		if(plugin.isEnabled()){
			task = new BukkitRunnable(){
				int time = length;
				
				@Override
				public void run(){
					if(arena.getStatus() == GameStatus.INGAME){
						if(time == 1200){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 20 minutes.");
						} else if(time == 600){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 10 minutes.");
						} else if(time == 300){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 5 minutes.");
						} else if(time == 240){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 4 minutes.");
						}  else if(time == 180){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 3 minutes.");
						} else if(time == 120){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 2 minutes.");
						} else if(time == 60){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 1 minute.");
						} else if(time == 45 || time == 30 || time == 15 || (time <= 5 && time > 1)){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in " + time + " seconds.");
						} else if(time == 1){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will auto-end in 1 second.");
						} else if(time == 0){
							arena.broadcastMessage(ChatColor.BLUE + "Auto-ending the game on arena '" + arena.getName() + "'...");
							
							//End game
							arena.end(false);
							this.cancel();
							return;
						}
						
						time -= 1;
					} else {
						this.cancel();
						return;
					}
				}
			};
			
			task.runTaskTimer(plugin, 20L, 20L);
		}
	}
	
	public BukkitRunnable getTask(){
		return task;
	}
}