package gameWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private List<Projectile> Projs = new ArrayList<Projectile>();
	private List<Monstre> Monstres = new ArrayList<Monstre>();
	protected List<StaticEntity> StaticEntities = new ArrayList<StaticEntity>();
	private long lastTime = 0;
	private long nextTime = 0;
	private long inTime = 0;
	private long projTime = 0;
	private long proj2Time = 0;



	// A world needs a hero
	public GameWorld(Hero hero, Room room) {
		this.hero = hero;
		currentRoom = new Room(hero);
		Monstres.add(new Fly(new Vector2(0.6,0.8), RoomInfos.TILE_SIZE.scalarMultiplication(0.5),0.01/8, "images/Fly.png", 3, "fly"));
		Monstres.add(new Spider(new Vector2(0.6,0.6), RoomInfos.TILE_SIZE.scalarMultiplication(0.5),0.07, "images/Spider.png", 5, "spider"));
		Monstres.add(new Gaper(new Vector2(0.5,0.5), RoomInfos.TILE_SIZE.scalarMultiplication(1.0),0.1, "images/Gaper.png", 20, "gaper"));


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
		processPhysicsMonstre(Monstres, Tears);
		processPhysicsProjs(Projs);
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

	public void processPhysicsProjs(List<Projectile> Projs) {
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

	public void processPhysicsMonstre(List<Monstre> Monstres, List<Tear> Tears) {
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

							//We create a Vector in the direction fo the hero
							directionFly.setX(heroPos.getX()-monstrePos.getX());
							directionFly.setY(heroPos.getY()-monstrePos.getY());	
							directionFly.euclidianNorm();

							//We set the coordonnate of the monster with the direction
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

					//We use a timer to make the spider pause between movement
					if (System.currentTimeMillis() - nextTime > 750 || System.currentTimeMillis() - nextTime < 0) {	
						if(monstres.getType()=="spider"){
							Vector2 directionSpider = new Vector2();
							directionSpider.euclidianNorm();

							//We choose with random the direction of the spider
							directionSpider.setX(Math.floor(Math.random()*(1.0-(-1.0)+1)+(-1.0)));
							directionSpider.setY(Math.floor(Math.random()*(1.0-(-1.0)+1)+(-1.0)));
							monstres.getDirection().setX(directionSpider.getX());
							monstres.getDirection().setY(directionSpider.getY());
							nextTime = System.currentTimeMillis();
						}
					}
					
					if(monstres.getType()=="gaper"){
						
					if (System.currentTimeMillis() - inTime > 750 || System.currentTimeMillis() - inTime < 0) {
							Vector2 directionGaper = new Vector2();
							directionGaper.euclidianNorm();

							//We choose with random the direction of the Gaper
							directionGaper.setX(Math.floor(Math.random()*(1.0-(-1.0)+1)+(-1.0)));
							directionGaper.setY(Math.floor(Math.random()*(1.0-(-1.0)+1)+(-1.0)));
							monstres.getDirection().setX(directionGaper.getX());
							monstres.getDirection().setY(directionGaper.getY());
							inTime = System.currentTimeMillis();
							}
					if (System.currentTimeMillis() - proj2Time > 700 || System.currentTimeMillis() - proj2Time < 0) {
								Vector2 dir = new Vector2();	
								Projectile projo = new Projectile(monstres.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
								0.01, "images/proj.png");
	
								dir.setX(hero.getPosition().getX()-monstres.getPosition().getX());
								dir.setY(hero.getPosition().getY()-monstres.getPosition().getY());	
								dir.euclidianNorm();
								projo.getDirection().setX(dir.getX());
								projo.getDirection().setY(dir.getY());
								Projs.add(projo);
	
								proj2Time = System.currentTimeMillis();
	
							}
						
						}
					}
						
					
				
						if(Tears != null){
						for (Monstre monstre : Monstres) {
							for (Tear larme : Tears) {

								//We check if the tear collide with a monster
								if(Physics.rectangleCollision(larme.getPosition(), larme.getSize(), monstre.getPosition(), monstre.getSize())){
									
									//We take out 1 life point to the monster and check if he has 0 life point to remove it
									monstre.setLife(monstre.getLife()-1);
									if(monstre.getLife()==0){
									monstreToRemove.add(monstre);
									}
									tearToRemove.add(larme);
						}
					}	
							for(Projectile proj : Projs){
								if(Physics.rectangleCollision(proj.getPosition(), proj.getSize(), hero.getPosition(), hero.getSize())){
									hero.setLife(hero.getLife()-1);
									projToRemove.add(proj);
								}
							}


								//If the hero is touch by a monster he lose 1 life point
								if(Physics.rectangleCollision(hero.getPosition(), hero.getSize(), monstre.getPosition(), monstre.getSize())){
									hero.setLife(hero.getLife()-1);}
					
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

	public void UpdateRoom(Room newRoom) {
		currentRoom = newRoom;
	}
	
}
