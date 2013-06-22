package com.chasechocolate.ghostsquadron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class NoCommands implements Listener {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;
	
	public NoCommands(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
		Player player = event.getPlayer();
		String msg = event.getMessage();
		
		if(GameUtils.isInGame(player)){
			Arena arena = ArenaUtils.getPlayerArena(player);
			
			if(arena.getStatus() == GameStatus.INGAME){
				if(!(msg.startsWith("/ghostsquadron"))){
					if(!(player.isOp())){
						event.setCancelled(true);
						PlayerUtils.sendMessage(player, ChatColor.RED + "You may only use Ghost Squadron commands while in-game (e.g. /ghostsquadron leave)!");
					}
				}
			}
		}
	}
}