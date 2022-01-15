package gameWorld;

import java.util.List;
import gameWorld.*;
import gameobjects.*;
import libraries.StdDraw;
import libraries.Vector2;
import monsters.GaperBehavior;
import resources.*;

public class BossRoom extends Room {


    //Initialise la salle de boss avec un Gaper
    public BossRoom(Hero hero) {
        super(hero);

        monstres.add(new GaperBehavior(new Vector2(0.5, 0.5), MonstersInfo.GAPER_SIZE, MonstersInfo.GAPER_SPEED,
                ImagePaths.GAPER, MonstersInfo.GAPER_LIFE));
    }
}
