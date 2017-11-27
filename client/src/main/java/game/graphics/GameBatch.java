package game.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameBatch extends SpriteBatch
{
	private static float alpha = 1f;
	
	public void draw(Sprite sprite)
    {
        sprite.draw(this, alpha);
    }
	
	public void draw(Texture texture, int x, int y)
	{
		Color color = this.getColor();
		this.setColor(this.getColor().r, this.getColor().g, this.getColor().b, this.getColor().a);
		this.draw(texture, x, y);
		this.setColor(color);
	}

    public void draw(DrawableObject drawable)
    {
        drawable.draw(this);
    }

    public void setAlpha(float a)
    {
        alpha = a;
    }

    public float getAlpha()
    {
        return alpha;
    }
}
