package com.chasechocolate.ghostsquadron.listeners;

import net.minecraft.server.v1_6_R2.Packet205ClientCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.TeamColor;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;
import com.chasechocolate.ghostsquadron.utils.Utilities;

public class PlayerDeathListener implements Listener {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;
	
	public PlayerDeathListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		Player killer = player.getKiller();
		EntityDamageEvent damageEvent = player.getLastDamageCause();
		
		if(GameUtils.isInGame(player)){
			Arena arena = ArenaUtils.getPlayerArena(player);
			TeamColor playerTeam = PlayerUtils.getTeam(player);
			
			arena.removePlayerFromTeam(player, playerTeam);
			event.setDeathMessage(null);
			event.setDroppedExp(0);
			event.getDrops().clear();
			
			String message = null;
			
			if(killer != null){
				TeamColor killerTeam = PlayerUtils.getTeam(killer);
				
				message = killerTeam.toChatColor() + killer.getName() + ChatColor.GRAY + " has killed " + playerTeam.toChatColor() + player.getName() + ChatColor.GRAY + "!";
			} else {
				if(damageEvent instanceof EntityDamageByEntityEvent){
					EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) damageEvent;
					EntityType type = damageByEntityEvent.getDamager().getType();
					String entityName = Utilities.capitalize(type.toString().replaceAll("_", " "));
					
					message = playerTeam.toChatColor() + player.getName() + ChatColor.GRAY + " was killed by " + (Utilities.startsWithVowel(entityName) ? "an" : "a") + " " + ChatColor.GREEN + entityName + ChatColor.GRAY + "!";
				} else {
					message = playerTeam.toChatColor() + player.getName() + ChatColor.GRAY + " was killed!";
				}
			}
			
			PlayerUtils.sendPacket(player, getRespawnPacket());
			arena.checkWin();
			arena.broadcastMessage(message);
		}
	}
	
	private Packet205ClientCommand getRespawnPacket(){
		Packet205ClientCommand packet = new Packet205ClientCommand();
		
		packet.a = (int) 1;
		
		return packet;
	}
}