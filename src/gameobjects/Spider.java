package gameobjects;

import java.util.List;

import libraries.Vector2;

public class Spider extends Monstre {

    private long lastTime = 0;

    public Spider(Vector2 position, Vector2 size, double speed, String imagePath, int life, String type) {
        super(position, size, speed, imagePath, life, type);
    }

    public void move(List<StaticEntity> entity, Hero hero) {

        // We use a timer to make the spider pause between movement
        if (System.currentTimeMillis() - lastTime > 750 || System.currentTimeMillis() - lastTime < 0) {
            Vector2 directionSpider = new Vector2();
            directionSpider.euclidianNorm();

            // We choose with random the direction of the spider
            directionSpider.setX(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
            directionSpider.setY(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
            this.getDirection().setX(directionSpider.getX());
            this.getDirection().setY(directionSpider.getY());
            lastTime = System.currentTimeMillis();
        }
    }

    public void attack(List<Projectile> projectiles, Hero hero) {

    }
}
