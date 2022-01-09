package gameWorld;

import java.util.ArrayList;
import java.util.List;

import gameobjects.*;
import libraries.StdDraw;
import resources.Controls;

public class GameWorld {
	private Room currentRoom;
	private Hero hero;
	protected List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();

	// A world needs a hero
	public GameWorld(Hero hero, Room room) {
		this.hero = hero;

		currentRoom = room;
	}

	public void processUserInput() {
		processKeysForMovement();
		hero.processKeysForShooting(currentRoom);
		processKeysForCheating();
	}

	public boolean gameOver() {
		
		return false;
	}

	public void updateGameObjects() {
		currentRoom.updateRoom();

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

	private void processKeysForCheating() {
		if (StdDraw.isKeyPressed(Controls.invincible)) {
			if (hero.getLife() != 1006) {
				hero.setLife(hero.getLife() + 1000);
			} else {
				hero.setLife(6);
			}
		}
		if (StdDraw.isKeyPressed(Controls.speed)) {
			if (hero.getSpeed() != 0.04) {
				hero.setSpeed(hero.getSpeed() + 0.03);
			} else {
				hero.setSpeed(0.01);
			}
		}
		if (StdDraw.isKeyPressed(Controls.kill)) {
			currentRoom.removeMonster();
		}
		if (StdDraw.isKeyPressed(Controls.piece)) {
			hero.setMoney(hero.getMoney() + 10);
		}
		if (StdDraw.isKeyPressed(Controls.dmg)) {
			currentRoom.instantKill();
		}

	}

	


	public void UpdateRoom(Room newRoom) {
		currentRoom = newRoom;
	}
}
