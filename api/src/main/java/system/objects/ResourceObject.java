package system.objects;

public interface ResourceObject 
{
	/**
	 * Charge les ressources de dépendance du composant
	 */
	public void load();
	
	/**
	 * Décharge les ressources utilisées par le composant
	 */
	public void unload();
}
