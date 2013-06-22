package com.chasechocolate.ghostsquadron.mysql;

import java.sql.Connection;

public abstract class Database { //Credits -_Husky_- for this class, although I edited it to my liking ;D
	protected boolean connected;
	protected Connection conn;
	public int lastUpdate;

	public Database(){
		this.connected = false;
		this.conn = null;
	}

	protected Statements getStatement(String query){
		String trimmedQuery = query.trim();
		
		if(trimmedQuery.substring(0, 6).equalsIgnoreCase("SELECT"))
			return Statements.SELECT;
		
		if(trimmedQuery.substring(0, 6).equalsIgnoreCase("INSERT"))
			return Statements.INSERT;
		
		if(trimmedQuery.substring(0, 6).equalsIgnoreCase("UPDATE"))
			return Statements.UPDATE;
		
		if(trimmedQuery.substring(0, 6).equalsIgnoreCase("DELETE"))
			return Statements.DELETE;
		
		if(trimmedQuery.substring(0, 6).equalsIgnoreCase("CREATE"))
			return Statements.CREATE;
		
		if(trimmedQuery.substring(0, 5).equalsIgnoreCase("ALTER"))
			return Statements.ALTER;
		
		if(trimmedQuery.substring(0, 4).equalsIgnoreCase("DROP"))
			return Statements.DROP;
		
		if(trimmedQuery.substring(0, 8).equalsIgnoreCase("TRUNCATE"))
			return Statements.TRUNCATE;
		
		if(trimmedQuery.substring(0, 6).equalsIgnoreCase("RENAME"))
			return Statements.RENAME;
		
		if(trimmedQuery.substring(0, 2).equalsIgnoreCase("DO"))
			return Statements.DO;
		
		if(trimmedQuery.substring(0, 7).equalsIgnoreCase("REPLACE"))
			return Statements.REPLACE;
		
		if(trimmedQuery.substring(0, 4).equalsIgnoreCase("LOAD"))
			return Statements.LOAD;
		
		if(trimmedQuery.substring(0, 7).equalsIgnoreCase("HANDLER"))
			return Statements.HANDLER;
		
		if(trimmedQuery.substring(0, 4).equalsIgnoreCase("CALL"))
			return Statements.CALL;
		
		return Statements.SELECT;
	}
}