package gameWorld;

import gameobjects.Hero;
import gameobjects.StaticEntity;
import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;

public class Room
{
	private Hero hero;
	private StaticEntity[] StaticEntities;


	public Room(Hero hero)
	{
		this.hero = hero;
		StaticEntity se = new StaticEntity(new Vector2(0.5, 0.4), HeroInfos.ISAAC_SIZE, ImagePaths.ROCK);
		//StaticEntity se2 = new StaticEntity(new Vector2(0.6, 0.5), HeroInfos.ISAAC_SIZE, ImagePaths.ROCK);
		StaticEntity se3 = new StaticEntity(new Vector2(0.3, 0.7), HeroInfos.ISAAC_SIZE, ImagePaths.ROCK);
		StaticEntity se4 = new StaticEntity(new Vector2(0.8, 0.6), HeroInfos.ISAAC_SIZE, ImagePaths.ROCK);
		StaticEntities = new StaticEntity[4];
		StaticEntities[0] = se;
		//StaticEntities[1] = se2;
		StaticEntities[2] = se3;
		StaticEntities[3] = se4;
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
		hero.processPhysics(StaticEntities);
	}
	
	/*
	 * Drawing
	 */
	public void drawRoom()
	{
		// For every tile, set background color.
		StdDraw.setPenColor(StdDraw.GRAY);
		for (int i = 0; i < RoomInfos.NB_TILES; i++)
		{
			for (int j = 0; j < RoomInfos.NB_TILES; j++)
			{
				Vector2 position = positionFromTileIndex(i, j);
				StdDraw.filledRectangle(position.getX(), position.getY(), RoomInfos.HALF_TILE_SIZE.getX(),
						RoomInfos.HALF_TILE_SIZE.getY());
			}
		}
		hero.drawGameObject();
		
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
	private static Vector2 positionFromTileIndex(int indexX, int indexY)
	{
		return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
				indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY());
	}
}
