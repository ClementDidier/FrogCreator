package environment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import environment.map.GameMap;
import environment.map.GameMapChunk;
import math.Vector2;
import utils.FrogException;

public class EnvironmentTest {

	private static final int CHUNK_WIDTH = 10;
	private static final int CHUNK_HEIGHT = 10;
	private GameMap map;
	
	@Before
	public void initialize()
	{
		this.map = new GameMap(32, 32, CHUNK_WIDTH, CHUNK_HEIGHT);
	}
	
	@Test
	public void getEmptyChunk() 
	{
		assertEquals("L'obtention d'un chunk inexistant ne retourne pas null", null, this.map.getChunk(0,  0));
	}
	
	@Test
	public void addChunk1() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, 0, 0);
	}
	
	@Test
	public void addChunk2() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, 1, 10);
		this.map.setChunk(chunk, 3, 5);
		this.map.setChunk(chunk, 10, 10);
		this.map.setChunk(chunk, 13, 9);
	}
	
	@Test(expected=FrogException.class)
	public void addChunkNegativeIndexs1() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, -3, 0);
	}
	
	@Test(expected=FrogException.class)
	public void addChunkNegativeIndexs2() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, -1, -6);
	}
	
	@Test(expected=FrogException.class)
	public void addChunkNegativeIndexs3() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, 0, -12);
	}
	
	@Test
	public void getNotEmptyChunk() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, 1, 1);
		assertEquals("Le chunk obtenu n'est pas le même que celui enregistré", chunk, this.map.getChunk(1,  1));
	}
	
	@Test
	public void getEmptyChunkFromAbsoluteCoordinates() throws FrogException
	{
		Vector2<Integer> v = this.map.getChunkCoordinatesFromAbsLocation(134, 170);
		assertEquals("Le chunk obtenu n'est pas le même que celui enregistré", null, this.map.getChunk(v.getX(), v.getY()));
	}
	
	@Test
	public void getChunkFromNegativeAbsoluteCoordinates() throws FrogException
	{
		Vector2<Integer> v = this.map.getChunkCoordinatesFromAbsLocation(-1, 170);
		assertEquals("Le chunk obtenu n'est pas le même que celui enregistré", null, this.map.getChunk(v.getX(), v.getY()));
	}
	
	@Test
	public void getNotEmptyChunkFromAbsoluteCoordinates() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, 13, 17);
		Vector2<Integer> v = this.map.getChunkCoordinatesFromAbsLocation(134, 170);
		assertEquals("Le chunk obtenu n'est pas le même que celui enregistré", chunk, this.map.getChunk(v.getX(), v.getY()));
		
		GameMapChunk chunk2 = new GameMapChunk(this.map);
		this.map.setChunk(chunk2, 14, 0);
		Vector2<Integer> v2 = this.map.getChunkCoordinatesFromAbsLocation(148, 4);
		assertEquals("Le chunk obtenu n'est pas le même que celui enregistré", chunk2, this.map.getChunk(v2.getX(), v2.getY()));
	}
	
	@Test
	public void setTileFromAbsoluteCoordinates() throws FrogException
	{
		this.map.setChunk(new GameMapChunk(this.map), 1, 1);
		this.map.setTile(0, 10, 4, 3);
	}
	
	@Test
	public void cropMap() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.setChunk(chunk, 1, 10);
		this.map.setChunk(chunk, 3, 5);
		this.map.setChunk(chunk, 10, 11);
		this.map.setChunk(chunk, 13, 9);
		assertEquals("Map ne disposant pas de la bonne largeur courante", 13, this.map.getWidth() - 1);
		assertEquals("Map ne disposant pas de la bonne hauteur courante", 11, this.map.getHeight() - 1);
		
		this.map.setChunk(null, 10, 11);
		this.map.autoCrop();
		assertEquals("Map ne disposant pas de la bonne largeur courante", 13, this.map.getWidth() - 1);
		assertEquals("Map ne disposant pas de la bonne hauteur courante", 10, this.map.getHeight() - 1);
	
		this.map.setChunk(null, 13, 9);
		this.map.autoCrop();
		assertEquals("Map ne disposant pas de la bonne largeur courante", 3, this.map.getWidth() - 1);
		assertEquals("Map ne disposant pas de la bonne hauteur courante", 10, this.map.getHeight() - 1);
	}
	
	@Test(timeout=10000)
	public void BoostTest() throws FrogException
	{
		final int wmax = 10000;
		final int hmax = 10000;
		
		this.map.setChunk(new GameMapChunk(this.map), wmax, hmax);
		this.map.autoCrop();
		this.map.setChunk(null, wmax, hmax);
		this.map.autoCrop();
	}
}
