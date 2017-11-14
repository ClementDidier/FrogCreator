package environment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import environment.map.GameMap;
import environment.map.GameMapChunk;
import utils.FrogException;

public class EnvironmentTest {

	private GameMap map;
	
	@Before
	public void initialize()
	{
		this.map = new GameMap(32, 32, 10, 10);
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
		this.map.updateChunk(chunk, 0, 0);
	}
	
	@Test
	public void addChunk2() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.updateChunk(chunk, 1, 10);
		this.map.updateChunk(chunk, 3, 5);
		this.map.updateChunk(chunk, 10, 10);
		this.map.updateChunk(chunk, 13, 9);
	}
	
	@Test(expected=FrogException.class)
	public void addChunkNegativeIndexs1() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.updateChunk(chunk, -3, 0);
	}
	
	@Test(expected=FrogException.class)
	public void addChunkNegativeIndexs2() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.updateChunk(chunk, -1, -6);
	}
	
	@Test(expected=FrogException.class)
	public void addChunkNegativeIndexs3() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.updateChunk(chunk, 0, -12);
	}
	
	@Test
	public void getNotEmptyChunk() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.updateChunk(chunk, 1, 1);
		assertEquals("Le chunk obtenu n'est pas le même que celui enregistré", chunk, this.map.getChunk(1,  1));
	}
	
	@Test
	public void cropMap() throws FrogException
	{
		GameMapChunk chunk = new GameMapChunk(this.map);
		this.map.updateChunk(chunk, 1, 10);
		this.map.updateChunk(chunk, 3, 5);
		this.map.updateChunk(chunk, 10, 11);
		this.map.updateChunk(chunk, 13, 9);
		assertEquals("Map ne disposant pas de la bonne largeur courante", 13, this.map.getWidth() - 1);
		assertEquals("Map ne disposant pas de la bonne hauteur courante", 11, this.map.getHeight() - 1);
		
		this.map.updateChunk(null, 10, 11);
		this.map.autoCrop();
		assertEquals("Map ne disposant pas de la bonne largeur courante", 13, this.map.getWidth() - 1);
		assertEquals("Map ne disposant pas de la bonne hauteur courante", 10, this.map.getHeight() - 1);
	
		this.map.updateChunk(null, 13, 9);
		this.map.autoCrop();
		assertEquals("Map ne disposant pas de la bonne largeur courante", 3, this.map.getWidth() - 1);
		assertEquals("Map ne disposant pas de la bonne hauteur courante", 10, this.map.getHeight() - 1);
	}
}
