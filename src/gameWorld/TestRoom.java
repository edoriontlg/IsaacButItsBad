package gameWorld;

import gameobjects.Hero;
import gameobjects.Magasin;
import gameobjects.ObjectOnGround;
import gameobjects.StaticEntity;
import libraries.Vector2;
import monsters.FlyBehavior;
import monsters.GaperBehavior;
import monsters.SpiderBehavior;
import resources.RoomInfos;
import resources.ImagePaths;
import libraries.StdDraw;

public class TestRoom extends Room {

    public TestRoom(Hero hero) {
        super(hero);

		
		
		monstres.add(new FlyBehavior(new Vector2(0.6, 0.8), RoomInfos.TILE_SIZE.scalarMultiplication(0.5), 0.01,
				"images/Fly.png", 3, "fly"));
		monstres.add(new SpiderBehavior(new Vector2(0.6, 0.6), RoomInfos.TILE_SIZE.scalarMultiplication(0.5), 0.07,
				"images/Spider.png", 5, "spider"));
		monstres.add(new GaperBehavior(new Vector2(0.5, 0.5), RoomInfos.TILE_SIZE.scalarMultiplication(1.0), 0.1,
				"images/Gaper.png", 20, "gaper"));
		
    } 
}
