package resources;

import libraries.Vector2;

public class HeroInfos
{
	public static Vector2 ISAAC_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.7);
	public static final double ISAAC_SPEED = 0.01;
	public static final double AUTHORIZED_OVERLAP = RoomInfos.TILE_WIDTH / 10000;
	public static final int MAX_LIFE = 6;
	public static final int MAX_UP_LIFE = 8;
}
