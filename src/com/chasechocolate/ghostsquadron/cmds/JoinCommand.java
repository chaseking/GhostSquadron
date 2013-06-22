package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class JoinCommand extends GhostSquadronCommand {	
	public JoinCommand(GhostSquadron plugin){
		super(plugin);
	}
	
	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			//Usage: /ghostsquadron join <arena>
			if(args.length == 2){
				String arenaName = args[1];
				if(!(ArenaUtils.isArena(arenaName))){
					PlayerUtils.sendMessage(player, ChatColor.RED + "The specified arena does not exist or is not enabled!");
					return;
				}
				
				if(GameUtils.isInGame(player)){
					PlayerUtils.sendMessage(player, ChatColor.RED + "You are already in a game! You must leave the game to join another!");
					return;
				}
				
				if(!(GameUtils.lobbyExists())){
					PlayerUtils.sendMessage(player, ChatColor.RED + "Failed to join the game! The lobby location hasn't been set yet!");
					return;
				}
				
				Arena arena = ArenaUtils.getArena(arenaName);

				if(arena != null){
					arena.addPlayer(player);
					return;
				}
			} else {
				helper.wrongArguments();
				return;
			}
		} else {
			helper.noConsole();
			return;
		}
	}
}