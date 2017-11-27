package game.graphics.screens.transitions;

import application.GameClient;

public class FadeOutTransition implements Transition
{
    private float alpha;
    private float duration;
    private float elapsed;

    public FadeOutTransition(float miliseconds)
    {
        this.duration = miliseconds / 1000;
    }

    @Override
    public void initialize()
    {
        this.alpha = 0f;
        this.elapsed = 0f;
    }

    @Override
    public boolean act(float delta)
    {
        this.elapsed += delta;
        this.alpha = this.elapsed / this.duration;

        if(this.alpha <= 1f)
            GameClient.getInstance().getBatch().setAlpha(this.alpha);

        return this.elapsed < this.duration;
    }
}