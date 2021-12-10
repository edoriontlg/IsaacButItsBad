package gameobjects;

import libraries.StdDraw;

import java.io.Console;

import libraries.Physics;
import libraries.Vector2;
import resources.DisplaySettings;
import resources.HeroInfos;

public class Hero
{
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	private double speed;
	private Vector2 direction;

	private Vector2 lastPosition = position;
	private Vector2 lastDirection = direction;


	public Hero(Vector2 position, Vector2 size, double speed, String imagePath)
	{
		this.position = position;
		this.size = size;
		this.speed = speed;
		this.imagePath = imagePath;
		this.direction = new Vector2();
	}

	public void updateGameObject()
	{
		move();
	}

	private void move()
	{
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		lastPosition = getPosition();
		lastDirection = normalizedDirection;
		setPosition(positionAfterMoving);
		direction = new Vector2();
	}

	public void processPhysics(StaticEntity[] entitiesToCollide) {
		//We get the current pos
		Vector2 newPos = getPosition();

		//We check if it's valid (top and bottom) and if not we correct it
		if (getPosition().getX() + (getSize().getX() / 2) > 1d) {
			newPos.setX(1d - (getSize().getX() / 2));
		} else if (getPosition().getX() - (getSize().getX() / 2) < 0d) {
			newPos.setX(0d + (getSize().getX() / 2));
		}
		
		//We check if it's valid (left and right) and if not we correct it
		if (getPosition().getY() + (getSize().getX() / 2) > 1d) {
			newPos.setY(1d - (getSize().getX() / 2));
		} else if (getPosition().getY() - (getSize().getX() / 2) < 0d) {
			newPos.setY(0d + (getSize().getX() / 2));
		}
		
		boolean isMoveValid = true;
		for (StaticEntity entity : entitiesToCollide) {
			/*
			if (Physics.rectangleCollision(getPosition(), getSize(), entity.getPosition(), entity.getSize())) {
				

				OLD CODE, resets the position and move it back but we can do everything simpler by just not making any new movement
				Vector2 pos1 = getPosition();
				Vector2 size1 = getSize();
				Vector2 pos2 = entity.getPosition();
				Vector2 size2 = entity.getSize();
				
				getPosition().addX(-Math.max(pos1.getX() + (size1.getX() / 2) + HeroInfos.AUTHORIZED_OVERLAP - pos2.getX() + (size2.getX() / 2), 0d));
				getPosition().addY(-Math.max(pos1.getY() + (size1.getY() / 2) + HeroInfos.AUTHORIZED_OVERLAP - pos2.getY() + (size2.getY() / 2), 0d));
				getPosition().addX(-Math.max(pos1.getX() - (size1.getX() / 2) - HeroInfos.AUTHORIZED_OVERLAP - pos2.getX() + (size2.getX() / 2), 0d));
				getPosition().addY(-Math.max(pos1.getY() - (size1.getY() / 2) - HeroInfos.AUTHORIZED_OVERLAP - pos2.getY() + (size2.getY() / 2), 0d));
			
			}	*/

			isMoveValid = !Physics.rectangleCollision(getPosition(), getSize(), entity.getPosition(), entity.getSize());
			if (!isMoveValid) {
				//We get the last movement direction we got
				Vector2 lastMove = lastDirection;

				//Check both X and Y positions after move individually
				Vector2 posAfterX = lastPosition;
				posAfterX.addX(lastMove.getX());
				Vector2 posAfterY = lastPosition;
				posAfterY.addY(lastMove.getY());

				
				Vector2 correctedMove = new Vector2(0, 0);
				System.out.println(posAfterX);
				System.out.println(posAfterY);
				if(!Physics.rectangleCollision(posAfterX, getSize(), entity.getPosition(), entity.getSize())) {
					correctedMove.addX(lastMove.getX());
				}

				if(!Physics.rectangleCollision(posAfterY, getSize(), entity.getPosition(), entity.getSize())) {
					correctedMove.addY(lastMove.getY());
				}

				Vector2 correctedPosition = lastPosition;
				setPosition(correctedPosition.addVector(correctedMove));
				
				break;
			}
		}
	}
	
	public void drawGameObject()
	{
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);
		
		if (DisplaySettings.IS_DEBUG_MODE) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.text(0.15, 0.95, "x:" 
				+ Math.round(getPosition().getX() * 1000d ) / 1000d 
				+ ", y:" 
				+ Math.round(getPosition().getY() * 1000d ) / 1000d);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.rectangle(getPosition().getX(), getPosition().getY(),  getSize().getX() / 2d,  getSize().getY() / 2d);
		}
	}

	/*
	 * Moving from key inputs. Direction vector is later normalised.
	 */
	public void goUpNext()
	{
		getDirection().addY(1);
	}

	public void goDownNext()
	{
		getDirection().addY(-1);
	}

	public void goLeftNext()
	{
		getDirection().addX(-1);
	}

	public void goRightNext()
	{
		getDirection().addX(1);
	}

	public Vector2 getNormalizedDirection()
	{
		Vector2 normalizedVector = new Vector2(direction);
		normalizedVector.euclidianNormalize(speed);
		return normalizedVector;
	}


	/*
	 * Getters and Setters
	 */
	public Vector2 getPosition()
	{
		return position;
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	public Vector2 getSize()
	{
		return size;
	}

	public void setSize(Vector2 size)
	{
		this.size = size;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	public Vector2 getDirection()
	{
		return direction;
	}

	public void setDirection(Vector2 direction)
	{
		this.direction = direction;
	}
}
