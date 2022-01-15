package gameWorld;

import java.util.ArrayList;
import java.util.List;

import gameobjects.*;
import libraries.StdDraw;
import resources.Controls;

public class GameWorld {
	private static Room currentRoom;
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
		return hero.getLife() == 0;
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

	// Touches pour tricher dans le jeu
	private void processKeysForCheating() {

		// permet de devenir invincible
		if (StdDraw.isKeyPressed(Controls.invincible)) {
			if (hero.getLife() != 1006) {
				hero.setLife(hero.getLife() + 1000);
			} else {
				hero.setLife(6);
			}
		}

		// Permet d'aller beaucoup plus vite
		if (StdDraw.isKeyPressed(Controls.speed)) {
			if (hero.getSpeed() != 0.04) {
				hero.setSpeed(hero.getSpeed() + 0.03);
			} else {
				hero.setSpeed(0.01);
			}
		}

		// Permet de tuer tout les monstres
		if (StdDraw.isKeyPressed(Controls.kill)) {
			currentRoom.removeMonster();
		}

		// Permet d'avoir des pièces à l'infini
		if (StdDraw.isKeyPressed(Controls.piece)) {
			hero.setMoney(hero.getMoney() + 10);
		}

		// Augmente les dommages d'Isaac
		if (StdDraw.isKeyPressed(Controls.dmg)) {
			currentRoom.instantKill();
		}

	}

	public static void UpdateRoom(Room newRoom) {
		currentRoom = newRoom;
	}
}
