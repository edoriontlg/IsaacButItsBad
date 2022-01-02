package gameWorld;

import gameobjects.Fly;
import gameobjects.Gaper;
import gameobjects.Hero;
import gameobjects.ObjectOnGround;
import gameobjects.Spider;
import gameobjects.StaticEntity;
import libraries.Vector2;
import resources.RoomInfos;
import resources.ImagePaths;

public class TestRoom extends Room {

    public TestRoom(Hero hero) {
        super(hero);


		Monstres.add(new Fly(new Vector2(0.6, 0.8), RoomInfos.TILE_SIZE.scalarMultiplication(0.5), 0.01 / 8,
				"images/Fly.png", 3, "fly"));
		Monstres.add(new Spider(new Vector2(0.6, 0.6), RoomInfos.TILE_SIZE.scalarMultiplication(0.5), 0.07,
				"images/Spider.png", 5, "spider"));
		Monstres.add(new Gaper(new Vector2(0.5, 0.5), RoomInfos.TILE_SIZE.scalarMultiplication(1.0), 0.1,
				"images/Gaper.png", 20, "gaper"));
    } 
}
