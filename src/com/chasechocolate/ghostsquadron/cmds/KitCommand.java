package com.chasechocolate.ghostsquadron.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.GhostSquadron;
import com.chasechocolate.ghostsquadron.game.GameStatus;
import com.chasechocolate.ghostsquadron.game.GameUtils;
import com.chasechocolate.ghostsquadron.game.arena.Arena;
import com.chasechocolate.ghostsquadron.game.arena.ArenaUtils;
import com.chasechocolate.ghostsquadron.game.kit.KitUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class KitCommand extends GhostSquadronCommand {
	public KitCommand(GhostSquadron plugin){
		super(plugin);
	}
	
	@Override
	public void executeCommand(CommandSender sender, Command cmd, String[] args){
		CommandHelper helper = new CommandHelper(sender, cmd);
		
		//Usage: /ghostsquadron kit
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			if(GameUtils.isInGame(player)){
				Arena arena = ArenaUtils.getPlayerArena(player);
				
				if(arena.getStatus() != GameStatus.INGAME){
					KitUtils.openKitSelectionMenu(player);
				} else {
					PlayerUtils.sendMessage(player, ChatColor.RED + "You may not choose a kit after the game has started!");
				}
			} else {
				PlayerUtils.sendMessage(player, ChatColor.RED + "You are currently not in a game!");
				return;
			}
		} else {
			helper.noConsole();
			return;
		}
	}
}