package game.graphics.screens.transitions;

public interface Transition 
{
	void initialize();
    boolean act(float delta);
}
