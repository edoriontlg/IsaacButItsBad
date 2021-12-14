package gameWorld;

import java.util.ArrayList;
import java.util.List;
import gameobjects.Hero;
import gameobjects.ObjectOnGround;
import gameobjects.StaticEntity;
import libraries.StdDraw;
import libraries.Vector2;
import resources.DisplaySettings;
import resources.ImagePaths;
import resources.RoomInfos;

public class Room
{
	private Hero hero;
	private String backgroundTile;
	private List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();
	private List<ObjectOnGround> ObjectPickable = new ArrayList<ObjectOnGround>();


	public Room(Hero hero)
	{
		this.hero = hero;
		this.backgroundTile = ImagePaths.BACKGROUND_TILE_1;
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.5, 0.8), RoomInfos.HEART_SIZE, ImagePaths.HEART_PICKABLE));
		StaticEntities.add(new StaticEntity(positionFromTileIndex(5, 5), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
		StaticEntities.add(new StaticEntity(positionFromTileIndex(4, 6), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
		StaticEntities.add(new StaticEntity(positionFromTileIndex(8, 2), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
	}



	/*
	 * Make every entity that compose a room process one step
	 */
	public void updateRoom()
	{
		makeHeroPlay();
	}


	private void makeHeroPlay()
	{
		hero.updateGameObject();
	}

	
	public void processPhysics() {
		hero.processPhysics(StaticEntities, ObjectPickable);
	}
	

	/*
	 * Drawing
	 */
	public void drawRoom()
	{
		//For every CENTER TILE draw them
		for (int i = 1; i < RoomInfos.NB_TILES - 1; i++)
		{
			for (int j = 1; j < RoomInfos.NB_TILES - 1; j++)
			{
				Vector2 position = positionFromTileIndex(i, j);
				StdDraw.picture(position.getX(), position.getY(), backgroundTile, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
				//StdDraw.filledRectangle(position.getX(), position.getY(), RoomInfos.HALF_TILE_SIZE.getX(), RoomInfos.HALF_TILE_SIZE.getY());
			}
		}

		//Draw walls
		for (int i = 1; i < RoomInfos.NB_TILES - 1; i ++) {
			Vector2 position = positionFromTileIndex(0, i);
			StdDraw.picture(position.getX(), position.getY(), ImagePaths.WALL_LEFT, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
			position = positionFromTileIndex(RoomInfos.NB_TILES - 1, i);
			StdDraw.picture(position.getX(), position.getY(), ImagePaths.WALL_RIGHT, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
			position = positionFromTileIndex(i, 0);
			StdDraw.picture(position.getX(), position.getY(), ImagePaths.WALL_BOTTOM, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
			position = positionFromTileIndex(i, RoomInfos.NB_TILES - 1);
			StdDraw.picture(position.getX(), position.getY(), ImagePaths.WALL_TOP, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
		}


		//Draw corners
		StdDraw.picture(positionFromTileIndex(0, 0).getX(), positionFromTileIndex(0, 0).getY(), ImagePaths.CORNER_BOTTOM_LEFT, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
		StdDraw.picture(positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getX(), positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getY(), ImagePaths.CORNER_BOTTOM_RIGHT, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
		StdDraw.picture(positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getX(), positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getY(), ImagePaths.CORNER_UP_RIGHT, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);
		StdDraw.picture(positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getX(), positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getY(), ImagePaths.CORNER_UP_LEFT, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT);


		hero.drawGameObject();
		if (ObjectPickable != null) {
			for (ObjectOnGround sol : ObjectPickable) {
				if (sol != null)
					sol.drawGameObject();
			}
		}
		
		if (StaticEntities != null) {
			for (StaticEntity entity : StaticEntities) {
				if (entity != null)
					entity.drawGameObject();
			}
		}
	}
	
	/**
	 * Convert a tile index to a 0-1 position.
	 * 
	 * @param indexX
	 * @param indexY
	 * @return
	 */
	public static Vector2 positionFromTileIndex(int indexX, int indexY)
	{
		return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
				indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY());
	}
}
