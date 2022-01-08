package gameWorld;

import gameobjects.Hero;
import gameobjects.ObjectOnGround;
import gameobjects.StaticEntity;
import libraries.Vector2;
import monsters.FlyBehavior;
import resources.RoomInfos;
import resources.HeroInfos;
import resources.ImagePaths;

public class StartRoom extends Room {

    public StartRoom(Hero hero) {
        super(hero);

        
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.5, 0.8), RoomInfos.PICKABLE_SIZE, ImagePaths.HEART_PICKABLE));
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.5, 0.6), RoomInfos.PICKABLE_SIZE, ImagePaths.HALF_HEART_PICKABLE));
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.1, 0.8), RoomInfos.PICKABLE_SIZE, ImagePaths.COIN));
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.2, 0.8), RoomInfos.PICKABLE_SIZE, ImagePaths.NICKEL));
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.7, 0.8), RoomInfos.PICKABLE_SIZE, ImagePaths.DIME));
		StaticEntities.add(new StaticEntity(positionFromTileIndex(5, 5), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
		StaticEntities.add(new StaticEntity(positionFromTileIndex(4, 6), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.8, 0.9), HeroInfos.ISAAC_SIZE, "images/hp_up.png"));
		ObjectPickable.add(new ObjectOnGround(new Vector2(0.2, 0.9), HeroInfos.ISAAC_SIZE, "images/Blood_of_the_martyr.png"));

		monstres.add(new FlyBehavior(new Vector2(0.6, 0.8), RoomInfos.TILE_SIZE.scalarMultiplication(0.5), 0.01,
		"images/Fly.png", 3, "fly"));	}
    
}
