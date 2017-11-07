package localization;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import utils.FrogException;

public class I18nProperties 
{
	public static final String DEFAULT_RESOURCES_PATH = System.getProperty("user.dir") + File.separator + "localization";
	public static final String DEFAULT_BUNDLE_NAME = "I18nResources";

	private List<URL> resourcesPaths;
	private ResourceBundle bundle;
	private Locale locale;
	
	public I18nProperties() throws FrogException 
	{
		this.setDefault(Locale.FRENCH);
		this.resourcesPaths = new ArrayList<URL>();
		this.addResourcesPath(DEFAULT_RESOURCES_PATH);
		this.reload();
	}
	
	/**
	 * <p>Définit la langue du gestionnaire de ressources</p>
	 * <ul>
	 *  <li>Un rechargement de l'objet est nécessaire afin de prendre en compte la modification</li>
	 * </ul> 
	 * @param locale La langue que prendra en charge le gestionnaire de ressources
	 */
	public void setDefault(Locale locale)
	{
		this.locale = locale;
	}
	
	/**
	 * <p>Ajoute un chemin vers un nouveau dossier de ressources</p>
	 * <ul>
	 * 	<li>Un rechargement de l'objet est nécessaire afin de prendre en considération ce nouveau repertoire</li> 
	 * </ul>
	 * @param folderPath Le chemin vers le dossier de ressources à ajouter
	 * @throws FrogException Exception jetée lorsque le chemin donné est incorrect
	 */
	public void addResourcesPath(String folderPath) throws FrogException
	{
		File file = new File(folderPath); 
		
		try 
		{
			this.resourcesPaths.add(file.toURI().toURL());
		} 
		catch (MalformedURLException e) 
		{
			throw new FrogException(e.getMessage());
		}
	}
	
	/**
	 * Recharge le gestionnaire de ressources en prenant en compte les chemins de dossiers ressources et la langue
	 */
	public void reload() throws FrogException
	{
		try 
		{
			ClassLoader loader = new URLClassLoader((URL[]) this.resourcesPaths.toArray(new URL[0]));
			this.bundle = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME, this.locale, loader);
		}
		catch (MissingResourceException e)
		{
			throw new FrogException(e.getMessage());
		}
	}
	
	/**	@Test
	public void initializeI18nTest() throws FrogException {
		this.properties = new I18nProperties();
	}
	 * Obtient la langue courante du gestionnaire de ressources
	 * @return La langue courante
	 */
	public Locale getCurrentLocale()
	{
		return this.locale;
	}
	
	
	/**
	 * Obtient la valeur de la ressource définit par la clé donnée, pour la langue actuelle
	 * @param key La clé de la ressource à obtenir
	 * @return La valeur de la ressource recherchée, pour la langue actuelle
	 * @throws FrogException Exception jetée lorsque la clé est inexistante dans le gestionnaire de ressources
	 */
	public String getString(String key) throws FrogException
	{
		if(!this.bundle.containsKey(key))
			throw new FrogException(String.format("The specified key \"\" does not exists in the collection", key));
		
		return this.bundle.getString(key);
	}
}
