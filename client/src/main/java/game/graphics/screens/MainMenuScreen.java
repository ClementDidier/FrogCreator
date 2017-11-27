package game.graphics.screens;

import game.FrogGame;
import game.graphics.GameBatch;

public class MainMenuScreen extends AbstractScreen 
{
	public MainMenuScreen(FrogGame game) 
	{
		super(game);
	}
	
	@Override
	public void draw(GameBatch batch) 
	{
		
	}

	@Override
	public void update(float delta) 
	{
		super.update(delta);
	}

	@Override
	public void load() 
	{
		super.load();
		
		this.isLoaded = true;
	}

	@Override
	public void unload() {
	}

	@Override
	public void resize(int width, int height)
	{
		
	}
}
