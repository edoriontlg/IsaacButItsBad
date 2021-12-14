package gameobjects;

import libraries.StdDraw;

import java.util.Vector;

import gameobjects.Hero;
import libraries.Physics;
import libraries.Vector2;
import resources.DisplaySettings;
import resources.ImagePaths;

public class ObjetSol {

    private Vector2 position;
	private Vector2 size;
	private String imagePath;

    public ObjetSol(Vector2 position, Vector2 size, String imagePath){
        this.position = position;
        this.size = size;
        this.imagePath = imagePath;
    }


    public void drawGameObject() {
        StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
                0);

        if (DisplaySettings.DRAW_DEBUG_INFO) {
                 StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.002);
                StdDraw.rectangle(getPosition().getX(), getPosition().getY(),  getSize().getX() / 2d,  getSize().getY() / 2d);
                }
    }
    
    

   



    public Vector2 getPosition(){
        return this.position;
    }

    public String getImagePath(){
        return this.imagePath;
    }

    public Vector2 getSize(){
        return this.size;
    }





}
