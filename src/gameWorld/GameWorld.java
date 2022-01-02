package gameWorld;

import java.util.ArrayList;
import java.util.List;

import gameobjects.*;
import libraries.StdDraw;
import resources.Controls;
import resources.HeroInfos;
import resources.RoomInfos;

public class GameWorld {
	private Room currentRoom;
	private Hero hero;
	protected List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();
	private long lastTime = 0;

	// A world needs a hero
	public GameWorld(Hero hero, Room room) {
		this.hero = hero;

		currentRoom = room;
	}

	public void processUserInput() {
		processKeysForMovement();
		processKeysForShooting();
	}

	public boolean gameOver() {
		return false;
	}

	public void updateGameObjects() {
		currentRoom.updateRoom();

	}

	public void processPhysics() {
		currentRoom.processPhysics();
	}

	public void drawGameObjects() {
		currentRoom.drawRoom();
	}

	/*
	 * Keys processing
	 */

	private void processKeysForMovement() {
		if (StdDraw.isKeyPressed(Controls.goUp)) {
			hero.goUpNext();
		}
		if (StdDraw.isKeyPressed(Controls.goDown)) {
			hero.goDownNext();
		}
		if (StdDraw.isKeyPressed(Controls.goRight)) {
			hero.goRightNext();
		}
		if (StdDraw.isKeyPressed(Controls.goLeft)) {
			hero.goLeftNext();
		}
	}

	private void processKeysForShooting() {
		if (System.currentTimeMillis() - lastTime > 250 || System.currentTimeMillis() - lastTime < 0) {
			if (StdDraw.isKeyPressed(Controls.shootUp)) {
				Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
						HeroInfos.ISAAC_SPEED, "images/tear.png");
				currentRoom.Tears.add(tear);
				tear.shootUpNext();
				lastTime = System.currentTimeMillis();
			}
			if (StdDraw.isKeyPressed(Controls.shootDown)) {
				Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
						HeroInfos.ISAAC_SPEED, "images/tear.png");
				currentRoom.Tears.add(tear);
				tear.shootDownNext();
				lastTime = System.currentTimeMillis();
			}
			if (StdDraw.isKeyPressed(Controls.shootRight)) {
				Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
						HeroInfos.ISAAC_SPEED, "images/tear.png");
				currentRoom.Tears.add(tear);
				tear.shootRightNext();
				lastTime = System.currentTimeMillis();
			}
			if (StdDraw.isKeyPressed(Controls.shootLeft)) {
				Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
						HeroInfos.ISAAC_SPEED, "images/tear.png");
				currentRoom.Tears.add(tear);
				tear.shootLeftNext();
				lastTime = System.currentTimeMillis();
			}

		}

		//if (currentRoom.Tears != null) {
		//	for (Tear larme : currentRoom.Tears) {
		//		if (larme != null) {
		//			larme.drawGameObject();
		//			larme.updateGameObject();
		//		}
		//	}
		//}
	}

	

	public void UpdateRoom(Room newRoom) {
		currentRoom = newRoom;
	}

}
