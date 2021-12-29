package gameWorld;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import gameobjects.Hero;
import gameobjects.ObjectOnGround;
import gameobjects.StaticEntity;
import gameobjects.Tear;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

public class Room
{
	private Hero hero;
	private Image BACKGROUND_TILE;
	private Image WALL;
	private Image CORNER;

	//We make it protected so other rooms can use it, but not other classes.
	protected List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();
	protected List<ObjectOnGround> ObjectPickable = new ArrayList<ObjectOnGround>();


	public Room(Hero hero)
	{
		this.hero = hero;
		this.BACKGROUND_TILE = StdDraw.getImage(ImagePaths.BACKGROUND_TILE_1);
		this.WALL = StdDraw.getImage(ImagePaths.WALL);
		this.CORNER = StdDraw.getImage(ImagePaths.CORNER);

		//Temporary
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
		hero.updateGameObject(StaticEntities);
	}

	
	public void processPhysics() {
		hero.processPhysics(ObjectPickable);
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
				StdDraw.pictureIMG(position.getX(), position.getY(), this.BACKGROUND_TILE, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 0);
				//StdDraw.filledRectangle(position.getX(), position.getY(), RoomInfos.HALF_TILE_SIZE.getX(), RoomInfos.HALF_TILE_SIZE.getY());
			}
		}

		//Draw walls
		for (int i = 1; i < RoomInfos.NB_TILES - 1; i ++) {
			Vector2 position = positionFromTileIndex(0, i);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 180);
			position = positionFromTileIndex(RoomInfos.NB_TILES - 1, i);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 0);
			position = positionFromTileIndex(i, 0);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 270);
			position = positionFromTileIndex(i, RoomInfos.NB_TILES - 1);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 90);
		}


		//Draw corners
		StdDraw.pictureIMG(positionFromTileIndex(0, 0).getX(), positionFromTileIndex(0, 0).getY(), this.CORNER, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 180);
		StdDraw.pictureIMG(positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getX(), positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getY(), this.CORNER, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 270);
		StdDraw.pictureIMG(positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getX(), positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getY(), this.CORNER, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 0);
		StdDraw.pictureIMG(positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getX(), positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getY(), this.CORNER, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 90);


		hero.drawGameObject();
		StdDraw.picture(0.05,0.9,ImagePaths.COIN);


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
