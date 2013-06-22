package com.chasechocolate.ghostsquadron.game.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Kit {
	private String name;
	
	private ItemStack icon;
	protected ItemStack steak = new ItemStack(Material.COOKED_BEEF, 16);
	
	public Kit(String name, ItemStack icon){
		this.name = name;
		this.icon = icon;
	}
	
	public abstract void applyKit(Player player);
	
	public abstract String[] getDescription();
	
	public String getName(){
		return this.name;
	}
	
	public ItemStack getIcon(){
		return this.icon;
	}
}