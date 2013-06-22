package com.chasechocolate.ghostsquadron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class PlayerInteractListener implements Listener {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;
	
	public PlayerInteractListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Action action = event.getAction();
		@SuppressWarnings("unused")
		ItemStack hand = player.getItemInHand();
		
		if(GameUtils.isInGame(player)){
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
				Block block = event.getClickedBlock();
				if(block.getState() instanceof Sign){
					Sign sign = (Sign) block.getState();
					if(sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[MineKart]")){
						if(sign.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "Join")){
							if(ArenaUtils.isArena(ChatColor.stripColor(sign.getLine(2)))){
								Arena arena = ArenaUtils.getArena(sign.getLine(2));
								if(ArenaUtils.getAllArenas().contains(arena)){
									arena.addPlayer(player);
								} else {
									PlayerUtils.sendMessage(player, ChatColor.RED + "The arena '" + sign.getLine(2) + "' is not running!");
								}
							} else {
								PlayerUtils.sendMessage(player, ChatColor.RED + "The arena '" + sign.getLine(2) + "' does not exist!");
							}
						}
					}
				}
			}
			
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
				
			}
		}
	}
}