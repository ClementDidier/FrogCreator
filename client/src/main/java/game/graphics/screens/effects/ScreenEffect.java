package game.graphics.screens.effects;

import com.badlogic.gdx.graphics.OrthographicCamera;

public interface ScreenEffect 
{
	/**
	 * Initialise l'effet
	 * @param camera La caméra de jeu
	 */
	void initialize(OrthographicCamera camera);
	
	/**
	 * Met à jour l'effet
	 * @param delta Le temps écoulé depuis la dernière mise à jour
	 */
	void update(float delta);
	
	/**
	 * Obtient l'état de l'effet
	 * @return True si l'effet est terminé, False dans le cas contraire
	 */
	boolean isFinished();
}
