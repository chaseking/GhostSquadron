package com.chasechocolate.ghostsquadron.game.kit;

public enum KitType {
	ARCHER("archer"),
	PYRO("pyro"),
	SCOUT("scout"),
	BRUISER("bruiser"),
	ALCHEMIST("alchemist"),
	SPAWNER("spawner");
	
	private String name;
	
	KitType(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static boolean isKit(String kitName){
		try{
			@SuppressWarnings("unused")
			KitType kit = KitType.valueOf(kitName.toUpperCase());
			return true;
		} catch(IllegalArgumentException e){
			return false;
		}
	}
}