package game.graphics.screens.effects;

import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ShakeEffect implements ScreenEffect
{
	private float elapsed;
	private float duration;
	private float intensity;
	private Random random;
	private OrthographicCamera camera;
	private Vector3 origin;
	
	public ShakeEffect(float intensity, float duration) {
		this.elapsed = 0;
		this.duration = duration / 1000f;
		this.intensity = intensity;
		this.random = new Random();
	}
	
	@Override
	public void initialize(OrthographicCamera camera) {
		this.camera = camera;
		this.origin = new Vector3(camera.position.x, camera.position.y, 0);
	}

	@Override
	public void update(float delta) 
	{
		this.camera.position.set(this.origin);
		if(this.elapsed < this.duration)
		{
			float shakePower = this.intensity * this.camera.zoom * ((this.duration - this.elapsed) / this.duration);
			float x = (this.random.nextFloat() - 0.5f) * 2 * shakePower;
			float y = (this.random.nextFloat() - 0.5f) * 2 * shakePower;
			this.camera.translate(-x, -y, 0);
			
			this.elapsed += delta;
		}
	}

	@Override
	public boolean isFinished() {
		return this.elapsed >= this.duration;
	}

}
