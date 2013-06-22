package com.chasechocolate.ghostsquadron.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.chasechocolate.ghostsquadron.game.arena.Arena;

public class GameQueue {
	private Arena arena;
	
	private List<String> queue = new ArrayList<String>();
	
	public GameQueue(Arena arena){
		this.arena = arena;
	}
	
	public Arena getArena(){
		return this.arena;
	}
	
	public List<String> getAllInQueue(){
		return this.queue;
	}
	
	public boolean isInQueue(Player player){
		boolean isInQueue = queue.contains(player.getName());
		return isInQueue;
	}
	
	public void addToQueue(Player player){
		if(!(isInQueue(player))){
			queue.add(player.getName());
		}
	}
	
	public void removeFromQueue(Player player){
		if(isInQueue(player)){
			queue.remove(player.getName());
		}
	}
}