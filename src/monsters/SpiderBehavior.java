package monsters;

import java.util.List;

import gameWorld.Room;
import gameobjects.Hero;
import gameobjects.Projectile;
import gameobjects.StaticEntity;
import libraries.Vector2;
import resources.MonstersInfo;
import resources.RoomInfos;

public class SpiderBehavior extends Monstre {

    private long lastTime = 0;

    public SpiderBehavior(Vector2 position, Vector2 size, double speed, String imagePath, int life) {
        super(position, size, speed, imagePath, life, MONSTER_TYPE.SPIDER);
    }

    public void move(List<StaticEntity> entity, Hero hero) {

        // We use a timer to make the spider pause between movement
        if (System.currentTimeMillis() - lastTime < MonstersInfo.SPIDER_MOVE_TIME) {

            Vector2 newPos = position.addVector(direction.scalarMultiplication(speed));
            if (newPos.getX() + getSize().getX() > RoomInfos.maxHorizontal
                    || newPos.getY() + getSize().getY() > RoomInfos.maxVertical
                    || newPos.getX() - getSize().getX() < RoomInfos.minHorizontal
                    || newPos.getY() - getSize().getY() < RoomInfos.minVertical)
                return;
    
            setPosition(newPos);
        }
        else if (System.currentTimeMillis() - lastTime > MonstersInfo.SPIDER_MOVE_TIME + MonstersInfo.SPIDER_PAUSE_TIME) {

            
            Vector2 directionSpider = new Vector2();

            // We choose with random the direction of the spider
            directionSpider.setX(Math.random() * 2 - 1);
            directionSpider.setY(Math.random() * 2 - 1);
            directionSpider.euclidianNormalize(1.0);

            direction = directionSpider;


            lastTime = System.currentTimeMillis();
        }
    }

    public void attack(List<Projectile> projectiles, Hero hero) {

    }
}
