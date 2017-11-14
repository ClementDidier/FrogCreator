package plugin;

@FrogPlugin
public interface Plugin 
{
	/**
	 * Obtient le nom du plugin
	 * @return Le nom du plugin
	 */
	String getName();
	
	/**
	 * Obtient le nom de l'auteur du plugin
	 * @return Le nom de l'auteur du plugin
	 */
	String getAuthor();
	
	/**
	 * Obtient la version du plugin
	 * @return La version du plugin
	 */
	String getVersion();
	
	/**
	 * Etape de chargement du plugin
	 */
	void load();
	
	/**
	 * Etape d'execution du plugin
	 * <ul>
	 * 	<li>Lancé après le chargement</li>
	 * </ul>
	 */
	void execute();
	
	/**
	 * Etape de libération des ressources utilisées par le plugin
	 * <ul>
	 * 	<li>Appelé lors de la fin de vie du plugin, après l'avoir préalablement chargé et exécuté</ul>
	 * </ul>
	 */
	void unload();
}
