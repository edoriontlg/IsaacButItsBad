package gameWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;

import gameobjects.*;
import libraries.*;
import monsters.Monstre;
import monsters.Monstre.MONSTER_TYPE;
import resources.*;

public class Room {
	private Hero hero;
	private Image BACKGROUND_TILE;
	private Image WALL;
	private Image CORNER;
	private Image DOOR_OPEN;
	private Image DOOR_CLOSED;

	// We make it protected so other rooms can use it, but not other classes.
	protected List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();
	protected List<ObjectOnGround> ObjectPickable = new ArrayList<ObjectOnGround>();

	// Room specific, used for mobs etc
	public List<Projectile> tears = new ArrayList<Projectile>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Monstre> monstres = new ArrayList<Monstre>();

	private List<Projectile> tearsToRemove = new ArrayList<Projectile>();
	private List<Projectile> projToRemove = new ArrayList<Projectile>();
	private List<Monstre> monstresToRemove = new ArrayList<Monstre>();

	public boolean roomFinished = false;

	// Other rooms
	public Room topRoom;
	private Vector2 topRoomPos = new Vector2(
			positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getX() / (double) 2,
			positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getY());
	public Room bottomRoom;
	private Vector2 bottomRoomPos = new Vector2(
			positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getX() / (double) 2,
			positionFromTileIndex(RoomInfos.NB_TILES - 1, 0).getY());
	public Room leftRoom;
	private Vector2 leftRoomPos = new Vector2(
			positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getX(),
			positionFromTileIndex(0, RoomInfos.NB_TILES - 1).getY() / (double) 2);
	public Room rightRoom;
	private Vector2 rightRoomPos = new Vector2(
			positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getX(),
			positionFromTileIndex(RoomInfos.NB_TILES - 1, RoomInfos.NB_TILES - 1).getY() / (double) 2);

	public Room(Hero hero) {
		this.hero = hero;
		this.BACKGROUND_TILE = StdDraw.getImage(ImagePaths.BACKGROUND_TILE_1);
		this.WALL = StdDraw.getImage(ImagePaths.WALL);
		this.CORNER = StdDraw.getImage(ImagePaths.CORNER);
		this.DOOR_CLOSED = StdDraw.getImage(ImagePaths.CLOSED_DOOR);
		this.DOOR_OPEN = StdDraw.getImage(ImagePaths.OPENED_DOOR);
	}

	public Room(Hero hero, Room topRoom, Room bottomRoom, Room leftRoom, Room rightRoom) {
		this.hero = hero;
		this.BACKGROUND_TILE = StdDraw.getImage(ImagePaths.BACKGROUND_TILE_1);
		this.WALL = StdDraw.getImage(ImagePaths.WALL);
		this.CORNER = StdDraw.getImage(ImagePaths.CORNER);

		this.topRoom = topRoom;
		this.bottomRoom = bottomRoom;
		this.leftRoom = leftRoom;
		this.rightRoom = rightRoom;
	}

	/*
	 * Make every entity that compose a room process one step
	 */
	public void updateRoom() {
		// Update hero (movement and attack)
		hero.updateGameObject(StaticEntities);

		if (roomFinished) {
			if (topRoom != null && Physics.rectangleCollision(topRoomPos,
					RoomInfos.TILE_SIZE.scalarMultiplication(1.5),
					hero.getPosition(),
					hero.getSize())) {

				hero.getPosition().setY(0.15);
				GameWorld.UpdateRoom(topRoom);
			} else if (rightRoom != null && Physics.rectangleCollision(rightRoomPos,
					RoomInfos.TILE_SIZE.scalarMultiplication(1.5),
					hero.getPosition(),
					hero.getSize())) {

				hero.getPosition().setX(0.15);
				GameWorld.UpdateRoom(rightRoom);
			} else if (bottomRoom != null && Physics.rectangleCollision(bottomRoomPos,
					RoomInfos.TILE_SIZE.scalarMultiplication(1.5),
					hero.getPosition(),
					hero.getSize())) {

				hero.getPosition().setY(0.85);
				GameWorld.UpdateRoom(bottomRoom);
			} else if (leftRoom != null && Physics.rectangleCollision(leftRoomPos,
					RoomInfos.TILE_SIZE.scalarMultiplication(1.5),
					hero.getPosition(),
					hero.getSize())) {

				hero.getPosition().setX(0.85);
				GameWorld.UpdateRoom(leftRoom);
			}

		}

		// Update monster (movement and attack)
		for (Monstre monstre : monstres) {
			monstre.updateGameObject(StaticEntities, hero, projectiles);
		}

		// We update the projectile positions
		for (Projectile proj : projectiles) {
			if (proj != null)
				proj.updateGameObject();
		}

		// Same for the tears
		for (Projectile larme : tears) {
			if (larme != null)
				larme.updateGameObject();
		}

		// First we check if the hero is picking any object
		hero.processPhysics(ObjectPickable);

		// Check if anything else is colliding (IE Tears with monsters, projs with
		// heroes)
		processPhysicsTears();
		processPhysicsProjs();

		// We clear all entities that needs to be deleted
		clearDeadEntities();

		
		if (!roomFinished && monstres.isEmpty()) roomFinished = true;
	}

	
	private void processPhysicsTears() {
		// For each tear if it's outside the map delete it
		for (Projectile larme : tears) {
			if (larme.getPosition().getX() > 0.9 || larme.getPosition().getX() < 0.1
					|| larme.getPosition().getY() > 0.9 || larme.getPosition().getY() < 0.1) {
				tearsToRemove.add(larme);
			}

			for (Monstre monstre : monstres) {
				// We check if the tear collide with a monster
				if (Physics.rectangleCollision(larme.getPosition(), larme.getSize(), monstre.getPosition(),
						monstre.getSize())) {

					// We take out 1 life point to the monster and check if he has 0 life point to
					// remove it
					monstre.setLife(monstre.getLife() - hero.getAttack() - hero.cheatDamage);
					if (monstre.getLife() <= 0) {
						monstresToRemove.add(monstre);
					}
					tearsToRemove.add(larme);
				}
			}
		}
	}

	// Gère la collision des projectiles
	private void processPhysicsProjs() {
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
	}

	// Enlève les objets quand ils meurent
	private void clearDeadEntities() {
		for (Projectile proj : tearsToRemove) {
			tears.remove(proj);
		}

		for (Projectile proj : projToRemove) {
			projectiles.remove(proj);
		}

		for (Monstre proj : monstresToRemove) {
			monstres.remove(proj);
		}
	}

	/*
	 * Drawing
	 * 
	 * 
	 * 
	 */
	public void drawRoom() {
		// For every CENTER TILE
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

		// Draw doors

		if (topRoom != null) {
			if (roomFinished)
				StdDraw.pictureIMG(topRoomPos.getX(), topRoomPos.getY(),
						DOOR_OPEN, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 0);
			else
				StdDraw.pictureIMG(topRoomPos.getX(), topRoomPos.getY(),
						DOOR_CLOSED, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 0);
		}

		if (bottomRoom != null) {
			if (roomFinished)
				StdDraw.pictureIMG(bottomRoomPos.getX(), bottomRoomPos.getY(),
						DOOR_OPEN, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 180);
			else
				StdDraw.pictureIMG(bottomRoomPos.getX(), bottomRoomPos.getY(),
						DOOR_CLOSED, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 180);
		}

		if (leftRoom != null) {
			if (roomFinished)
				StdDraw.pictureIMG(leftRoomPos.getX(), leftRoomPos.getY(),
						DOOR_OPEN, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 90);
			else
				StdDraw.pictureIMG(leftRoomPos.getX(), leftRoomPos.getY(),
						DOOR_CLOSED, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 90);
		}

		if (rightRoom != null) {
			if (roomFinished)
				StdDraw.pictureIMG(rightRoomPos.getX(), rightRoomPos.getY(),
						DOOR_OPEN, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 270);
			else
				StdDraw.pictureIMG(rightRoomPos.getX(), rightRoomPos.getY(),
						DOOR_CLOSED, RoomInfos.TILE_WIDTH, RoomInfos.TILE_HEIGHT, 270);
		}

		// Draw hero
		hero.drawGameObject();

		for (ObjectOnGround obj : ObjectPickable) {
			if (obj != null)
				obj.drawGameObject();
		}
		for (StaticEntity entity : StaticEntities) {
			if (entity != null)
				entity.drawGameObject();
		}
		for (Projectile larme : tears) {
			if (larme != null)
				larme.drawGameObject();
		}
		for (Monstre monstre : monstres) {
			if (monstre != null)
				monstre.drawGameObject();
		}
		for (Projectile proj : projectiles) {
			if (proj != null)
				proj.drawGameObject();
		}
	}

	/*
	 * Helpers
	 * 
	 * 
	 * 
	 */
	public void removeMonster() {
		monstres.clear();
		projectiles.clear();
	}

	public void instantKill() {
		if (hero.cheatDamage == 0) {
			hero.cheatDamage = 1000;
		} else {
			hero.cheatDamage = 0;
		}
	}

	
	/** 
	 * Génère un donjon à partir de salles, d'une salle de boss et d'un heros
	 * @param rooms
	 * @param bossRoom
	 * @param hero
	 * @return Room
	 */
	// Création d'un donjon avec des salles aléatoires
	public static Room createDungeon(Room[] rooms, BossRoom bossRoom, Hero hero) {
		Room result = new StartRoom(hero);

		Room currentRoomToEdit = result;

		// For each room inside our loop
		for (int i = 0; i < rooms.length; i++) {
			int choice = new Random().nextInt(2);
			int roomPos = new Random().nextInt(4);

			switch (roomPos) {
				case 0:
					if (currentRoomToEdit.topRoom != null) {
						i--;
						continue;
					}
					break;
				case 1:
					if (currentRoomToEdit.rightRoom != null) {
						i--;
						continue;
					}
					break;
				case 2:
					if (currentRoomToEdit.bottomRoom != null) {
						i--;
						continue;
					}
					break;
				default:
					if (currentRoomToEdit.leftRoom != null) {
						i--;
						continue;
					}
					break;
			}

			switch (choice) {
				case 0:
					switch (roomPos) {
						case 0:
							currentRoomToEdit.topRoom = rooms[i];
							rooms[i].bottomRoom = currentRoomToEdit;
							break;
						case 1:
							currentRoomToEdit.rightRoom = rooms[i];
							rooms[i].leftRoom = currentRoomToEdit;
							break;
						case 2:
							currentRoomToEdit.bottomRoom = rooms[i];
							rooms[i].topRoom = currentRoomToEdit;
							break;
						default:
							currentRoomToEdit.leftRoom = rooms[i];
							rooms[i].rightRoom = currentRoomToEdit;
							break;
					}
					continue;
				default:
					switch (roomPos) {
						case 0:
							currentRoomToEdit.topRoom = rooms[i];
							rooms[i].bottomRoom = currentRoomToEdit;
							break;
						case 1:
							currentRoomToEdit.rightRoom = rooms[i];
							rooms[i].leftRoom = currentRoomToEdit;
							break;
						case 2:
							currentRoomToEdit.bottomRoom = rooms[i];
							rooms[i].topRoom = currentRoomToEdit;
							break;
						default:
							currentRoomToEdit.leftRoom = rooms[i];
							rooms[i].rightRoom = currentRoomToEdit;
							break;
					}
					currentRoomToEdit = rooms[i];
					continue;
			}
		}

		// Lastly we add the bost room at the end
		int roomPos = new Random().nextInt(4);
		switch (roomPos) {
			case 0:
				currentRoomToEdit.topRoom = bossRoom;
				bossRoom.bottomRoom = currentRoomToEdit;
				break;
			case 1:
				currentRoomToEdit.rightRoom = bossRoom;
				bossRoom.leftRoom = currentRoomToEdit;
				break;
			case 2:
				currentRoomToEdit.bottomRoom = bossRoom;
				bossRoom.topRoom = currentRoomToEdit;
				break;
			default:
				currentRoomToEdit.leftRoom = bossRoom;
				bossRoom.rightRoom = currentRoomToEdit;
				break;
		}

		return result;
	}

	public boolean win() {
		for (Monstre monstre : monstres) {
			if (monstre.getType() == MONSTER_TYPE.GAPER) {
				return false;
			}
		}
		return true;
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
