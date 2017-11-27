package game.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

import game.FrogGame;
import game.graphics.GameBatch;

public class SplashScreen extends AbstractScreen 
{
	private Sprite logo;
	
	public SplashScreen(FrogGame game) 
	{
		super(game);
	}

	@Override
	public void draw(GameBatch batch) 
	{
		batch.draw(logo);
	}

	@Override
	public void load() 
	{
		super.load();
		this.assetManager.load(Gdx.files.internal("res\\logo.png").path(), Texture.class);
		this.assetManager.finishLoading();
		Texture texture = this.assetManager.get(Gdx.files.internal("res\\logo.png").path(), Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.logo = new Sprite(texture);
		this.isLoaded = true;
	}

	@Override
	public void unload() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) 
	{
		super.update(delta);
		this.logo.setPosition(
				this.game.getViewport().getWorldWidth() / 2f - this.logo.getWidth() / 2f,
				this.game.getViewport().getWorldHeight() / 2f - this.logo.getHeight() / 2f);
	}

	@Override
	public void resize(int width, int height) 
	{
		// TODO Auto-generated method stub

	}
}
