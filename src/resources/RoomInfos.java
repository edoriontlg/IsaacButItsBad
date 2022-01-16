package resources;

import gameWorld.Room;
import libraries.Vector2;

public class RoomInfos
{
	public static final int NB_TILES = 11; //Beyond 11 we start to see the limitations of stddraw. Don't go beyond that
	public static final double TILE_WIDTH = 1.0 / NB_TILES;
	public static final double TILE_HEIGHT = 1.0 / NB_TILES;
	public static final Vector2 TILE_SIZE = new Vector2(TILE_WIDTH, TILE_HEIGHT);
	public static final Vector2 HALF_TILE_SIZE = new Vector2(TILE_WIDTH, TILE_HEIGHT).scalarMultiplication(0.5);
	public static Vector2 PICKABLE_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.4);

	public static double maxHorizontal = positionFromTileIndex(NB_TILES-1, NB_TILES-1).getX() - HALF_TILE_SIZE.getX();
	public static double maxVertical = positionFromTileIndex(NB_TILES-1, NB_TILES-1).getY() - HALF_TILE_SIZE.getY();
	public static double minHorizontal = positionFromTileIndex(0, 0).getX() + HALF_TILE_SIZE.getX();
	public static double minVertical = positionFromTileIndex(0, 0).getY() + HALF_TILE_SIZE.getY();
	
	public static final Vector2 POSITION_CENTER_OF_ROOM = new Vector2(0.5, 0.5);
	public static final Vector2 POSITION_UP_OF_ROOM = new Vector2(0.4, 0.7);
	public static final Vector2 POSITION_DOWN_OF_ROOM = new Vector2(0.5, 0.2);

	

	/**
	 * Convert a tile index to a 0-1 position.
	 * 
	 * @param indexX
	 * @param indexY
	 * @return
	 */
	public static Vector2 positionFromTileIndex(int indexX, int indexY) {
		return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
				indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY());
	}
}
