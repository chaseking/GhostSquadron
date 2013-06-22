package com.chasechocolate.ghostsquadron.game.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class ArcherKit extends Kit {
	private ItemStack bow = new ItemStack(Material.BOW);
	private ItemStack arrows = new ItemStack(Material.ARROW, 64);
	
	private PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0);
	private PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
	
	public ArcherKit(){
		super("Archer", new ItemStack(Material.BOW));
	}
	
	@Override
	public void applyKit(Player player){
		PlayerUtils.wipe(player);
		
		PlayerInventory inv = player.getInventory();
		
		inv.setItem(0, bow);
		inv.setItem(9, arrows);
		inv.setItem(10, arrows);
		
		inv.setItem(8, steak);
		
		player.addPotionEffect(strength);
		player.addPotionEffect(resistance);
	}
	
	@Override
	public String[] getDescription(){
		return new String[]{
				"Start with a bow and 2 stacks",
				"of arrows that make players",
				"invisible for 3 seconds."
		};
	}
}