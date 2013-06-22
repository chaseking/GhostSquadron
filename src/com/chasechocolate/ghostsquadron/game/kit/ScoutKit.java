package com.chasechocolate.ghostsquadron.game.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.chasechocolate.ghostsquadron.utils.ItemUtils;
import com.chasechocolate.ghostsquadron.utils.PlayerUtils;

public class ScoutKit extends Kit {
	private ItemStack stick = new ItemStack(Material.STICK);
	
	private PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0);
	private PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
	private PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
	
	public ScoutKit(){
		super("Scout", new ItemStack(Material.STICK));
		
		stick = ItemUtils.setItemName(stick, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Night Stick");
		stick = ItemUtils.addEnchantment(stick, Enchantment.KNOCKBACK, 2);
	}
	
	@Override
	public void applyKit(Player player){
		PlayerUtils.wipe(player);
		
		PlayerInventory inv = player.getInventory();
		
		inv.setItem(0, stick);
		
		inv.setItem(8, steak);
		
		player.addPotionEffect(strength);
		player.addPotionEffect(resistance);
		player.addPotionEffect(speed);
	}
	
	@Override
	public String[] getDescription(){
		return new String[]{
				"Start with a knockback II stick",
				"and a speed II boost."		
		};
	}
}