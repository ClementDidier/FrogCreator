package application;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.FrogGame;
import game.graphics.GameBatch;
import game.graphics.screens.ScreenManager;
import game.graphics.screens.SplashScreen;
import game.graphics.screens.transitions.FadeInTransition;
import game.graphics.screens.transitions.FadeOutTransition;
import math.Vector2;

public class GameClient extends ApplicationAdapter implements FrogGame
{
	public final static Vector2<Integer> VIEWPORT = new Vector2<Integer>(1280, 720);
	private static GameClient INSTANCE;
	
	private GameBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	public static GameClient getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new GameClient();
		return INSTANCE;
	}
	
	@Override
	public void create() 
	{
		this.batch = new GameBatch();
		this.camera = new OrthographicCamera(VIEWPORT.getX(), VIEWPORT.getY());
		this.camera.setToOrtho(false, VIEWPORT.getX(), VIEWPORT.getY());
		this.viewport = new ScreenViewport(this.camera);
		
		ScreenManager.setScreen(new SplashScreen(this), new FadeOutTransition(3000), new FadeInTransition(3000));
	}
	
	@Override
	public void render() 
	{		
		this.camera.update();
		ScreenManager.getCurrentScreen().update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.batch.setProjectionMatrix(this.camera.combined);
		this.batch.begin();
		ScreenManager.getCurrentScreen().draw(this.batch);
		this.batch.end();
	}
	
	@Override
	public void resize (int width, int height) 
	{
		this.viewport.update(width,  height, true);
		ScreenManager.getCurrentScreen().resize(width, height);
	}

	@Override
	public Camera getCamera() 
	{
		return this.camera;
	}

	@Override
	public Viewport getViewport() 
	{
		return this.viewport;
	}

	@Override
	public GameBatch getBatch() 
	{
		return this.batch;
	}
}
