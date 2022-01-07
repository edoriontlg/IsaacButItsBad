package gameobjects;
import java.util.List;

import gameWorld.Room;
import gameobjects.*;
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

public class Magasin {
    private List<ObjectOnGround> magasin;
    private Room currentRoom;
    

    public Magasin(List<ObjectOnGround> magasin, Room room){
        this.magasin = magasin;
        currentRoom = room;
        
    }

    public static void initializeShop(List<ObjectOnGround> magasin){
        magasin.add(new ObjectOnGround(new Vector2(0.6, 0.7), HeroInfos.ISAAC_SIZE, "images/hp_up.png"));
        magasin.add(new ObjectOnGround(new Vector2(0.4, 0.7), HeroInfos.ISAAC_SIZE, "images/Blood_of_the_martyr.png"));

    }

    


}
