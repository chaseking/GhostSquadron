package com.chasechocolate.ghostsquadron.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameUtils;

public class InventoryClickListener implements Listener {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;
	
	public InventoryClickListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getWhoClicked() instanceof Player){
			Player player = (Player) event.getWhoClicked();
			
			if(GameUtils.isInGame(player)){	
				if(event.getSlotType() == SlotType.ARMOR){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "You may not alter your armor contents!");
				}
			}
		}
	}
}