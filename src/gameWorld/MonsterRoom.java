package gameWorld;

import java.util.Random;

import gameobjects.*;
import libraries.Vector2;
import monsters.Monstre;
import monsters.Monstre.MONSTER_TYPE;
import resources.*;

public class MonsterRoom extends Room {

    //Initialise une salle avec des monstres aléatoires
    public MonsterRoom(Hero hero, String type) {
        super(hero, type);
        
        StaticEntities.add(new StaticEntity(positionFromTileIndex(2, 5), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
		StaticEntities.add(new StaticEntity(positionFromTileIndex(3, 8), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
        StaticEntities.add(new StaticEntity(positionFromTileIndex(8, 5), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
		StaticEntities.add(new StaticEntity(positionFromTileIndex(5, 3), RoomInfos.TILE_SIZE, ImagePaths.ROCK));
        
        for(int i = 0 ; i < 4; i++){
            int monstre = new Random().nextInt(2);
            if(monstre==1){
                monstres.add(Monstre.CreateMonster(new Vector2(0.5, 0.5), MONSTER_TYPE.FLY));
            }
            else{
                monstres.add(Monstre.CreateMonster(new Vector2(0.5, 0.5), MONSTER_TYPE.SPIDER));
            }
        }
    } 
}
