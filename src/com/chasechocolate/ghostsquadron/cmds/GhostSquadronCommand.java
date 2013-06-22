package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.chasechocolate.ghostsquadron.GhostSquadron;

public abstract class GhostSquadronCommand {
	protected GhostSquadron plugin;
	
	public GhostSquadronCommand(GhostSquadron plugin){
		this.plugin = plugin;
	}
	
	public abstract void executeCommand(CommandSender sender, Command cmd, String[] args);
}