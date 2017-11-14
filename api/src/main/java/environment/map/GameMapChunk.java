package environment.map;

public class GameMapChunk 
{
	private GameMap parent;
	private int tileWidth, tileHeight;
	private int chunkWidth, chunkHeight;
	
	public GameMapChunk(GameMap parent) 
	{
		super();
		this.parent = parent;
		this.tileWidth = this.parent.getTileWidth();
		this.tileHeight = this.parent.getTileHeight();
		this.chunkWidth = this.parent.getChunkWidth();
		this.chunkHeight = this.parent.getChunkHeight();
	}
}
