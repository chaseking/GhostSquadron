package com.chasechocolate.ghostsquadron.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.TeamColor;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.Localization;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class ProjectileListener implements Listener {
	private GhostSquadron plugin;
	
	private List<UUID> revealingIds = new ArrayList<UUID>();
	
	public ProjectileListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event){
		Projectile proj = event.getEntity();
		
		if(proj instanceof ThrownPotion){
			ThrownPotion potion = (ThrownPotion) proj;
			
			if(potion.getShooter() instanceof Player){
				Player shooter = (Player) potion.getShooter();
				
				if(GameUtils.isInGame(shooter)){
					revealingIds.add(potion.getUniqueId());
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event){
		Projectile proj = event.getEntity();
		
		if(proj instanceof ThrownPotion){
			ThrownPotion potion = (ThrownPotion) proj;
			
			if(potion.getShooter() instanceof Player){
				Player shooter = (Player) potion.getShooter();
				
				if(GameUtils.isInGame(shooter)){
					Arena arena = ArenaUtils.getPlayerArena(shooter);
					TeamColor shooterTeam = PlayerUtils.getTeam(shooter);
					
					if(arena.getStatus() == GameStatus.INGAME){
						boolean isRevealingPotion = revealingIds.contains(potion.getUniqueId());
						
						if(isRevealingPotion){
							for(Entity entity : potion.getNearbyEntities(3.5D, 3.5D, 3.5D)){
								if(entity instanceof Player){
									final Player nearbyPlayer = (Player) entity;
									
									if(GameUtils.isInGame(nearbyPlayer)){
										TeamColor nearbyTeam = PlayerUtils.getTeam(nearbyPlayer);
										
										if(!(shooterTeam == nearbyTeam)){
											nearbyPlayer.removePotionEffect(PotionEffectType.INVISIBILITY);
											
											new BukkitRunnable(){
												@Override
												public void run(){
													nearbyPlayer.addPotionEffect(Localization.INVISIBILITY);
												}
											}.runTaskLater(plugin, 60L);
										}
									}
								}
							}
						}
					}
				}
			}
		} else if(proj instanceof Arrow){
			Arrow arrow = (Arrow) proj;
			
			if(arrow.getShooter() instanceof Player){
				Player shooter = (Player) arrow.getShooter();
				
				if(GameUtils.isInGame(shooter)){
					arrow.remove();
				}
			}
		}
	}
}