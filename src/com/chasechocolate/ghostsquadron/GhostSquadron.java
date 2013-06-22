package com.chasechocolate.ghostsquadron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.chasechocolate.ghostsquadron.cmds.CommandManager;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.game.kit.AlchemistKit;
import com.chasechocolate.ghostsquadron.game.kit.ArcherKit;
import com.chasechocolate.ghostsquadron.game.kit.BruiserKit;
import com.chasechocolate.ghostsquadron.game.kit.KitUtils;
import com.chasechocolate.ghostsquadron.game.kit.PyroKit;
import com.chasechocolate.ghostsquadron.game.kit.ScoutKit;
import com.chasechocolate.ghostsquadron.game.kit.SpawnerKit;
import com.chasechocolate.ghostsquadron.game.map.MapUtils;
import com.chasechocolate.ghostsquadron.listeners.EntityDamageListener;
import com.chasechocolate.ghostsquadron.listeners.InventoryClickListener;
import com.chasechocolate.ghostsquadron.listeners.ItemListener;
import com.chasechocolate.ghostsquadron.listeners.NoCommands;
import com.chasechocolate.ghostsquadron.listeners.PlayerDeathListener;
import com.chasechocolate.ghostsquadron.listeners.PlayerInteractListener;
import com.chasechocolate.ghostsquadron.listeners.PlayerJoinLeaveListener;
import com.chasechocolate.ghostsquadron.listeners.ProjectileListener;
import com.chasechocolate.ghostsquadron.listeners.SignChangeListener;
import com.chasechocolate.ghostsquadron.scoreboards.ScoreboardTools;
import com.chasechocolate.ghostsquadron.utils.Config;
import com.chasechocolate.ghostsquadron.utils.Metrics;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;
import com.chasechocolate.ghostsquadron.utils.WorldEditUtils;

public class GhostSquadron extends JavaPlugin {
	public long statsSendDelay;
	
	public List<UUID> droppedItems = new ArrayList<UUID>();
	
	public SortedMap<Integer, String> commandHelp = new TreeMap<Integer, String>();
	
	private static GhostSquadron instance;
	
	public void log(String msg){
		this.getLogger().info(msg);
	}
	
	@Override
	public void onEnable(){
		instance = this;
		
		PluginManager pm = this.getServer().getPluginManager();
		
		if(pm.getPlugin("WorldEdit") != null){
			log("Successfully hooked into WorldEdit!");
		} else {
			this.getLogger().log(Level.WARNING, "Found no WorldEdit!");
			this.getLogger().log(Level.WARNING, "Shutting down Ghost Squadron...");
			pm.disablePlugin(this);
			return;
		}
		
		//Configuration files
		new Config(this);
		Config.createAllFiles();
		
		if(Config.getConfig().getBoolean("metrics.enabled")){
			log("Attempting to setup Metrics...");
			
			try{
				Metrics metrics = new Metrics(this);
				metrics.start();
				
				log("Successfully setup Metics!");
			} catch(IOException e){
				log("Failed to setup Metrics!");
			}
		}
		
		//Initialization
		new MapUtils();
		new PlayerUtils(this);
		new WorldEditUtils(pm);
		
		ScoreboardTools.init(this);
		MapUtils.loadMaps();
		ArenaUtils.loadArenas();
		//SQLUtils.setup(this);
		
		log("Loaded " + MapUtils.getBaseMapsFile().listFiles().length + " maps!");
		log("Loaded " + ArenaUtils.getAllArenas().size() + " arenas!");
		
		for(Arena arena : ArenaUtils.getAllArenas()){
			arena.startCountdown();
		}
		
		commandHelp.put(1, "/ghostsquadron join <arena> - Join an arena with the specified name.");
		//TODO more help messages
		
		//Registering kits
		KitUtils.registerKit(new AlchemistKit());
		KitUtils.registerKit(new ArcherKit());
		KitUtils.registerKit(new BruiserKit());
		KitUtils.registerKit(new PyroKit());
		KitUtils.registerKit(new ScoutKit());
		KitUtils.registerKit(new SpawnerKit());
		
		//Registering events
		pm.registerEvents(new PlayerInteractListener(this), this);
		pm.registerEvents(new NoCommands(this), this);
		pm.registerEvents(new PlayerJoinLeaveListener(this), this);
		pm.registerEvents(new ItemListener(this), this);
		pm.registerEvents(new InventoryClickListener(this), this);
		pm.registerEvents(new SignChangeListener(this), this);
		pm.registerEvents(new EntityDamageListener(this), this);
		pm.registerEvents(new PlayerDeathListener(this), this);
		pm.registerEvents(new ProjectileListener(this), this);
		
		//Registering commands
		this.getCommand("ghostsquadron").setExecutor(new CommandManager(this));
		
		//Start timer to automatically send statistics to database
		//StatUtils.startStatSender(this, statsSendDelay);
		
		log("Enabled!");
	}
	
	@Override
	public void onDisable(){
		log("Ending all arenas...");
		
		for(Arena arena : ArenaUtils.getAllArenas()){
			for(Player player : arena.getAllPlayers()){
				arena.end(true);
				PlayerUtils.sendMessage(player, ChatColor.RED + "You have been teleported and removed from the game because the server is reloading or stopping!");
			}
		}
		
		log("Disabled!");
	}
	
	public static GhostSquadron getInstance(){
		return instance;
	}
}