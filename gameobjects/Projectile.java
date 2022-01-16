package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.DisplaySettings;
import resources.ImagePaths;
import resources.RoomInfos;

public class Projectile {
    private Vector2 position;
    private Vector2 size;
    private String imagePath;
    private double speed;
    private Vector2 direction;

    public Projectile(Vector2 position, Vector2 size, double speed, String imagePath, Vector2 direction) {
        this.position = position;
        this.size = size;
        this.speed = speed;
        this.imagePath = imagePath;
        this.direction = direction;
    }

    public void updateGameObject() {
        move();
    }

    // Fait bouger le projectile en normalisant le vecteur et en le multipliant par
    // sa vitesse
    private void move() {
        Vector2 normalizedDirection = getNormalizedDirection();
        Vector2 newPosition = getPosition().addVector(normalizedDirection.scalarMultiplication(speed));
        setPosition(newPosition);
    }

    // Dessine le projectile
    public void drawGameObject() {
        StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
                0);

        // Hitbox
        if (DisplaySettings.DRAW_DEBUG_INFO) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.rectangle(getPosition().getX(), getPosition().getY(), getSize().getX() / 2d, getSize().getY() / 2d);
        }
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public Vector2 getDirection() {
        return this.direction;
    }

    public Vector2 getNormalizedDirection() {
        Vector2 normalizedVector = new Vector2(direction);
        normalizedVector.euclidianNormalize(speed);
        return normalizedVector;
    }

    /**
     * HELPERS
     * 
     */

    public static Projectile createTear(Vector2 position, Vector2 direction) {
        return new Projectile(position, RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
                0.1, ImagePaths.TEAR, direction);
    }
}
