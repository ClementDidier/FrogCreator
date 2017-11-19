package math;

public class Vector2<T> 
{
	private T x, y;
	
	/**
	 * Constructeur de vecteur à 2 valeurs
	 * @param x La première valeur
	 * @param y La seconde valeur
	 */
	public Vector2(T x, T y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Met à jour la première valeur du vecteur
	 * @param value La nouvelle valeur
	 */
	public void setX(T value)
	{
		this.x = value;
	}
	
	
	/**
	 * Met à jour la seconde valeur du vecteur
	 * @param value La nouvelle valeur
	 */
	public void setY(T value)
	{
		this.y = value;
	}
	
	/**
	 * Obtient la première valeur du vecteur
	 * @return La première valeur
	 */
	public T getX()
	{
		return this.x;
	}
	
	/**
	 * Obtient la seconde valeur du vecteur
	 * @return La seconde valeur
	 */
	public T getY()
	{
		return this.y;
	}
}
