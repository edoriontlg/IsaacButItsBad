package gameWorld;

import java.util.List;
import gameWorld.*;
import gameobjects.*;
import libraries.StdDraw;
import libraries.Vector2;
import resources.*;


public class Shop extends Room{
   
    //Initialise un magasin
    public Shop(Hero hero){
        super(hero);


        ObjectPickable.add(new ObjectOnGround(new Vector2(0.3,0.6), RoomInfos.HALF_TILE_SIZE, ImagePaths.BLOOD_OF_THE_MARTYR));
        ObjectPickable.add(new ObjectOnGround(new Vector2(0.6,0.6), RoomInfos.HALF_TILE_SIZE, ImagePaths.HP_UP));
    }
}
