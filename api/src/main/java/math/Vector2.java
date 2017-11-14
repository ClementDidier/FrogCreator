package math;

public class Vector2<T> 
{
	private T x, y;
	
	public Vector2(T x, T y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setX(T value)
	{
		this.x = value;
	}
	
	public void setY(T value)
	{
		this.y = value;
	}
	
	public T getX()
	{
		return this.x;
	}
	
	public T getY()
	{
		return this.y;
	}
}
