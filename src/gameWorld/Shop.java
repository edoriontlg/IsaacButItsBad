package gameWorld;

import java.util.List;
import gameWorld.*;
import gameobjects.*;
import libraries.StdDraw;
import libraries.Vector2;
import resources.*;


public class Shop extends Room{

    private ObjectOnGround objOne = new ObjectOnGround(new Vector2(0.3,0.6), RoomInfos.HALF_TILE_SIZE, ImagePaths.BLOOD_OF_THE_MARTYR);
    private ObjectOnGround objTwo = new ObjectOnGround(new Vector2(0.6,0.6), RoomInfos.HALF_TILE_SIZE, ImagePaths.HP_UP);
   
    //Initialise un magasin
    public Shop(Hero hero){
        super(hero);


        ObjectPickable.add(objOne);
        ObjectPickable.add(objTwo);
        
    }

    @Override
    public void drawRoom() {
        super.drawRoom();
        StdDraw.picture(0.5, 0.7, "Cain.png");

        if (ObjectPickable.contains(objOne)){
            StdDraw.picture(0.35, 0.55, ImagePaths.PIECE, 0.03, 0.03);
            StdDraw.picture(0.30, 0.55, ImagePaths.DIX, 0.03, 0.03);
        }

        if (ObjectPickable.contains(objTwo)){
            StdDraw.picture(0.65, 0.55, ImagePaths.PIECE, 0.03, 0.03);
            StdDraw.picture(0.60, 0.55, ImagePaths.DIX, 0.03, 0.03);
        }

    }


}
