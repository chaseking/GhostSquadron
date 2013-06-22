package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.utils.Localization;

public class CommandManager implements CommandExecutor {
	private GhostSquadron plugin;
	
	public CommandManager(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if(args.length == 0){
			sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "-=- Ghost Squadron -=-");
			sender.sendMessage(ChatColor.RED + "Plugin developed by: Nauss (IGN) AKA chasechocolate (Bukkit)");
			sender.sendMessage(ChatColor.RED + "Game created by: SethBling");
			sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.DARK_AQUA + "/ghostsquadron help" + ChatColor.AQUA + " for a list of commands.");
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("help")){
			new HelpCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("join")){
			if(sender.hasPermission(Localization.CMD_JOIN_PERM)){
				if(sender instanceof Player){					
					new JoinCommand(plugin).executeCommand(sender, cmd, args);
					return true;
				} else {
					helper.noConsole();
					return true;
				}
			} else {
				helper.noPermission();
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("leave")){
			if(sender.hasPermission(Localization.CMD_LEAVE_PERM)){
				if(sender instanceof Player){
					new LeaveCommand(plugin).executeCommand(sender, cmd, args);
					return true;
				} else {
					helper.noConsole();
					return true;
				}
			} else {
				helper.noPermission();
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("map")){
			if(sender.hasPermission(Localization.CMD_MAP_PERM)){
				if(sender instanceof Player){
					new MapCommand(plugin).executeCommand(sender, cmd, args);
				} else {
					helper.noConsole();
					return true;
				}
			} else {
				helper.noPermission();
				return true;
			}
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("arena")){
			if(sender.hasPermission(Localization.CMD_ARENA_PERM)){
				if(sender instanceof Player){
					new ArenaCommand(plugin).executeCommand(sender, cmd, args);
					return true;
				} else {
					helper.noConsole();
					return true;
				}
			} else {
				helper.noPermission();
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("setspawn")){
			if(sender.hasPermission(Localization.CMD_SETSPAWN_PERM)){
				if(sender instanceof Player){
					new SetSpawnCommand(plugin).executeCommand(sender, cmd, args);
					return true;
				} else {
					helper.noConsole();
					return true;
				}
			} else {
				helper.noPermission();
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("start")){
			if(sender.hasPermission(Localization.CMD_START_PERM)){
				new StartCommand(plugin).executeCommand(sender, cmd, args);
				return true;
			} else {
				helper.noPermission();
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("end")){
			if(sender.hasPermission(Localization.CMD_END_PERM)){
				new EndCommand(plugin).executeCommand(sender, cmd, args);
				return true;
			} else {
				helper.noPermission();
				return true;
			}
		}
		
		if(args[0].equalsIgnoreCase("kit")){
			new KitCommand(plugin).executeCommand(sender, cmd, args);
			return true;
		}
		
		helper.unknownCommand();
		return true;
	}
}