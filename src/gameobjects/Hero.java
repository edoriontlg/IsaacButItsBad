package gameobjects;

import libraries.StdDraw;

import java.util.ArrayList;
import java.util.List;

import gameWorld.Room;
import libraries.Physics;
import libraries.Vector2;
import resources.DisplaySettings;
import resources.ImagePaths;
import resources.RoomInfos;
import gameobjects.Equipments;

public class Hero {
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	private double speed;
	private Vector2 direction;
	private int life;
	private boolean lifeMax = false;
	private int money;

	public Hero(Vector2 position, Vector2 size, double speed, String imagePath, int life, int money) {
		this.position = position;
		this.size = size;
		this.speed = speed;
		this.imagePath = imagePath;
		this.direction = new Vector2();
		this.life = life;
		this.money = money;
	}

	public void updateGameObject(List<StaticEntity> entities) {
		move(entities);
	}

	private void move(List<StaticEntity> entities) {
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		for (StaticEntity entity : entities) {
			if (Physics.rectangleCollision(positionAfterMoving, size, entity.position, entity.size)) {
				direction = new Vector2();
				return;
			}
		}

		double halfSize = size.getX()/2;
		double tileHalfSize = RoomInfos.HALF_TILE_SIZE.getX();

		if (positionAfterMoving.getX() + halfSize > Room.positionFromTileIndex(RoomInfos.NB_TILES -1, 0).getX() - tileHalfSize) {
			direction = new Vector2();
			return;
		}

		if (positionAfterMoving.getX() - halfSize < Room.positionFromTileIndex(1, 0).getX() - tileHalfSize) {
			direction = new Vector2();
			return;
		}

		if (positionAfterMoving.getY() + halfSize > Room.positionFromTileIndex(0, RoomInfos.NB_TILES-1).getY() - tileHalfSize) {
			direction = new Vector2();
			return;
		}

		if (positionAfterMoving.getY() - halfSize < Room.positionFromTileIndex(0, 1).getY() - tileHalfSize) {
			direction = new Vector2();
			return;
		}

		setPosition(positionAfterMoving);
		direction = new Vector2();
	}

	public void processPhysics(List<ObjectOnGround> objectsToCollide) {
		//We need to do this one first, if done after it could 
		//Make us go into the walls
		processPhysicsObjets(objectsToCollide);
	}

	//Collision with objects like hearts, gold, etc
	public void processPhysicsObjets(List<ObjectOnGround> objectsToCollide) {
		if (objectsToCollide != null) {
			//We store the object we will need to delete
			List<ObjectOnGround> objToRemove = new ArrayList<ObjectOnGround>();

			//For each, if collision we do something then delete it
			for (ObjectOnGround obj : objectsToCollide) {
				if (obj != null && Physics.rectangleCollision(getPosition(), getSize(), obj.getPosition(), obj.getSize())) {
					if(obj.getImagePath()=="images/hp_up.png"){
						this.life = 8;
						this.lifeMax =true;
						objToRemove.add(obj);
					}
					if(obj.getImagePath()=="images/Red_Heart.png"){
						if(this.life<5 || obj.getImagePath()=="images/hp_up.png" && this.life<7){
							this.life += 2;
							objToRemove.add(obj);}
				}
					else if(obj.getImagePath()=="images/Half_Red_Heart.png"){
						if(this.life<6 || obj.getImagePath()=="images/hp_up.png" && this.life<8){
							this.life += 1;
							objToRemove.add(obj);}
					}

					else if(obj.getImagePath()=="images/Penny.png"){
							this.money += 1;
							objToRemove.add(obj);}
					else if(obj.getImagePath()=="images/Nickel.png"){
							this.money += 5;
							objToRemove.add(obj);}
					else if(obj.getImagePath()=="images/Dime.png"){
							this.money += 10;
							objToRemove.add(obj);}
					
				}
			}
			

			//We ACTUALLY remove it (not inside the first loop, it messes up everything)
			for (ObjectOnGround obj : objToRemove) {
				objectsToCollide.remove(obj);
			}
		}
		}
	


	
	

	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.text(0.1,0.9, this.money+"");
				
		if (DisplaySettings.DRAW_DEBUG_INFO) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(0.15, 0.95, "x:"
					+ Math.round(getPosition().getX() * 1000d) / 1000d
					+ ", y:"
					+ Math.round(getPosition().getY() * 1000d) / 1000d);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.rectangle(getPosition().getX(), getPosition().getY(), getSize().getX() / 2d, getSize().getY() / 2d);

			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.setPenRadius(0.004);
		}

		// If health not empty, draw hearts, else draw empty heart
		if (this.life > 0) {
			// One full heart = 2 life

			int fullHearts = (this.life - this.life % 2) / 2;
			boolean HalfHeart = this.life % 2 == 1;

			for (double i = 0; i < fullHearts; i++) {
				StdDraw.picture(0.05 * (i + 1), 0.95, ImagePaths.HEART_HUD);
			}

			// If health not even we draw the half heart
			if (HalfHeart) {
				StdDraw.picture(0.05 * (this.life / 2 + 1), 0.95, ImagePaths.HALF_HEART_HUD);
			}

			if(this.life <7 && lifeMax){
				StdDraw.picture(0.2, 0.95, ImagePaths.EMPTY_HEART_HUD);
			}

			if(this.life<5){
				StdDraw.picture(0.15, 0.95, ImagePaths.EMPTY_HEART_HUD);
			}
			if(this.life<3){
				StdDraw.picture(0.1, 0.95, ImagePaths.EMPTY_HEART_HUD);
			}

		}
		else {
			StdDraw.picture(0.05, 0.95, ImagePaths.EMPTY_HEART_HUD);
			StdDraw.picture(0.1, 0.95, ImagePaths.EMPTY_HEART_HUD);
			StdDraw.picture(0.15, 0.95, ImagePaths.EMPTY_HEART_HUD);

		}
	

	}

	/*
	 * Moving from key inputs. Direction vector is later normalised.
	 */
	public void goUpNext() {
		getDirection().addY(1);
	}

	public void goDownNext() {
		getDirection().addY(-1);
	}

	public void goLeftNext() {
		getDirection().addX(-1);
	}

	public void goRightNext() {
		getDirection().addX(1);
	}

	public Vector2 getNormalizedDirection() {
		Vector2 normalizedVector = new Vector2(direction);
		normalizedVector.euclidianNormalize(speed);
		return normalizedVector;
	}

	/*
	 * Getters and Setters
	 */
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public int getLife() {
		return this.life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	
}
