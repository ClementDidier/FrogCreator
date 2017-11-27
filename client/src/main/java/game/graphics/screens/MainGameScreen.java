package game.graphics.screens;

import game.FrogGame;
import game.graphics.GameBatch;

public class MainGameScreen extends AbstractScreen 
{

	public MainGameScreen(FrogGame game) 
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
		this.assetManager.update();
	}

	@Override
	public void load() 
	{
		super.load();
		
		this.isLoaded = true;
	}

	@Override
	public void unload()
	{
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
}
