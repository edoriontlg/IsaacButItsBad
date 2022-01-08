package monsters;

import java.util.List;

import gameobjects.Hero;
import gameobjects.Projectile;
import gameobjects.StaticEntity;
import libraries.Vector2;

public class SpiderBehavior extends Monstre {

    private long lastTime = 0;

    public SpiderBehavior(Vector2 position, Vector2 size, double speed, String imagePath, int life, String type) {
        super(position, size, speed, imagePath, life, type);
    }

    public void move(List<StaticEntity> entity, Hero hero) {
        
        // We use a timer to make the spider pause between movement
        if (System.currentTimeMillis() - lastTime > 750 || System.currentTimeMillis() - lastTime < 0) {
            Vector2 directionSpider = new Vector2();

            // We choose with random the direction of the spider
            directionSpider.setX(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
            directionSpider.setY(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
            directionSpider.euclidianNorm();
            this.getDirection().setX(directionSpider.getX());
            this.getDirection().setY(directionSpider.getY());
            lastTime = System.currentTimeMillis();
        }
    }

    public void attack(List<Projectile> projectiles, Hero hero) {

    }
}
