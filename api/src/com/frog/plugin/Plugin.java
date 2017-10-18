package com.frog.plugin;

@FrogPlugin
public interface Plugin 
{
	String getName();
	String getAuthor();
	String getVersion();
	
	void load();
	void execute();
	void unload();
}
