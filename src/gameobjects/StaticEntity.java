package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.DisplaySettings;

public class StaticEntity {
	public String imagePath;
	public Vector2 position;
	public Vector2 size;
	
	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);

		if (DisplaySettings.IS_DEBUG_MODE) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.rectangle(getPosition().getX(), getPosition().getY(),  getSize().getX() / 2d,  getSize().getY() / 2d);
		}

	}

	public StaticEntity(Vector2 position, Vector2 size, String imagePath) {
		this.imagePath = imagePath;
		this.position = position;
		this.size = size;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

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
}
