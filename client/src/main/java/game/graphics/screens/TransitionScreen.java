package game.graphics.screens;

import com.badlogic.gdx.Gdx;

import game.FrogGame;
import game.graphics.GameBatch;
import game.graphics.screens.transitions.SequenceTransition;
import game.graphics.screens.transitions.Transition;

public class TransitionScreen extends AbstractScreen
{
    private AbstractScreen current;
    private Transition transition;

    public TransitionScreen(FrogGame game, Transition transition1, final AbstractScreen nextScreen, Transition transition2)
    {
    	super(game);
    	this.current = ScreenManager.getCurrentScreen();
    	Gdx.input.setInputProcessor(null);
        ScreenManager.getCurrentScreen();

        this.transition = new SequenceTransition(
	        transition1,
	        new Transition()
	        {
	            @Override public void initialize() { current = nextScreen; if(!current.isLoaded()) current.load(); }
	            @Override public boolean act(float delta) { return false; }
	        },
	        transition2,
	        new Transition()
	        { 
	        	@Override public void initialize() { ScreenManager.setScreen(nextScreen); }
	        	@Override public boolean act(float delta) { return false; }
	        });

        this.transition.initialize();
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
        // Nothing
    }

    @Override
    public void update(float delta)
    {
    	if(this.current != null)
    		this.current.update(delta);
    	this.transition.act(delta);
    }

    @Override
    public void draw(GameBatch batch)
    {
    	if(this.current != null) 
    		this.current.draw(batch);
        //this.current.drawWidgets(batch);
    }

	@Override
	public void resize(int width, int height) {
		
	}

}