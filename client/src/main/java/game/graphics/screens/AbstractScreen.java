package game.graphics.screens;

import com.badlogic.gdx.assets.AssetManager;

import game.FrogGame;
import game.graphics.DrawableObject;
import game.graphics.ResizableObject;
import system.objects.ResourceObject;
import system.objects.UpdatableObject;

public abstract class AbstractScreen implements DrawableObject, ResourceObject, UpdatableObject, ResizableObject
{
	protected FrogGame game;
	protected AssetManager assetManager;
	protected Boolean isLoaded;
	
	public AbstractScreen(FrogGame game)
	{
		this.game = game;
		this.assetManager = new AssetManager();
		this.isLoaded = false;
	}
	
	public void update(float delta)
	{
		this.assetManager.update();
	}
	
	@Override
	public void load()
	{
		if(this.isLoaded())
			return;
	}
	
	public boolean isLoaded()
	{
		return this.isLoaded;
	}
}
