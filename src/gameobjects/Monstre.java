package gameobjects;

import libraries.StdDraw;


import gameWorld.Room;
import libraries.Physics;
import libraries.Vector2;
import resources.DisplaySettings;
import resources.ImagePaths;
import resources.RoomInfos;

public class Monstre {
    private Vector2 position;
	private Vector2 size;
	private String imagePath;
	private double speed;
	Vector2 direction;
	private int life;
    protected String type;

    private Vector2 lastPosition;
	private Vector2 lastNormalizedDirection;

    public Monstre(Vector2 position, Vector2 size, double speed, String imagePath, int life, String type) {
		this.position = position;
		this.size = size;
		this.speed = speed;
		this.imagePath = imagePath;
		this.direction = new Vector2();
		this.life = life;
        this.type = type;
	}

    public void updateGameObject() {
		move();
	}

	private void move() {
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		lastPosition = getPosition();
		setPosition(positionAfterMoving);
		lastNormalizedDirection = normalizedDirection;
		direction = new Vector2();
	}

    public void drawGameObject() {
        StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
                0);

        //Hitbox
        if (DisplaySettings.DRAW_DEBUG_INFO) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.rectangle(getPosition().getX(), getPosition().getY(), getSize().getX() / 2d, getSize().getY() / 2d);} 
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
    public String getType(){
        return this.type;
    }
}
