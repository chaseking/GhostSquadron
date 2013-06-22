package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class CommandHelper {
	private CommandSender sender;
	private Command cmd;
	
	public CommandHelper(CommandSender sender, Command cmd){
		this.sender = sender;
		this.cmd = cmd;
	}
	
	public void noPermission(){
		PlayerUtils.sendMessage(sender, ChatColor.RED + "You do not have permission to use this command!");
	}
	
	public void noConsole(){
		PlayerUtils.sendMessage(sender, ChatColor.RED + "This command can only be run in-game!");
	}
	
	public void wrongArguments(){
		PlayerUtils.sendMessage(sender, ChatColor.RED + "Unknown sub-command! Usage: " + cmd.getUsage());	
	}
	
	public void unknownCommand(){
		PlayerUtils.sendMessage(sender, ChatColor.RED + "Unknown command! Use /ghostsquadron help for plugin help/usage!");
	}
}