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

	private void addRow()
	{
		this.chunks.add(new ArrayList<GameMapChunk>());
	}
	
	// TODO: Test
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
	
	public GameMapChunk getChunk(int x, int y)
	{
		if(this.chunks.size() > y && this.chunks.get(y).size() > x)
			return this.chunks.get(y).get(x);
		return null;
	}
	
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
		
		System.out.println(this.toString());
	}
	
	// TODO: Test
	/**
	 * <ul>
	 * 	<li>Découpe la map automatiquement en fonction des chunks qui l'a compose</li>
	 * 	<li>La map est coupée pour être la plus petite possible (largeur / hauteur)</li>
	 * </ul>
	 */
	public void autoCrop()
	{	
		this.autoCropRows();
		this.autoCropColumns();
		
		System.out.println(this.toString());
	}
	
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
	
	int getTileWidth() 
	{
		return tileWidth;
	}

	int getTileHeight() 
	{
		return tileHeight;
	}

	int getChunkWidth() 
	{
		return chunkWidth;
	}

	int getChunkHeight() 
	{
		return chunkHeight;
	}
	
	public int getWidth()
	{
		return this.currentMapWidth;
	}
	
	public int getHeight()
	{
		return this.chunks.size();
	}
	
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
