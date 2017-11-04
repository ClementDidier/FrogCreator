package com.frog.localization;

import java.util.Locale;
import java.util.ResourceBundle;

import com.frog.utils.FrogException;

public class I18nProperties 
{
	private static final String DEFAULT_BUNDLE_NAME = "I18nResources";
	
	private ResourceBundle bundle;
	private Locale locale;
	
	public I18nProperties()
	{
		this.setDefault(Locale.FRENCH);
		this.bundle = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME, this.locale);
	}
	
	public void setDefault(Locale locale)
	{
		this.locale = locale;
	}
	
	public String getString(String key) throws FrogException
	{
		if(!this.bundle.containsKey(key))
			throw new FrogException(String.format("The specified key \"\" does not exists in the collection", key));
		
		return this.bundle.getString(key);
	}
}
