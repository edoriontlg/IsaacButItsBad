package gameWorld;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import gameobjects.*;
import libraries.*;
import monsters.Monstre;
import resources.*;

public class Room {
	private Hero hero;
	private Image BACKGROUND_TILE;
	private Image WALL;
	private Image CORNER;

	// We make it protected so other rooms can use it, but not other classes.
	protected List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();
	protected List<ObjectOnGround> ObjectPickable = new ArrayList<ObjectOnGround>();

	// Room specific, used for mobs etc
	public List<Projectile> tears = new ArrayList<Projectile>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Monstre> monstres = new ArrayList<Monstre>();
	private int cheatDmg = 0;

	// Other rooms
	public Room topRoom;
	public Room bottomRoom;
	public Room lefRoom;
	public Room rightRoom;

	public Room(Hero hero) {
		this.hero = hero;
		this.BACKGROUND_TILE = StdDraw.getImage(ImagePaths.BACKGROUND_TILE_1);
		this.WALL = StdDraw.getImage(ImagePaths.WALL);
		this.CORNER = StdDraw.getImage(ImagePaths.CORNER);
	}

	public Room(Hero hero, Room topRoom, Room bottomRoom, Room leftRoom, Room rightRoom) {
		this.hero = hero;
		this.BACKGROUND_TILE = StdDraw.getImage(ImagePaths.BACKGROUND_TILE_1);
		this.WALL = StdDraw.getImage(ImagePaths.WALL);
		this.CORNER = StdDraw.getImage(ImagePaths.CORNER);

		this.topRoom = topRoom;
		this.bottomRoom = bottomRoom;
		this.lefRoom = leftRoom;
		this.rightRoom = rightRoom;
	}

	/*
	 * Make every entity that compose a room process one step
	 */
	public void updateRoom() {
		hero.updateGameObject(StaticEntities);
		for (Monstre monstre : monstres) {
			monstre.updateGameObject(StaticEntities, hero, projectiles);
		}

		// We update the projectile positions
		if (projectiles != null) {
			for (Projectile proj : projectiles) {
				if (proj != null)
					proj.updateGameObject();
			}
		}

		// Same for the tears
		if (tears != null) {
			for (Projectile larme : tears) {
				if (larme != null)
					larme.updateGameObject();
			}
		}

	}

	public void processPhysics() {
		// First we check if the hero is picking any object
		hero.processPhysics(ObjectPickable);

		// Check if anything else is colliding
		processPhysicsTears();
		processPhysicsProjs();
		processPhysicsMonstre();
	}

	public void processPhysicsTears() {
		if (tears != null) {
			// We store the tear we will need to delete
			List<Projectile> tearToRemove = new ArrayList<Projectile>();

			// For each, if collision we do something then delete it
			for (Projectile larme : tears) {
				if (larme.getPosition().getX() > 0.9 || larme.getPosition().getX() < 0.1
						|| larme.getPosition().getY() > 0.9 || larme.getPosition().getY() < 0.1) {
					tearToRemove.add(larme);
				}
			}

			// We ACTUALLY remove it (not inside the first loop, it messes up everything)
			for (Projectile larme : tearToRemove) {
				tears.remove(larme);
			}
		}

	}

	public void processPhysicsProjs() {
		if (projectiles != null) {
			// We store the tear we will need to delete
			List<Projectile> projToRemove = new ArrayList<Projectile>();

			// We check if the projectile collide with the player or is offscreen
			for (Projectile proj : projectiles) {
				if (Physics.rectangleCollision(proj.getPosition(), proj.getSize(), hero.getPosition(),
						hero.getSize())) {
					hero.setLife(hero.getLife() - 1);
					projToRemove.add(proj);
				} else if (proj.getPosition().getX() > 0.9 || proj.getPosition().getX() < 0.1
						|| proj.getPosition().getY() > 0.9 || proj.getPosition().getY() < 0.1) {
					projToRemove.add(proj);
				}
			}

			// We ACTUALLY remove it (not inside the first loop, it messes up everything)
			for (Projectile proj : projToRemove) {
				projectiles.remove(proj);
			}
		}

	}

	public void processPhysicsMonstre() {
		if (monstres != null) {
			// We store the object we will need to delete
			List<Monstre> monstreToRemove = new ArrayList<Monstre>();
			List<Projectile> tearToRemove = new ArrayList<Projectile>();

			if (tears != null) {
				for (Monstre monstre : monstres) {
					for (Projectile larme : tears) {

						// We check if the tear collide with a monster
						if (Physics.rectangleCollision(larme.getPosition(), larme.getSize(), monstre.getPosition(),
								monstre.getSize())) {

							// We take out 1 life point to the monster and check if he has 0 life point to
							// remove it
							monstre.setLife(monstre.getLife() - hero.getAttack() - cheatDmg);
							if (monstre.getLife() <= 0) {
								monstreToRemove.add(monstre);
							}
							tearToRemove.add(larme);
						}
					}
				}
			}

			for (Monstre monstre : monstreToRemove) {
				monstres.remove(monstre);
			}
			for (Projectile larme : tearToRemove) {
				tears.remove(larme);
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

		// Draw hero
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
		if (tears != null) {
			for (Projectile larme : tears) {
				if (larme != null)
					larme.drawGameObject();
			}
		}
		if (monstres != null) {
			for (Monstre monstre : monstres) {
				if (monstre != null)
					monstre.drawGameObject();
			}
		}
		if (projectiles != null) {
			for (Projectile proj : projectiles) {
				if (proj != null)
					proj.drawGameObject();
			}
		}
	}

	public void removeMonster() {

		monstres.clear();

	}

	public void instantKill() {
		if (cheatDmg == 0) {
			cheatDmg = 1000;
		} else {
			cheatDmg = 0;
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
