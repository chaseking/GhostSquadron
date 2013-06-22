package com.chasechocolate.ghostsquadron.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utilities {
	public static void paginate(CommandSender sender, SortedMap<Integer, String> map, int page, int pageLength){ //Credits to gomeow
		sender.sendMessage(ChatColor.YELLOW + "List: Page (" + String.valueOf(page) + " of " + (((map.size() % pageLength) == 0) ? map.size() / pageLength : (map.size() / pageLength) + 1));
		int i = 0;
		int k = 0;
		page--;
		
		for(final Entry<Integer, String> e : map.entrySet()){
			k += 1;
			if((((page * pageLength) + i + 1) == k) && (k != ((page * pageLength) + pageLength + 1))){
				i += 1;
				sender.sendMessage(ChatColor.YELLOW + " - " + e.getValue());
			}
		}
	}
	
	public static boolean isNumber(String text){
		try{
			Integer.parseInt(text);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}
	
	public static boolean startsWithVowel(String text){
		String substring = text.substring(0, 1).toLowerCase();
		List<String> vowels = Arrays.asList("a", "e", "i", "o", "u");
		
		return vowels.contains(substring);
	}
	
	public static int round(int number, int multiple){
		int newNumber = number;
		
		while(newNumber % multiple != 0){
			newNumber += 1;
		}
		
		return newNumber;
	}
	
	public static String capitalize(String text){
		String firstLetter = text.substring(0, 1).toUpperCase();
		String remainingLetters = text.substring(1).toLowerCase();
		String capitalized = firstLetter + remainingLetters;
		
		return capitalized;
	}
}