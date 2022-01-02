package gameobjects;

import libraries.Vector2;

public class Gaper extends Monstre{

    public Gaper(Vector2 position, Vector2 size, double speed, String imagePath, int life, String type) {
        super(position, size, speed, imagePath, life, type);
    }
    
    public void move(){

    }

    @Override
    public String getType(){
        return this.type;
    }
}