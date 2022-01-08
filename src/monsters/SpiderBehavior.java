package monsters;

import java.util.List;

import gameobjects.Hero;
import gameobjects.Projectile;
import gameobjects.StaticEntity;
import libraries.Vector2;

public class SpiderBehavior extends Monstre {

    private long lastTime = 0;

    public SpiderBehavior(Vector2 position, Vector2 size, double speed, String imagePath, int life) {
        super(position, size, speed, imagePath, life, MONSTER_TYPE.SPIDER);
    }

    public void move(List<StaticEntity> entity, Hero hero) {
        
        // We use a timer to make the spider pause between movement
        if (System.currentTimeMillis() - lastTime > 750 || System.currentTimeMillis() - lastTime < 0) {
            Vector2 directionSpider = new Vector2();

            // We choose with random the direction of the spider
            directionSpider.setX(Math.random() * 2 - 1);
            directionSpider.setY(Math.random() * 2 - 1);
            directionSpider.euclidianNormalize(1.0);

            direction = directionSpider;

            lastTime = System.currentTimeMillis();
        }

        setPosition(position.addVector(direction.scalarMultiplication(speed)));
    }

    public void attack(List<Projectile> projectiles, Hero hero) {

    }
}
