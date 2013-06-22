package com.chasechocolate.ghostsquadron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.game.map.Map;
import com.chasechocolate.ghostsquadron.game.map.MapUtils;
import com.chasechocolate.ghostsquadron.utils.LocationUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;
import com.chasechocolate.ghostsquadron.utils.WorldEditUtils;

public class PlayerJoinLeaveListener implements Listener {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;
	
	public PlayerJoinLeaveListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		if(GameUtils.isInGame(player)){
			Arena arena = ArenaUtils.getPlayerArena(player);
			
			arena.removePlayer(player);
		}
		
		for(Arena arena : ArenaUtils.getAllArenas()){
			arena.getQueue().removeFromQueue(player);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){		
		final Player player = event.getPlayer();
		
		for(Map map : MapUtils.getAllMaps()){			
			if(map != null){
				if(WorldEditUtils.isInMap(player, map)){
					if(!(player.isOp())){
						player.teleport(LocationUtils.getLobbyLoc());
						PlayerUtils.sendMessage(player, ChatColor.RED + "You were teleported to the Ghost Squadron lobby because you joined in a game map!");
					}
				}
			}
		}
	}
}