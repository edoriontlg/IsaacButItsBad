package monsters;

import java.io.Console;
import java.io.ObjectInputStream.GetField;
import java.util.List;

import gameobjects.Hero;
import gameobjects.Projectile;
import gameobjects.StaticEntity;
import libraries.Physics;
import libraries.Vector2;
import resources.*;

public class GaperBehavior extends Monstre {

    private long lastTimeMove = 0;
    private long lastTimeProj = 0;

    public GaperBehavior(Vector2 position, Vector2 size, double speed, String imagePath, int life) {
        super(position, size, speed, imagePath, life, MONSTER_TYPE.GAPER);
    }

    public void move(List<StaticEntity> entity, Hero hero) {

        // We use a timer to make the spider pause between movement
        if (System.currentTimeMillis() - lastTimeMove < MonstersInfo.GAPER_MOVE_TIME) {

            Vector2 newPos = position.addVector(direction.scalarMultiplication(speed));
            if (newPos.getX() + getSize().getX() > RoomInfos.maxHorizontal
                    || newPos.getY() + getSize().getY() > RoomInfos.maxVertical
                    || newPos.getX() - getSize().getX() < RoomInfos.minHorizontal
                    || newPos.getY() - getSize().getY() < RoomInfos.minVertical)
                return;
    
            setPosition(newPos);
        }
        else if (System.currentTimeMillis() - lastTimeMove > MonstersInfo.GAPER_MOVE_TIME + MonstersInfo.GAPER_PAUSE_TIME) {

            
            Vector2 directioGaper = new Vector2();

            // We choose with random the direction of the spider
            directioGaper.setX(Math.random() * 2 - 1);
            directioGaper.setY(Math.random() * 2 - 1);
            directioGaper.euclidianNormalize(1.0);

            direction = directioGaper;


            lastTimeMove = System.currentTimeMillis();
        }
    }

    public void attack(List<Projectile> projectiles, Hero hero) {

        if (System.currentTimeMillis() - lastTimeProj > 700 || System.currentTimeMillis() - lastTimeProj < 0) {
            Vector2 dir = new Vector2();
            dir.setX(hero.getPosition().getX() - this.getPosition().getX());
            dir.setY(hero.getPosition().getY() - this.getPosition().getY());
            dir.euclidianNorm();

            Projectile projo = new Projectile(this.getPosition(),
                    RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
                    MonstersInfo.GAPER_PROJ_SPEED, "images/proj.png", dir);
            projectiles.add(projo);

            lastTimeProj = System.currentTimeMillis();
        }
    }
}