package monsters;

import java.util.List;

import gameobjects.Hero;
import gameobjects.Projectile;
import gameobjects.StaticEntity;
import libraries.Vector2;
import resources.*;

public class GaperBehavior extends Monstre {

    private long lastTimeMove = 0;
    private long lastTimeProj = 0;

    public GaperBehavior(Vector2 position, Vector2 size, double speed, String imagePath, int life) {
        super(position, size, speed, imagePath, life, MONSTER_TYPE.GAPER);
    }

    public void move(List<StaticEntity> entity, Hero hero) {
        if (System.currentTimeMillis() - lastTimeMove > 750 || System.currentTimeMillis() - lastTimeMove < 0) {
            Vector2 directionGaper = new Vector2();

            // We choose with random the direction of the Gaper
            directionGaper.setX(Math.random() * 2 - 1);
            directionGaper.setY(Math.random() * 2 - 1);
            directionGaper.euclidianNormalize(1);

            direction = directionGaper;

            lastTimeMove = System.currentTimeMillis();
        }

        setPosition(position.addVector(direction.scalarMultiplication(speed)));
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