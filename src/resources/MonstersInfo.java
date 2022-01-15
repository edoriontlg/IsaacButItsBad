package resources;

import libraries.Vector2;

public class MonstersInfo
{
    public static final int FLY_LIFE = 3;
    public static final Vector2 FLY_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.3);
    public static final double FLY_PROJ_SPEED = 0.1;
    public static final double FLY_SPEED = 0.01;

    public static final int SPIDER_LIFE = 5;
    public static final Vector2 SPIDER_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.5);
    public static final double SPIDER_SPEED = 0.005;
    public static final int SPIDER_MOVE_TIME = 750;
    public static final int SPIDER_PAUSE_TIME = 350;

    public static final int GAPER_LIFE = 20;
    public static final Vector2 GAPER_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(1.0);
    public static final double GAPER_PROJ_SPEED = 0.08;
    public static final double GAPER_SPEED = 0.005;
}
