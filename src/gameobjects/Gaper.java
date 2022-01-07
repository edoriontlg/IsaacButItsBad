package gameobjects;

import java.util.List;

import libraries.Vector2;
import resources.*;

public class Gaper extends Monstre {

    private long lastTimeMove = 0;
    private long lastTimeProj = 0;

    public Gaper(Vector2 position, Vector2 size, double speed, String imagePath, int life, String type) {
        super(position, size, speed, imagePath, life, type);
    }

    public void move(List<StaticEntity> entity, Hero hero) {
        if (System.currentTimeMillis() - lastTimeMove > 750 || System.currentTimeMillis() - lastTimeMove < 0) {
            Vector2 directionGaper = new Vector2();
            directionGaper.euclidianNorm();

            // We choose with random the direction of the Gaper
            directionGaper.setX(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
            directionGaper.setY(Math.floor(Math.random() * (1.0 - (-1.0) + 1) + (-1.0)));
            this.getDirection().setX(directionGaper.getX());
            this.getDirection().setY(directionGaper.getY());
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