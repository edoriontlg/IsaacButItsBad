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
        ObjectPickable.add(new ObjectOnGround(new Vector2(0.3, 0.55), RoomInfos.HALF_TILE_SIZE, ImagePaths.COIN));
        ObjectPickable.add(new ObjectOnGround(new Vector2(0.45, 0.7), RoomInfos.HALF_TILE_SIZE,"Cain.png"));
        ObjectPickable.add(new ObjectOnGround(new Vector2(0.3, 0.55), new Vector2(0.03,0.03),ImagePaths.PIECE));
        ObjectPickable.add(new ObjectOnGround(new Vector2(0.6, 0.55),new Vector2(0.03,0.03),ImagePaths.PIECE));
        StdDraw.text(0.25, 0.55, "10");
        StdDraw.text(0.55, 0.55, "10");
    }
}
