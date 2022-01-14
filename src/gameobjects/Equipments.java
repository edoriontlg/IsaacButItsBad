package gameobjects;

import libraries.Vector2;

public class Equipments {
    private Vector2 position;
	private Vector2 size;
	private String imagePath;
    private String type;

    public Equipments(Vector2 position, Vector2 size, String imagePath, String type){
        this.type = type;
        this.size = size;
        this.imagePath = imagePath;
        this.position = position;
    }

    //Retourne le type d'équipement 
    public String getType(){
        return this.type;
    }
}
