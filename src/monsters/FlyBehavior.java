package monsters;

import java.util.List;

import gameobjects.Hero;
import gameobjects.Projectile;
import gameobjects.StaticEntity;
import libraries.Vector2;
import resources.RoomInfos;
import resources.MonstersInfo;

public class FlyBehavior extends Monstre {

    public static long projectileLifetime = 0;

    public FlyBehavior(Vector2 position, Vector2 size, double speed, String imagePath, int life, String type) {
        super(position, size, speed, imagePath, life, type);
    }

    public void move(List<StaticEntity> entities, Hero hero) {
        Vector2 heroPos = hero.getPosition();

        // We create a Vector in the direction of the hero
        Vector2 directionFly = new Vector2(
                heroPos.getX() - getPosition().getX(),
                heroPos.getY() - getPosition().getY());
        directionFly.euclidianNorm();
        Vector2 positionAfterMoving = getPosition().addVector(directionFly.scalarMultiplication(speed));

        setPosition(positionAfterMoving);
    }

    public void attack(List<Projectile> projectiles, Hero hero) {

        if (System.currentTimeMillis() - projectileLifetime > 1700
                || System.currentTimeMillis() - projectileLifetime < 0) {

            Vector2 heroPos = hero.getPosition();
            // We create a Vector in the direction fo the hero
            Vector2 directionProj = new Vector2(
                    heroPos.getX() - getPosition().getX(),
                    heroPos.getY() - getPosition().getY());
            directionProj.euclidianNorm();

            Projectile projo = new Projectile(this.position, RoomInfos.TILE_SIZE.scalarMultiplication(0.4),
                   MonstersInfo.FLY_PROJ_SPEED, "images/tear.png", directionProj);

            projectiles.add(projo);

            projectileLifetime = System.currentTimeMillis();
        }
    }
}
