package application;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import game.graphics.GameBatch;
import game.graphics.GameScreen;

public class GameClient extends ApplicationAdapter
{
	private GameScreen screen;
	private GameBatch batch;
	
	@Override
	public void create() 
	{
		this.batch = new GameBatch();
	}
	
	@Override
	public void render() 
	{
		this.screen.update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.batch.begin();
		this.screen.draw(this.batch);
		this.batch.end();
	}
	
	public void setScreen(GameScreen screen)
	{
		GameScreen oldScreen = this.screen;
		
		this.screen.load();
		this.screen = screen;
	
		if(oldScreen != null)
		{
			oldScreen.unload();
		}
	}
}
