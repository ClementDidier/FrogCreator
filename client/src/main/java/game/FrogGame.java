package game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.graphics.GameBatch;

public interface FrogGame 
{
	public abstract Viewport getViewport();
	public abstract Camera getCamera();
	public abstract GameBatch getBatch();
}
