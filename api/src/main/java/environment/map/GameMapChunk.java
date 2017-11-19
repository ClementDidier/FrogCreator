package environment.map;

import java.util.HashMap;

import system.objects.ResourceObject;
import utils.FrogException;

public class GameMapChunk implements ResourceObject
{
	private GameMap parent;
	private int tileWidth, tileHeight;
	private int chunkTilesCountRow, chunkTilesCountColumn;
	private int chunkWidth, chunkHeight;
	private HashMap<Integer, GameMapLayer> layers;
	
	public GameMapChunk(GameMap parent) 
	{
		super();
		this.parent = parent;
		this.tileWidth = this.parent.getTileWidth();
		this.tileHeight = this.parent.getTileHeight();
		this.chunkTilesCountRow = this.parent.getChunkTilesCountRow();
		this.chunkTilesCountColumn = this.parent.getChunkTilesCountColumn();
		this.chunkWidth = this.tileWidth * this.chunkTilesCountRow;
		this.chunkHeight = this.tileHeight * this.chunkTilesCountColumn;
		this.layers = new HashMap<Integer, GameMapLayer>();
	}
	
	public void load()
	{
		for(Integer i : this.layers.keySet())
		{
			this.layers.get(i).load();
		}
	}
	
	public void unload()
	{
		for(Integer i : this.layers.keySet())
		{
			this.layers.get(i).unload();
		}
	}
	
	/**
	 * Obtient la largeur en pixels du chunk
	 * @return La largeur du chunk
	 */
	public int getWidth()
	{
		return this.chunkWidth;
	}
	
	/**
	 * Obtient la hauteur en pixels du chunk
	 * @return La hauteur du chunk
	 */
	public int getHeight()
	{
		return this.chunkHeight;
	}
	
	/**
	 * Obtient l'état d'existence de la couche avec l'index spécifié
	 * @param index L'index de la couche recherchée
	 * @return Vrai si la couche existe, Faux dans le cas contraire
	 */
	public boolean hasLayer(int index)
	{
		return this.layers.containsKey(index);
	}
	
	/**
	 * Ajoute une nouvelle couche avec l'index spécifié
	 * @param index L'index de la nouvelle couche à ajouter
	 */
	public void addLayer(int index)
	{
		this.layers.put(index, new GameMapLayer(this.chunkTilesCountRow, this.chunkTilesCountColumn));
	}
	
	/**
	 * Supprime la couche spécifiée
	 * @param index L'index de la couche à supprimer
	 */
	public void removeLayer(int index)
	{
		this.layers.remove(index);
	}
	
	/**
	 * Met à jour la tuile disponible à la position relative spécifiée, sur la couche donnée
	 * @param layerIndex L'index de la couche de la tuile
	 * @param relativeX La valeur horizontale de la position
	 * @param relativeY La valeur verticale de la position
	 * @param value La nouvelle valeur de la tuile
	 * @throws FrogException Exception jetée si la couche spécifiée n'existe pas
	 */
	public void setTile(int layerIndex, int relativeX, int relativeY, int value) throws FrogException
	{
		if(this.layers.size() <= layerIndex)
			throw new FrogException("Tentative de modification d'une tuile sur une couche inexistante");
		this.layers.get(layerIndex).setTile(relativeX, relativeY, value);
	}
	
	/**
	 * Obtient la tuile à la position relative spécifiée du chunk
	 * @param layerIndex La couche du chunk
	 * @param relativeX La valeur horizontale de la position
	 * @param relativeY La valeur verticale de la position
	 * @return La tuile à la position relative spécifiée
	 * @throws FrogException Exception jetée si la couche spécifiée n'existe pas
	 */
	public Integer getTile(int layerIndex, int relativeX, int relativeY) throws FrogException
	{
		if(this.layers.size() <= layerIndex)
			throw new FrogException("Tentative d'obtention d'une tuile sur une couche inexistante");
		return this.layers.get(layerIndex).getTile(relativeX, relativeY);
	}
}
