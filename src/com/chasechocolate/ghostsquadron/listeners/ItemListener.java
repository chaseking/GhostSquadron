package com.chasechocolate.ghostsquadron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;

public class ItemListener implements Listener {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;
	
	public ItemListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		Player player = event.getPlayer();
		
		if(GameUtils.isInGame(player)){
			Arena arena = ArenaUtils.getPlayerArena(player);
			
			if(arena.getStatus() == GameStatus.INGAME){				
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		
		if(GameUtils.isInGame(player)){
			Arena arena = ArenaUtils.getPlayerArena(player);
			
			if(arena.getStatus() == GameStatus.INGAME){
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You may not drop items while in the game!");
			}
		}
	}
}