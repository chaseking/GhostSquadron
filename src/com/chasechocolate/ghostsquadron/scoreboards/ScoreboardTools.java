package com.chasechocolate.ghostsquadron.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.TeamColor;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;

public class ScoreboardTools {	
	private static ScoreboardManager manager;
	
	private static OfflinePlayer RED_TEAM_NAME;
	private static OfflinePlayer BLUE_TEAM_NAME;
	
	@SuppressWarnings("unused")
	private static GhostSquadron plugin;
	
	public static void init(GhostSquadron instance){
		plugin = instance;
		manager = Bukkit.getScoreboardManager();
		RED_TEAM_NAME = Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "Red Team");
		BLUE_TEAM_NAME = Bukkit.getOfflinePlayer(ChatColor.DARK_BLUE + "Blue Team");
	}
	
	public static Scoreboard getBlankScoreboard(){
		Scoreboard board = manager.getNewScoreboard();
		return board;
	}
	
	public static void update(){
		for(Arena arena : ArenaUtils.getAllArenas()){
			Scoreboard board = manager.getNewScoreboard();
			Objective objective = board.registerNewObjective("stats", "dummy");
			Team red = board.registerNewTeam("red");
			Team blue = board.registerNewTeam("blue");
			
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName(ChatColor.GREEN + "Ghost Squadron");
			
			red.setAllowFriendlyFire(true); //Minecraft bug!
			red.setCanSeeFriendlyInvisibles(true);
			red.setPrefix(ChatColor.DARK_RED + "[Red] ");
			red.setSuffix(ChatColor.RESET + "");
			
			blue.setAllowFriendlyFire(true); //Minecraft bug!
			blue.setCanSeeFriendlyInvisibles(true);
			blue.setPrefix(ChatColor.BLUE + "[Blue] ");
			blue.setSuffix(ChatColor.RESET + "");
			
			for(Player player : arena.getPlayersOnTeam(TeamColor.RED)){
				red.addPlayer(player);
			}
			
			for(Player player : arena.getPlayersOnTeam(TeamColor.BLUE)){
				blue.addPlayer(player);
			}
			
			Score redTeamScore = objective.getScore(RED_TEAM_NAME);
			Score blueTeamScore = objective.getScore(BLUE_TEAM_NAME);
			
			redTeamScore.setScore(red.getPlayers().size());
			blueTeamScore.setScore(blue.getPlayers().size());
			
			arena.setScoreboard(board);
			arena.setTeamRed(red);
			arena.setTeamBlue(blue);
		}
	}
}