package gameWorld;

import gameobjects.Hero;
import libraries.Vector2;
import monsters.Monstre;
import monsters.Monstre.MONSTER_TYPE;

public class TestRoom extends Room {

    public TestRoom(Hero hero, String type) {
        super(hero, type);	
        
        monstres.add(Monstre.CreateMonster(new Vector2(0.5, 0.5), MONSTER_TYPE.FLY));
        monstres.add(Monstre.CreateMonster(new Vector2(0.5, 0.5), MONSTER_TYPE.GAPER));
        monstres.add(Monstre.CreateMonster(new Vector2(0.5, 0.5), MONSTER_TYPE.SPIDER));
    } 
}
