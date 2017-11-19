package system.events;

import utils.FrogException;

public interface GameEventListener 
{
	/**
	 * Méthode appelée lors de la reception d'un nouvel évènement pris en charge par le système
	 * @param event Le nouvel évènement reçu
	 * @throws FrogException Exception jetée si une erreur survient lors du processus de gestion de l'évènement reçu
	 */
	public void eventReceived(GameEvent event) throws FrogException;
}
