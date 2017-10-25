package localization;

import java.util.HashMap;

import utils.FrogException;

public class Language 
{
	private HashMap<String, String> resources;
	
	public Language(String resourcesFile)
	{
		this.resources = new HashMap<String, String>();
		// TODO : Load resources file
	}
	
	private String getString(String key) throws FrogException
	{
		if(this.resources.containsKey(key))
			throw new FrogException(String.format("The specified key \"\" does not exists in the collection", key));
		
		return this.resources.get(key);
	}
}
