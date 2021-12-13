package gameobjects;

import libraries.StdDraw;

import java.util.List;

import libraries.Physics;
import libraries.Vector2;
import resources.DisplaySettings;
import resources.ImagePaths;

public class Hero {
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	private double speed;
	private Vector2 direction;
	private int life;

	private Vector2 lastPosition;
	private Vector2 lastNormalizedDirection;

	public Hero(Vector2 position, Vector2 size, double speed, String imagePath, int life) {
		this.position = position;
		this.size = size;
		this.speed = speed;
		this.imagePath = imagePath;
		this.direction = new Vector2();
		this.life = life;
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

	public void processPhysics(List<StaticEntity> entitiesToCollide) {
		// Yes, by doing this if two static entities are to close to each other
		// you will completely ignore the physics of one of them.
		// Just dont put objects to close.
		boolean isMoveValid = true;

		if (entitiesToCollide != null) {

			for (StaticEntity entity : entitiesToCollide) {

				if (entity == null)
					continue;

				// Movement correction (yes, we could technically do this directly inside the
				// move function
				// But I find it easier if we put every physic check inside the same function).
				isMoveValid = !Physics.rectangleCollision(getPosition(), getSize(), entity.getPosition(),
						entity.getSize());
				if (!isMoveValid) {

					// Extract X and Y dir
					Vector2 lastXMovement = new Vector2(lastNormalizedDirection.getX(), 0);
					Vector2 lastYMovement = new Vector2(0, lastNormalizedDirection.getY());

					// The corrected movement we will use (because last one was incorrect, cf If
					// statement)
					Vector2 correctedMovement = new Vector2(0, 0);

					// Check if X movement is valid, if yes add it to the corrected movement
					if (!Physics.rectangleCollision(lastPosition.addVector(lastXMovement), getSize(),
							entity.getPosition(),
							entity.getSize())) {
						correctedMovement = correctedMovement.addVector(lastXMovement);
					}

					// Same for Y
					if (!Physics.rectangleCollision(lastPosition.addVector(lastYMovement), getSize(),
							entity.getPosition(),
							entity.getSize())) {
						correctedMovement = correctedMovement.addVector(lastYMovement);
					}

					// We correct the last position
					Vector2 correctedPosition = lastPosition.addVector(correctedMovement);

					// Set it
					setPosition(correctedPosition);
					break;
				}
			}

			// We get the current pos
			Vector2 newPos = new Vector2(getPosition());

			// We check if it's valid (top and bottom) and if not we correct it
			if (getPosition().getX() + (getSize().getX() / 2) > 1d) {
				newPos.setX(1d - (getSize().getX() / 2));
			} else if (getPosition().getX() - (getSize().getX() / 2) < 0d) {
				newPos.setX(0d + (getSize().getX() / 2));
			}

			// We check if it's valid (left and right) and if not we correct it
			if (getPosition().getY() + (getSize().getX() / 2) > 1d) {
				newPos.setY(1d - (getSize().getX() / 2));
			} else if (getPosition().getY() - (getSize().getX() / 2) < 0d) {
				newPos.setY(0d + (getSize().getX() / 2));
			}

			setPosition(newPos);
		}
	}

	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);

		if (DisplaySettings.DRAW_DEBUG_INFO) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(0.15, 0.95, "x:"
					+ Math.round(getPosition().getX() * 1000d) / 1000d
					+ ", y:"
					+ Math.round(getPosition().getY() * 1000d) / 1000d);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.rectangle(getPosition().getX(), getPosition().getY(), getSize().getX() / 2d, getSize().getY() / 2d);
		}

		if (DisplaySettings.DRAW_DEBUG_INFO) {
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.setPenRadius(0.004);

			StdDraw.line(getPosition().getX(),
					getPosition().getY(),
					getPosition().getX() + getPosition().subVector(lastPosition).getX(),
					getPosition().getY() + getPosition().subVector(lastPosition).getY());
		}

		// If health not empty, draw hearts, else draw empty heart
		if (this.life != 0) {
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
		} else {
			StdDraw.picture(0.05, 0.95, ImagePaths.EMPTY_HEART_HUD);
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
}
