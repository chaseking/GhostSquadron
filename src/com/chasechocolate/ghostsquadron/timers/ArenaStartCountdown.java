package com.chasechocolate.ghostsquadron.timers;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.arena.Arena;

public class ArenaStartCountdown {
	private Arena arena;
	
	private int startTime;
	
	private BukkitRunnable task;
	
	private GhostSquadron plugin;
	
	public ArenaStartCountdown(Arena arena, int startTime){
		this.arena = arena;
		this.startTime = startTime;
		this.plugin = GhostSquadron.getInstance();
	}
	
	public void startStartCountdown(){
		if(plugin.isEnabled()){
			task = new BukkitRunnable(){			
				int time = startTime;
				
				@Override
				public void run(){
					if(arena.getStatus() == GameStatus.COUNTDOWN){
						if(time == 120){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will start in two minutes. Choose a kit with " + ChatColor.DARK_AQUA + "/ghostsquadron kit" + ChatColor.BLUE + "!");
						} else if(time == 60){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will start in one minute. Choose a kit with " + ChatColor.DARK_AQUA + "/ghostsquadron kit" + ChatColor.BLUE + "!");
						} else if(time == 45 || time == 30 || time == 15 || (time <= 5 && time > 1)){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will start in " + time + " seconds. Choose a kit with " + ChatColor.DARK_AQUA + "/ghostsquadron kit" + ChatColor.BLUE + "!");
						} else if(time == 1){
							arena.broadcastMessage(ChatColor.BLUE + "Arena '" + arena.getName() + "' will start in 1 second. Choose a kit with " + ChatColor.DARK_AQUA + "/ghostsquadron kit" + ChatColor.BLUE + "!");
						} else if(time == 0){
							arena.broadcastMessage(ChatColor.BLUE + "Attempting to start the game...");
							
							//Start game
							arena.start();
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