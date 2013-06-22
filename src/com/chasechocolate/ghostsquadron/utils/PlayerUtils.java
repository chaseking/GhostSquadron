package com.chasechocolate.ghostsquadron.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.TeamColor;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;

public class PlayerUtils {
	@SuppressWarnings("unused")
	private static GhostSquadron plugin;
			
	@SuppressWarnings("static-access")
	public PlayerUtils(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	public static void wipe(Player player){
		PlayerInventory inv = player.getInventory();
		
		inv.clear();
		inv.setArmorContents(null);
		player.setExp(0);
		player.setLevel(0);
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setSaturation(12.8F); //12.8 = saturation for cooked porkchop and beef
		player.setRemainingAir(player.getMaximumAir());
		player.setFireTicks(0);
		
		for(PotionEffect pe : player.getActivePotionEffects()){
			player.removePotionEffect(pe.getType());
		}
	}
	
	public static void sendMessage(CommandSender sender, String msg){
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			if(player.isOnline()){
				player.sendMessage(Localization.CHAT_TITLE + msg);
			}
		} else {
			sender.sendMessage(Localization.CHAT_TITLE + msg);
		}
	}

	public static List<Player> getAllActivePlayers(){
		List<Player> allPlayers = new ArrayList<Player>();
		
		for(Arena arena : ArenaUtils.getAllArenas()){
			for(Player player : arena.getAllPlayers()){
				allPlayers.add(player);
			}
			
			for(String playerName : arena.getQueue().getAllInQueue()){
				Player player = Bukkit.getPlayerExact(playerName);
				
				if(!(allPlayers.contains(player))){
					allPlayers.add(player);
				}
			}
		}
		
		return allPlayers;
	}
	
	public static boolean sameArena(Player player1, Player player2){
		boolean same = ArenaUtils.getPlayerArena(player1).equals(ArenaUtils.getPlayerArena(player2));
		return same;
	}
	
	public static TeamColor getTeam(Player player){
		TeamColor team = null;
		
		if(GameUtils.isInGame(player)){
			Arena arena = ArenaUtils.getPlayerArena(player);
			
			if(arena.getPlayersOnTeam(TeamColor.RED).contains(player)){
				team = TeamColor.RED;
			} else if(arena.getPlayersOnTeam(TeamColor.BLUE).contains(player)){
				team = TeamColor.BLUE;
			}
		}
				
		return team;
	}
	
	public static void sendPacket(Player player, Packet packet){
		PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
		
		conn.sendPacket(packet);
	}
}