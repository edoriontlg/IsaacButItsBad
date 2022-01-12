package gameWorld;

import java.util.List;
import gameWorld.*;
import gameobjects.*;
import libraries.StdDraw;
import libraries.Vector2;
import resources.*;


public class Shop extends Room{
   

    public Shop(Hero hero, String type){
        super(hero, type);


        ObjectPickable.add(new ObjectOnGround(new Vector2(0.3,0.6), RoomInfos.HALF_TILE_SIZE, "images/Blood_of_the_martyr.png"));
        ObjectPickable.add(new ObjectOnGround(new Vector2(0.6,0.6), RoomInfos.HALF_TILE_SIZE, "images/hp_up.png"));
        
        
    }

    public static void drawShop(){
        StdDraw.picture(0.3, 0.55, ImagePaths.COIN);
        StdDraw.picture(0.6, 0.55, ImagePaths.COIN);
        StdDraw.picture(0.45, 0.7, "Cain.png", 0.1, 0.1, 0);
        StdDraw.text(0.25, 0.55, "10");
        StdDraw.text(0.55, 0.55, "10");
    }
    



    
}
