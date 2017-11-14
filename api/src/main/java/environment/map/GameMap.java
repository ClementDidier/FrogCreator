package environment.map;

import java.util.ArrayList;
import java.util.List;

import utils.FrogException;

public class GameMap 
{
	private List<List<GameMapChunk>> chunks;
	private int tileWidth, tileHeight;
	private int chunkWidth, chunkHeight;
	private int currentMapWidth;

	/**
	 * Constructeur de map dynamique
	 * @param tileWidth Largeur des tuiles
	 * @param tileHeight Hauteur des tuiles
	 */
	public GameMap(int tileWidth, int tileHeight, int chunkWidth, int chunkHeight)
	{
		this.chunks = new ArrayList<List<GameMapChunk>>();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.chunkWidth = chunkWidth;
		this.chunkHeight = chunkHeight;
		this.currentMapWidth = 0;
	}

	/**
	 * <p>Ajoute une nouvelle ligne dans la map</p>
	 * <p>Ligne ajoutée en bas de la map (axe y)</p>
	 */
	private void addRow()
	{
		this.chunks.add(new ArrayList<GameMapChunk>());
	}
	
	/**
	 * Ajoute des colonnes dans la carte jusqu'à l'index spécifié
	 * @param maxIndex L'index de la dernière colonne à droite (axe x)
	 */
	private void addColumns(int maxIndex)
	{
		for(int i = 0; i < this.chunks.size(); i++)
		{
			while(this.chunks.get(i).size() <= maxIndex)
			{
				this.chunks.get(i).add(null);
			}
		}
		this.currentMapWidth = maxIndex + 1;
	}
	
	/**
	 * Obtient le chunk à la position spécifiée
	 * @param x La valeur horizontale (axe x) de la position
	 * @param y La valeur verticale (axe y) de la position
	 * @return Obtient le chunk si existant, retourne null dans le cas contraire
	 */
	public GameMapChunk getChunk(int x, int y)
	{
		if(this.chunks.size() > y && this.chunks.get(y).size() > x)
			return this.chunks.get(y).get(x);
		return null;
	}
	
	/**
	 * Met à jour un chunk à une position donnée
	 * @param chunk Le nouveau chunk (peut être null)
	 * @param x La valeur horizontale (axe x) de la position
	 * @param y La valeur verticale (axe y) de la position
	 * @throws FrogException Exception jetée si les indexs sont incorrects, ou que la map n'a pas été redimensionnée correctement
	 */
	public void updateChunk(GameMapChunk chunk, int x, int y) throws FrogException
	{
		if(x < 0 || y < 0)
			throw new FrogException("Les indexs ne peuvent être négatifs");
		
		while(this.chunks.size() <= y)
			this.addRow();
		while(this.chunks.get(y).size() <= x)
			this.addColumns(x); // Mets à jour la map entière
		
		if(this.chunks.size() >= y && this.chunks.get(y).size() >= x)
			this.chunks.get(y).set(x, chunk);
		else
			throw new FrogException("Error lors de la mise à jour d'un chunk, il se peut que l'augmentation des arrays ait échouée");
	}
	
	/**
	 * <p>Découpe la map automatiquement en fonction des chunks qui l'a compose</p>
	 * <p>La map est coupée pour être la plus petite possible (largeur / hauteur)<br/>
	 * La découpe est faite en partant par le bord bas-droit vers le bord haut-gauche de la map</p>
	 */
	public void autoCrop()
	{	
		this.autoCropRows();
		this.autoCropColumns();
	}
	
	/**
	 * Découpe automatiquement les lignes de la carte en partant du bas de la map
	 */
	private void autoCropRows()
	{
		boolean isRowNull = true;
		
		while(this.chunks.size() > 0 && isRowNull)
		{
			for(int i = 0; i < this.chunks.size(); i++)
			{
				if(this.getChunk(i, this.chunks.size() - 1) != null)
				{
					isRowNull = false;
					break;
				}
			}
			
			if(isRowNull)
			{
				this.chunks.remove(this.chunks.size() - 1);
			}
		}
	}
	
	/**
	 * Découpe automatiquement les colonnes de la carte en partant de la droite de la map
	 */
	public void autoCropColumns()
	{
		boolean isColumnNull = true;
		
		while(currentMapWidth > 0 && isColumnNull)
		{
			for(int i = 0; i < this.chunks.size(); i++)
			{
				if(this.getChunk(currentMapWidth - 1, i) != null)
				{
					isColumnNull = false;
					break;
				}
			}
			
			if(isColumnNull)
			{
				for(int i = 0; i < this.chunks.size(); i++)
				{
					this.chunks.get(i).remove(currentMapWidth - 1);
				}
				currentMapWidth--;
			}
		}
	}
	
	/**
	 * Obtient la largeur des tuiles de la map
	 * @return La largeur des tuiles de la map
	 */
	int getTileWidth() 
	{
		return tileWidth;
	}

	/**
	 * Obtient la hauteur des tuiles de la map
	 * @return La hauteur des tuiles de la map
	 */
	int getTileHeight() 
	{
		return tileHeight;
	}

	/**
	 * Obtient la largeur des chunks de la map, en nombre de tuiles
	 * @return La largeur des chunks en nombre de tuiles
	 */
	int getChunkWidth() 
	{
		return chunkWidth;
	}

	/**
	 * Obtient la hauteur des chunks de la map, en nombre de tuiles
	 * @return La largeur des chunks en nombre de tuiles
	 */
	int getChunkHeight() 
	{
		return chunkHeight;
	}
	
	/**
	 * Obtient la largeur de la map en nombre de chunks
	 * @return La largeur de la map en nombre de chunks
	 */
	public int getWidth()
	{
		return this.currentMapWidth;
	}
	
	/**
	 * Obtient la hauteur de la map en nombre de chunks
	 * @return La heuteur de la map en nombre de chunks
	 */
	public int getHeight()
	{
		return this.chunks.size();
	}
	
	/**
	 * Obtient la représentation textuelle de la map
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(int y = 0; y < this.chunks.size(); y++)
		{
			for(int x = 0; x < this.chunks.get(y).size(); x++)
			{
				if(this.getChunk(x, y) != null)
					builder.append("[C]");
				else builder.append("[ ]");
			}
			builder.append('\n');
		}
		
		return builder.toString();
	}
}
