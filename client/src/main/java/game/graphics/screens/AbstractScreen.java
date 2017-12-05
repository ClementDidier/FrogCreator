package game.graphics.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;

import application.GameClient;
import game.FrogGame;
import game.graphics.DrawableObject;
import game.graphics.ResizableObject;
import game.graphics.screens.effects.ScreenEffect;
import system.objects.ResourceObject;
import system.objects.UpdatableObject;

public abstract class AbstractScreen implements DrawableObject, ResourceObject, UpdatableObject, ResizableObject
{
	protected FrogGame game;
	protected AssetManager assetManager;
	protected Boolean isLoaded;
	protected List<ScreenEffect> effects;
	
	public AbstractScreen(FrogGame game)
	{
		this.game = game;
		this.assetManager = new AssetManager();
		this.effects = new ArrayList<ScreenEffect>();
	}
	
	public void update(float delta)
	{
		this.assetManager.update();
		this.updateEffects(delta);
	}
	
	public void startEffect(ScreenEffect effect)
	{
		effect.initialize((OrthographicCamera) GameClient.getInstance().getCamera());
		this.effects.add(effect);
	}
	
	private void updateEffects(float delta)
	{
		for(int i = 0; i < this.effects.size(); i++)
		{
			if(this.effects.get(i).isFinished())
			{
				this.effects.remove(i);
				i--;
			}
			else
			{
				this.effects.get(i).update(delta);
			}
		}
	}
}
