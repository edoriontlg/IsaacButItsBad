package gameWorld;

import java.util.Random;

import gameobjects.Hero;
import libraries.Vector2;
import monsters.Monstre;
import monsters.Monstre.MONSTER_TYPE;

public class MonsterRoom extends Room {

    public MonsterRoom(Hero hero, String type) {
        super(hero, type);	
        
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
