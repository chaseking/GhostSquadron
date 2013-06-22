package com.chasechocolate.ghostsquadron.game.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameQueue;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.TeamColor;
import com.chasechocolate.ghostsquadron.game.kit.BruiserKit;
import com.chasechocolate.ghostsquadron.game.kit.Kit;
import com.chasechocolate.ghostsquadron.game.kit.KitUtils;
import com.chasechocolate.ghostsquadron.game.map.Map;
import com.chasechocolate.ghostsquadron.scoreboards.ScoreboardTools;
import com.chasechocolate.ghostsquadron.timers.ArenaEndCountdown;
import com.chasechocolate.ghostsquadron.timers.ArenaStartCountdown;
import com.chasechocolate.ghostsquadron.utils.Localization;
import com.chasechocolate.ghostsquadron.utils.LocationUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class Arena {
	@SuppressWarnings("unused")
	private GhostSquadron plugin;

	private String name;
	
	private List<String> allPlayers = new ArrayList<String>();
	private List<String> onRed = new ArrayList<String>();
	private List<String> onBlue = new ArrayList<String>();
	
	public static final int MIN = 1;
	public static final int MAX = 16;
	
	private Map map;
	private GameQueue queue;
	private GameStatus status;
	
	private ArenaStartCountdown startCountdown;
	private ArenaEndCountdown endCountdown;
	
	private Scoreboard board;
	private Team redTeam;
	private Team blueTeam;
	
	public Arena(String name, Map map){
		this.plugin = GhostSquadron.getInstance();
		this.name = name;
		this.map = map;
		this.queue = new GameQueue(this);
		this.status = GameStatus.COUNTDOWN;
		
		if(this.map.getConfigFile().exists()){
			this.map.getConfig().set("arena", this.name);
			
			this.map.saveConfig();
		}
	}
	
	public void startCountdown(){
		this.status = GameStatus.COUNTDOWN;
		
		this.startCountdown = new ArenaStartCountdown(this, 120);
		this.startCountdown.startStartCountdown();
	}
	
	public void start(){
		boolean canStart = queue.getAllInQueue().size() >= MIN && map != null;
		
		if(canStart){
			//Set game status
			status = GameStatus.INGAME;
			
			//Clear everything
			allPlayers.clear();
			onRed.clear();
			onBlue.clear();
			
			//Move the players in the queue to the actual game
			for(String playerName : queue.getAllInQueue()){
				allPlayers.add(playerName);
			}
			
			//Choose teams
			chooseTeams();
			
			//Teleport players
			for(Player player : getPlayersOnTeam(TeamColor.RED)){				
				player.teleport(map.getRedSpawn());
				PlayerUtils.sendMessage(player, TeamColor.RED.toChatColor() + "You are on the red team!");
			}
			
			for(Player player : getPlayersOnTeam(TeamColor.BLUE)){
				player.teleport(map.getBlueSpawn());
				PlayerUtils.sendMessage(player, TeamColor.BLUE.toChatColor() + "You are on the blue team!");
			}
			
			//Clear all in queue
			queue.getAllInQueue().clear();
			
			//Scoreboards
			ScoreboardTools.update();
			
			//Give kits
			for(Player player : getAllPlayers()){				
				if(!(KitUtils.hasKit(player))){
					KitUtils.setKit(player, new BruiserKit()); //Bruiser = default kit
				}
				
				Kit kit = KitUtils.getKit(player);
				
				kit.applyKit(player);
				player.addPotionEffect(Localization.INVISIBILITY);
				
				player.getInventory().addItem(new ItemStack(Material.FIREWORK));
				
				Bukkit.getPluginManager().registerEvents(new Listener(){
					@EventHandler
					public void onPlayerInteract(PlayerInteractEvent event){
						Player player = event.getPlayer();
						ItemStack hand = player.getItemInHand();						
						
						if(hand.getType() == Material.FIREWORK){
							event.setCancelled(false);
							player.setVelocity(new Vector(0.0D, 0.0D, 0.0D));
							
							Vector direction = player.getLocation().getDirection();
							player.setVelocity(direction.divide(new Vector(1.1D, 1.1D, 1.1D)).setY(0.95D));
							player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 3);
						}
					}
				}, GhostSquadron.getInstance());
			}
			
			//Inform all players that the game has started, and tell them which map and how many players are playing
			broadcastMessage(ChatColor.AQUA + "Ghost Squadron game started! Map: " + ChatColor.DARK_AQUA + map.getName() + ChatColor.GREEN + " | Players: " + ChatColor.DARK_AQUA + allPlayers.size() + "/" + MAX);
			
			//Start auto-ending timers
			this.endCountdown = new ArenaEndCountdown(this, 600);
			this.endCountdown.startEndCountdown();
		} else {
			broadcastMessage(ChatColor.RED + "Failed to start the game, there must be at least " + MIN + " players in the queue! Restarting countdown!");
			startCountdown();
		}
	}
	
	public void end(boolean force){
		this.status = GameStatus.ENDING;
		
		if(force){
			broadcastMessage(ChatColor.BOLD + "" + ChatColor.RED + "The game has been force ended!");
		}
		
		int redScore = onRed.size();
		int blueScore = onBlue.size();
		
		if(redScore > blueScore){
			broadcastMessage(ChatColor.AQUA + "The " + TeamColor.BLUE.toChatColor() + "blue team" + ChatColor.AQUA + " has won (" + TeamColor.BLUE.toChatColor() + blueScore + ChatColor.AQUA + " to " + TeamColor.RED.toChatColor() + redScore + ChatColor.AQUA + ")!");
		} else if(blueScore > redScore){
			broadcastMessage(ChatColor.AQUA + "The " + TeamColor.RED.toChatColor() + "red team" + ChatColor.AQUA + " has won (" + TeamColor.RED.toChatColor() + redScore + ChatColor.AQUA + " to " + TeamColor.BLUE.toChatColor() + blueScore + ChatColor.AQUA + ")!");
		} else {
			broadcastMessage(ChatColor.AQUA + "The game ends in a " + ChatColor.LIGHT_PURPLE + "draw" + ChatColor.AQUA + " (" + ChatColor.LIGHT_PURPLE + redScore + ChatColor.AQUA + " all)!");
		}
		
		//Teleport the players to the lobby and clear inventories
		for(Player player : getAllPlayers()){
			player.teleport(LocationUtils.getLobbyLoc());
			PlayerUtils.wipe(player);
			queue.addToQueue(player);
		}
		
		//Remove scoreboards
		setScoreboard(ScoreboardTools.getBlankScoreboard());
		
		allPlayers.clear();
		onRed.clear();
		onBlue.clear();
		startCountdown();
	}
	
	public void restart(){
		end(false);
		startCountdown();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void chooseTeams(){
		for(String playerName : queue.getAllInQueue()){
			Player player = Bukkit.getPlayerExact(playerName);
			TeamColor randomTeam = GameUtils.getRandomTeamColor(this);
			
			addPlayerToTeam(player, randomTeam);
		}
	}
	
	public List<Player> getAllPlayers(){
		List<Player> allPlayers = new ArrayList<Player>();
		
		for(String playerName : this.allPlayers){
			allPlayers.add(Bukkit.getPlayerExact(playerName));
		}
		
		return allPlayers;
	}
	
	public boolean isPlaying(Player player){
		boolean isPlaying = allPlayers.contains(player.getName());
		return isPlaying;
	}
	
	public void checkWin(){
		int redSize = onRed.size();
		int blueSize = onBlue.size();
		
		if(redSize == 0){
			broadcastMessage(ChatColor.YELLOW + "The " + TeamColor.BLUE.toChatColor() + "blue" + ChatColor.YELLOW + " team has won!");
			restart();
		} else if(blueSize == 0){
			broadcastMessage(ChatColor.YELLOW + "The " + TeamColor.RED.toChatColor() + "red" + ChatColor.YELLOW + " team has won!");
			restart();
		}
	}
	
	public void addPlayer(Player player){
		if(!(isPlaying(player))){
			if(status == GameStatus.INGAME){
				allPlayers.add(player.getName());
				
				TeamColor team = GameUtils.getRandomTeamColor(this);
				
				if(team == TeamColor.RED){
					onRed.add(player.getName());
				} else if(team == TeamColor.BLUE){
					onBlue.add(player.getName());
				}
			} else {
				if(!(queue.isInQueue(player))){
					queue.addToQueue(player);
					PlayerUtils.sendMessage(player, ChatColor.GREEN + "You have been added to the queue of this arena! You will be teleported when the game starts!");					
				} else {
					PlayerUtils.sendMessage(player, ChatColor.RED + "You are already in the game queue!");
				}
			}
		} else {
			PlayerUtils.sendMessage(player, ChatColor.RED + "You are already playing in the arena!");
		}
	}
	
	public void removePlayer(Player player){
		if(isPlaying(player)){
			this.allPlayers.remove(player);
			PlayerUtils.wipe(player);
			player.setScoreboard(ScoreboardTools.getBlankScoreboard());
			player.teleport(LocationUtils.getLobbyLoc());
		}
		
		if(onRed.contains(player.getName())){
			onRed.remove(player.getName());
		}
		
		if(onBlue.contains(player.getName())){
			onBlue.remove(player.getName());
		}
		
		checkWin();
	}
	
	public void addPlayerToTeam(Player player, TeamColor team){
		if(team == TeamColor.RED){
			onRed.add(player.getName());
		} else if(team == TeamColor.BLUE){
			onBlue.add(player.getName());
		}
	}
	
	public void removePlayerFromTeam(Player player, TeamColor team){
		if(team == TeamColor.RED){
			onRed.remove(player.getName());
		} else if(team == TeamColor.BLUE){
			onBlue.remove(player.getName());
		}
	}
	
	public List<Player> getPlayersOnTeam(TeamColor team){
		List<Player> playersOnTeam = new ArrayList<Player>();
		
		if(team == TeamColor.RED){			
			for(String playerName : onRed){
				Player player = Bukkit.getPlayerExact(playerName);
				playersOnTeam.add(player);
			}	
		} else if(team == TeamColor.BLUE){
			for(String playerName : onBlue){
				Player player = Bukkit.getPlayerExact(playerName);
				playersOnTeam.add(player);
			}
		}
		
		return playersOnTeam;
	}
	
	public void broadcastMessage(String msg){
		for(Player player : getAllPlayers()){
			PlayerUtils.sendMessage(player, msg);
		}
		
		for(String playerName : queue.getAllInQueue()){
			Player player = Bukkit.getPlayerExact(playerName);
			
			PlayerUtils.sendMessage(player, msg);
		}
	}
	
	public Map getMap(){
		return this.map;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
	
	public GameQueue getQueue(){
		return this.queue;
	}
	
	public void setQueue(GameQueue queue){
		this.queue = queue;
	}
	
	public GameStatus getStatus(){
		return this.status;
	}
	
	public void setGameStatus(GameStatus status){
		this.status = status;
	}
	
	public ArenaStartCountdown getStartCountdown(){
		return this.startCountdown;
	}
	
	public void setStartCountdown(ArenaStartCountdown startCountdown){
		this.startCountdown = startCountdown;
	}
	
	public ArenaEndCountdown getEndCountdown(){
		return this.endCountdown;
	}
	
	public void setEndCountdown(ArenaEndCountdown endCountdown){
		this.endCountdown = endCountdown;
	}
	
	public Scoreboard getScoreboard(){
		return this.board;
	}
	
	public void setScoreboard(Scoreboard board){
		this.board = board;
		
		for(Player player : getAllPlayers()){
			player.setScoreboard(board);
		}
	}
	
	public Team getTeamRed(){
		return this.redTeam;
	}
	
	public void setTeamRed(Team redTeam){
		this.redTeam = redTeam;
	}
	
	public Team getTeamBlue(){
		return this.blueTeam;
	}
	
	public void setTeamBlue(Team blueTeam){
		this.blueTeam = blueTeam;
	}
}