package com.chasechocolate.ghostsquadron.listeners;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.Config;
import com.chasechocolate.ghostsquadron.utils.Localization;

public class EntityDamageListener implements Listener {
	private GhostSquadron plugin;
	
	public EntityDamageListener(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			
			if(GameUtils.isInGame(player)){
				Arena arena = ArenaUtils.getPlayerArena(player);
				
				if(arena.getStatus() == GameStatus.INGAME){
					if(!(event.isCancelled())){
						if(Config.getConfig().getBoolean("game.blood-effect.enabled")){
							Location loc = player.getLocation();
							Effect effect = Effect.STEP_SOUND;
							int id = Config.getConfig().getInt("game.blood-effect.block-id");
							
							loc.getWorld().playEffect(loc, effect, id);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player){
			final Player player = (Player) event.getEntity();
			
			if(GameUtils.isInGame(player)){
				Arena arena = ArenaUtils.getPlayerArena(player);
				
				if(arena.getStatus() == GameStatus.INGAME){					
					if(event.getDamager() instanceof Arrow){
						Arrow arrow = (Arrow) event.getDamager();
						
						if(arrow.getShooter() instanceof Player){
							Player shooter = (Player) arrow.getShooter();
							
							if(GameUtils.isInGame(shooter)){
								if(!(event.isCancelled())){
									player.removePotionEffect(PotionEffectType.INVISIBILITY);
									
									new BukkitRunnable(){
										@Override
										public void run(){
											player.addPotionEffect(Localization.INVISIBILITY);
										}
									}.runTaskLater(plugin, 60L);
								}
							}
						}
					}
					
					if(!(event.isCancelled())){
						Location loc = player.getLocation();
						int id = Material.LAVA.getId();
						
						loc.getWorld().playEffect(loc, Effect.STEP_SOUND, id);
					}
				}
			}
		}
	}
}