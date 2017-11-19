package system.objects;

public interface UpdatableObject 
{
	/**
	 * <p><h1>Méthode de mise à jour de l'objet</h1>
	 * Méthode appelée pendant le cycle de mise à jour des entités du moteur<p>
	 * @param delta Le temps passé en seconde(s) depuis le dernier appel de cette méthode
	 */
	public void update(float delta);
}
