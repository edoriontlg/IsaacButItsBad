package gameWorld;

import java.util.ArrayList;
import java.util.List;

import gameobjects.*;
import libraries.StdDraw;
import libraries.Vector2;
import resources.Controls;
import resources.HeroInfos;
import resources.RoomInfos;
import libraries.Physics;


public class GameWorld {
	private Room currentRoom;
	private Hero hero;
	private List<Tear> Tears = new ArrayList<Tear>();
	private List<Monstre> Monstres = new ArrayList<Monstre>();
	private long lastTime = 0;

	// A world needs a hero
	public GameWorld(Hero hero, Room room) {
		this.hero = hero;
		currentRoom = new Room(hero);
		Monstres.add(new Fly(new Vector2(0.6,0.8), RoomInfos.TILE_SIZE.scalarMultiplication(0.5),0.01, "images/Fly.png", 3, "fly"));

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
		processPhysicsTears(Tears);
		processPhysicsFly(Monstres, Tears);
	}

	public void drawGameObjects() {
		currentRoom.drawRoom();
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
					monstre.updateGameObject();
			}
		}
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
				Tears.add(tear);
				tear.shootUpNext();
				lastTime = System.currentTimeMillis();
			}
			if (StdDraw.isKeyPressed(Controls.shootDown)) {
				Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
						HeroInfos.ISAAC_SPEED, "images/tear.png");
				Tears.add(tear);
				tear.shootDownNext();
				lastTime = System.currentTimeMillis();
			}
			if (StdDraw.isKeyPressed(Controls.shootRight)) {
				Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
						HeroInfos.ISAAC_SPEED, "images/tear.png");
				Tears.add(tear);
				tear.shootRightNext();
				lastTime = System.currentTimeMillis();
			}
			if (StdDraw.isKeyPressed(Controls.shootLeft)) {
				Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
						HeroInfos.ISAAC_SPEED, "images/tear.png");
				Tears.add(tear);
				tear.shootLeftNext();
				lastTime = System.currentTimeMillis();
			}

		}

		if (Tears != null) {
			for (Tear larme : Tears) {
				if (larme != null) {
					larme.drawGameObject();
					larme.updateGameObject();
				}
			}
		}
	}

	public void processPhysicsTears(List<Tear> Tears) {
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

	public void processPhysicsFly(List<Monstre> Monstres, List<Tear> Tears) {
		if (Monstres != null) {
			// We store the object we will need to delete
			List<Monstre> monstreToRemove = new ArrayList<Monstre>();
			List<Tear> tearToRemove = new ArrayList<Tear>();

			
				for (Monstre monstres : Monstres) {
					if (monstres.getType() == "fly") {
						Vector2 monstrePos = new Vector2(monstres.getPosition());
						Vector2 heroPos = new Vector2(hero.getPosition());
						Vector2 direction = new Vector2();
						direction.setX(heroPos.getX()-monstrePos.getX());
						direction.setY(heroPos.getY()-monstrePos.getY());	
						direction.euclidianNorm();
						monstres.setSpeed(0.005);
						monstres.getDirection().setX(direction.getX());
						monstres.getDirection().setY(direction.getY());
						if(Tears != null){
						for (Tear larme : Tears) {
							if(Physics.rectangleCollision(larme.getPosition(), larme.getSize(), monstres.getPosition(), monstres.getSize())){
								monstreToRemove.add(monstres);
								tearToRemove.add(larme);
						}
					}
				}
						
					
			
					}
				}
			
			for (Monstre monstres : monstreToRemove) {
				Monstres.remove(monstres);
				
			}
			for (Tear larme : tearToRemove) {
				Tears.remove(larme);
			}
	
		}
	}	

	public void UpdateRoom(Room newRoom) {
		currentRoom = newRoom;
	}
	
}
