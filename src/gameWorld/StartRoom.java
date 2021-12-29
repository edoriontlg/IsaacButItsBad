package gameWorld;

import gameobjects.Hero;
import gameobjects.ObjectOnGround;
import gameobjects.StaticEntity;
import libraries.Vector2;
import resources.RoomInfos;
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
		StaticEntities.add(new StaticEntity(positionFromTileIndex(8, 2), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
    }
    
}
