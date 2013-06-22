package com.chasechocolate.ghostsquadron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.Localization;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class SignChangeListener implements Listener {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;
	
	public SignChangeListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event){
		Player player = event.getPlayer();
		
		if(player.hasPermission(Localization.SIGN_PERM)){
			if(event.getLine(0).equalsIgnoreCase("[MineKart]")){
				if(event.getLine(1).equalsIgnoreCase("Join")){
					if(ArenaUtils.isArena(event.getLine(2))){
						event.setLine(0, ChatColor.GOLD + "[MineKart]");
						event.setLine(1, ChatColor.AQUA + "Join");
						event.setLine(2, ChatColor.GREEN + "" + event.getLine(2));
						PlayerUtils.sendMessage(player, ChatColor.GREEN + "Successfully created a join sign for the arena '" + ChatColor.stripColor(event.getLine(2)) + "'!");
					} else {
						PlayerUtils.sendMessage(player, ChatColor.RED + "Could not find the arena '" + event.getLine(2) + "'!");
					}
				}
			}
		}
	}
}