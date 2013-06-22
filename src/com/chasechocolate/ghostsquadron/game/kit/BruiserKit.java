package com.chasechocolate.ghostsquadron.game.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class BruiserKit extends Kit {
	private ItemStack sword = new ItemStack(Material.WOOD_SWORD);

	private PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1);
	private PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2);
	
	public BruiserKit(){
		super("Bruiser", new ItemStack(Material.WOOD_SWORD));
	}	
	
	@Override
	public void applyKit(Player player){
		PlayerUtils.wipe(player);
		
		PlayerInventory inv = player.getInventory();
		
		inv.setItem(0, sword);
		
		inv.setItem(8, steak);
		
		player.addPotionEffect(strength);
		player.addPotionEffect(resistance);
	}
	
	@Override
	public String[] getDescription(){
		return new String[]{
				"Start with a wooden sword."
		};
	}
}