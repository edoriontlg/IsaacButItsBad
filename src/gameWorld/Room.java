package gameWorld;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import gameobjects.Fly;
import gameobjects.Gaper;
import gameobjects.Hero;
import gameobjects.Monstre;
import gameobjects.ObjectOnGround;
import gameobjects.Projectile;
import gameobjects.Spider;
import gameobjects.StaticEntity;
import gameobjects.Tear;
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;

public class Room {
	private Hero hero;
	private Image BACKGROUND_TILE;
	private Image WALL;
	private Image CORNER;

	// We make it protected so other rooms can use it, but not other classes.
	protected List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();
	protected List<ObjectOnGround> ObjectPickable = new ArrayList<ObjectOnGround>();
	
	
	//Room specific, used for mobs etc
	public List<Tear> Tears = new ArrayList<Tear>();
	public List<Projectile> Projs = new ArrayList<Projectile>();
	public List<Monstre> Monstres = new ArrayList<Monstre>();
	private long nextTime = 0;
	private long inTime = 0;
	private long projTime = 0;
	private long proj2Time = 0;
	
	


	public Room(Hero hero) {
		this.hero = hero;
		this.BACKGROUND_TILE = StdDraw.getImage(ImagePaths.BACKGROUND_TILE_1);
		this.WALL = StdDraw.getImage(ImagePaths.WALL);
		this.CORNER = StdDraw.getImage(ImagePaths.CORNER);
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.8,0.9), HeroInfos.ISAAC_SIZE, "images/hp_up.png"));

		// Temporary
	}
	

	/*
	 * Make every entity that compose a room process one step
	 */
	public void updateRoom() {
		makeHeroPlay();
	}

	private void makeHeroPlay() {
		hero.updateGameObject(StaticEntities);
	}

	public void processPhysics() {
		hero.processPhysics(ObjectPickable);
		processPhysicsTears();
		processPhysicsMonstre();
		processPhysicsProjs();
	}

	
	public void processPhysicsTears() {
		if (Tears != null) {
			// We store the tear we will need to delete
			List<Tear> tearToRemove = new ArrayList<Tear>();

			// For each, if collision we do something then delete it
			for (Tear larme : Tears) {
				if (larme.getPosition().getX() > 0.9 || larme.getPosition().getX() < 0.1
						|| larme.getPosition().getY() > 0.9 || larme.getPosition().getY() < 0.1) {
					tearToRemove.add(larme);
				}
			}

			// We ACTUALLY remove it (not inside the first loop, it messes up everything)
			for (Tear larme : tearToRemove) {
				Tears.remove(larme);
			}
		}

	}

	


	public void processPhysicsProjs() {
		if (Projs != null) {
			// We store the tear we will need to delete
			List<Projectile> projToRemove = new ArrayList<Projectile>();

			// For each, if collision we do something then delete it
			for (Projectile proj : Projs) {
				if (proj.getPosition().getX() > 0.9 || proj.getPosition().getX() < 0.1
						|| proj.getPosition().getY() > 0.9 || proj.getPosition().getY() < 0.1) {
					projToRemove.add(proj);
				}
			}

			// We ACTUALLY remove it (not inside the first loop, it messes up everything)
			for (Projectile proj : projToRemove) {
				Projs.remove(proj);
			}
		}

	}

	public void processPhysicsMonstre() {
		if (Monstres != null) {
			// We store the object we will need to delete
			List<Monstre> monstreToRemove = new ArrayList<Monstre>();
			List<Tear> tearToRemove = new ArrayList<Tear>();
			List<Projectile> projToRemove = new ArrayList<Projectile>();

			for (Monstre monstres : Monstres) {
				if (monstres.getType() == "fly") {
					Vector2 monstrePos = monstres.getPosition();
					Vector2 heroPos = hero.getPosition();
					Vector2 directionFly = new Vector2();

					// We create a Vector in the direction fo the hero
					directionFly.setX(heroPos.getX() - monstrePos.getX());
					directionFly.setY(heroPos.getY() - monstrePos.getY());
					directionFly.euclidianNorm();

					// We set the coordonnate of the monster with the direction
					monstres.getDirection().setX(directionFly.getX());
					monstres.getDirection().setY(directionFly.getY());

					if (System.currentTimeMillis() - projTime > 1700 || System.currentTimeMillis() - projTime < 0) {
						Projectile projo = new Projectile(monstrePos, RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
								HeroInfos.ISAAC_SPEED, "images/proj.png");

						Projs.add(projo);
						projo.getDirection().setX(directionFly.getX());
						projo.getDirection().setY(directionFly.getY());

						projTime = System.currentTimeMillis();

					}
				}

				// We use a timer to make the spider pause between movement
				if (System.currentTimeMillis() - nextTime > 750 || System.currentTimeMillis() - nextTime < 0) {
					if (monstres.getType() == "spider") {
						Vector2 directionSpider = new Vector2();
						directionSpider.euclidianNorm();

						// We choose with random the direction of the spider
						directionSpider.setX(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
						directionSpider.setY(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
						monstres.getDirection().setX(directionSpider.getX());
						monstres.getDirection().setY(directionSpider.getY());
						nextTime = System.currentTimeMillis();
					}
				}

				if (monstres.getType() == "gaper") {

					if (System.currentTimeMillis() - inTime > 750 || System.currentTimeMillis() - inTime < 0) {
						Vector2 directionGaper = new Vector2();
						directionGaper.euclidianNorm();

						// We choose with random the direction of the Gaper
						directionGaper.setX(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
						directionGaper.setY(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
						monstres.getDirection().setX(directionGaper.getX());
						monstres.getDirection().setY(directionGaper.getY());
						inTime = System.currentTimeMillis();
					}
					if (System.currentTimeMillis() - proj2Time > 700 || System.currentTimeMillis() - proj2Time < 0) {
						Vector2 dir = new Vector2();
						Projectile projo = new Projectile(monstres.getPosition(),
								RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
								0.01, "images/proj.png");

						dir.setX(hero.getPosition().getX() - monstres.getPosition().getX());
						dir.setY(hero.getPosition().getY() - monstres.getPosition().getY());
						dir.euclidianNorm();
						projo.getDirection().setX(dir.getX());
						projo.getDirection().setY(dir.getY());
						Projs.add(projo);
						

						proj2Time = System.currentTimeMillis();

					}

				}
			}

			if (Tears != null) {
				for (Monstre monstre : Monstres) {
					for (Tear larme : Tears) {

						// We check if the tear collide with a monster
						if (Physics.rectangleCollision(larme.getPosition(), larme.getSize(), monstre.getPosition(),
								monstre.getSize())) {

							// We take out 1 life point to the monster and check if he has 0 life point to
							// remove it
							monstre.setLife(monstre.getLife() - 1);
							if (monstre.getLife() == 0) {
								monstreToRemove.add(monstre);
							}
							tearToRemove.add(larme);
						}
					}
				}
			}

					for (Projectile proj : Projs) {
						if (Physics.rectangleCollision(proj.getPosition(), proj.getSize(), hero.getPosition(),
								hero.getSize())) {
							hero.setLife(hero.getLife() - 1);
							projToRemove.add(proj);
						}
					}

					

				

			

			for (Monstre monstres : monstreToRemove) {
				Monstres.remove(monstres);

			}
			for (Tear larme : tearToRemove) {
				Tears.remove(larme);
			}
			for (Projectile proj : projToRemove) {
				Projs.remove(proj);
			}

		}
	}

	/*
	 * Drawing
	 */
	public void drawRoom() {
		// For every CENTER TILE draw them
		for (int i = 1; i < RoomInfos.NB_TILES - 1; i++) {
			for (int j = 1; j < RoomInfos.NB_TILES - 1; j++) {
				Vector2 position = positionFromTileIndex(i, j);
				StdDraw.pictureIMG(position.getX(), position.getY(), this.BACKGROUND_TILE, RoomInfos.TILE_WIDTH,
						RoomInfos.TILE_HEIGHT, 0);
				// StdDraw.filledRectangle(position.getX(), position.getY(),
				// RoomInfos.HALF_TILE_SIZE.getX(), RoomInfos.HALF_TILE_SIZE.getY());
			}
		}

		// Draw walls
		for (int i = 1; i < RoomInfos.NB_TILES - 1; i++) {
			Vector2 position = positionFromTileIndex(0, i);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT,
					180);
			position = positionFromTileIndex(RoomInfos.NB_TILES - 1, i);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT,
					0);
			position = positionFromTileIndex(i, 0);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT,
					270);
			position = positionFromTileIndex(i, RoomInfos.NB_TILES - 1);
			StdDraw.pictureIMG(position.getX(), position.getY(), this.WALL, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT,
					90);
		}

		// Draw corners
		StdDraw.pictureIMG(positionFromTileIndex(0, 0).getX(), positionFromTileIndex(0, 0).getY(), this.CORNER,
				RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 180);
		StdDraw.pictureIMG(positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getX(),
				positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getY(), this.CORNER, RoomInfos.TILE_WIDTH,
				RoomInfos.TILE_HEIGHT, 270);
		StdDraw.pictureIMG(positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getX(),
				positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getY(), this.CORNER,
				RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 0);
		StdDraw.pictureIMG(positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getX(),
				positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getY(), this.CORNER, RoomInfos.TILE_WIDTH,
				RoomInfos.TILE_HEIGHT, 90);

		hero.drawGameObject();
		StdDraw.picture(0.05, 0.9, ImagePaths.COIN);
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

		
		if (Tears != null) {
			for (Tear larme : Tears) {
				if (larme != null)
					larme.drawGameObject();
				larme.updateGameObject();
			}
		}
		if (Monstres != null) {
			for (Monstre monstre : Monstres) {
				if (monstre != null)
					monstre.drawGameObject();
				monstre.updateGameObject(StaticEntities);
			}
		}
		if (StaticEntities != null) {
			for (StaticEntity entity : StaticEntities) {
				if (entity != null)
					entity.drawGameObject();
			}
		}
		if (Projs != null) {
			for (Projectile proj : Projs) {
				if (proj != null)
					proj.drawGameObject();
				proj.updateGameObject();
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
	public static Vector2 positionFromTileIndex(int indexX, int indexY) {
		return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
				indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY());
	}
}
