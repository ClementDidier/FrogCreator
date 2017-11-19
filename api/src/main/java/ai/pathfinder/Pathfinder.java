package ai.pathfinder;

import ai.MovementPath;
import environment.map.GameMap;
import math.Vector2;

public abstract class Pathfinder 
{
	public abstract MovementPath getPath(GameMap map, Vector2<Integer> absoluteBeginLocation, Vector2<Integer> absoluteEndLocation);
}
