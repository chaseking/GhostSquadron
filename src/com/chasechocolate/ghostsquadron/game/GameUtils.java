package com.chasechocolate.ghostsquadron.game;

import java.util.Random;

import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.utils.Config;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class GameUtils {
	@SuppressWarnings("unused")
	private static GhostSquadron plugin;
	
	private static Random random = new Random();
		
	@SuppressWarnings("static-access")
	public GameUtils(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	public static boolean isInGame(Player player){
		boolean isInArena = PlayerUtils.getAllActivePlayers().contains(player);
		return isInArena;
	}
	
	public static boolean lobbyExists(){
		if(Config.getLocationsConfig().isConfigurationSection("lobby")){
			return true;
		} else {
			return false;
		}
	}
	
	public static TeamColor getRandomTeamColor(Arena arena){
		boolean randomBoolean = random.nextBoolean();
		TeamColor red = TeamColor.RED;
		TeamColor blue = TeamColor.BLUE;
		int redSize = arena.getPlayersOnTeam(red).size();
		int blueSize = arena.getPlayersOnTeam(blue).size();
		
		if(redSize > blueSize){
			return blue;
		} else if(blueSize > redSize){
			return red;
		} else {
			if(randomBoolean){
				return red;
			} else {
				return blue;
			}
		}
	}
}