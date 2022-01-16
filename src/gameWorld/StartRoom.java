package gameWorld;


import gameobjects.*;
import resources.*;

public class StartRoom extends Room {

	//Initialise la salle de départ
	public StartRoom(Hero hero) {
		super(hero);

		StaticEntities.add(new StaticEntity(RoomInfos.positionFromTileIndex(5, 5), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
		StaticEntities.add(new StaticEntity(RoomInfos.positionFromTileIndex(4, 6), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
	}

}
