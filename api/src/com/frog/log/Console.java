package com.frog.log;

import java.io.IOException;
import java.time.Instant;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.frog.utils.FrogException;

public class Console 
{
	public static boolean DEBUG = true;
	public static boolean LOGS_ENABLED = false;
	
	public final static Console log = new Console();
	private final static String LOGGER_NAME = "frogLogs";
	
	private Logger logger;
	
	public Console()
	{
		this.logger = Logger.getLogger(LOGGER_NAME);
		try {
			this.logger.addHandler(new FileHandler("logs"));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void error(FrogException exception)
	{
		if(DEBUG)
			System.err.println("[" + exception.getStackTrace()[0].getClassName() + "][" + Instant.now() + "] " + exception.getStackTrace().toString());
		
		if(LOGS_ENABLED)
			this.logger.severe(exception.getStackTrace().toString());
	}
}
