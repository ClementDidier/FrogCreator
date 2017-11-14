package environment.map;

import java.util.HashMap;

import utils.FrogException;

public class GameMapChunk 
{
	private GameMap parent;
	private int tileWidth, tileHeight;
	private int chunkWidth, chunkHeight;
	private HashMap<Integer, GameMapLayer> layers;
	
	public GameMapChunk(GameMap parent) 
	{
		super();
		this.parent = parent;
		this.tileWidth = this.parent.getTileWidth();
		this.tileHeight = this.parent.getTileHeight();
		this.chunkWidth = this.parent.getChunkWidth();
		this.chunkHeight = this.parent.getChunkHeight();
		this.layers = new HashMap<Integer, GameMapLayer>();
	}
	
	public void load()
	{
		
	}
	
	public void unload()
	{
		
	}
	
	public boolean hasLayer(int index)
	{
		return this.layers.containsKey(index);
	}
	
	public void addLayer(int index)
	{
		this.layers.put(index, new GameMapLayer(this.chunkWidth, this.chunkHeight));
	}
	
	public void removeLayer(int index)
	{
		this.layers.remove(index);
	}
	
	public void setTile(int layerIndex, int relativeX, int relativeY, int value) throws FrogException
	{
		if(this.layers.size() <= layerIndex)
			throw new FrogException("Tentative de modification d'une tuile sur une couche inexistante");
		this.layers.get(layerIndex).setTile(relativeX, relativeY, value);
	}
	
	public Integer getTile(int layerIndex, int relativeX, int relativeY) throws FrogException
	{
		if(this.layers.size() <= layerIndex)
			throw new FrogException("Tentative d'obtention d'une tuile sur une couche inexistante");
		return this.layers.get(layerIndex).getTile(relativeX, relativeY);
	}
}
