package gameWorld;

import java.util.ArrayList;
import java.util.List;

import gameobjects.*;
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.Controls;
import resources.HeroInfos;
import resources.RoomInfos;

public class GameWorld
{
	private Room currentRoom;
	private Hero hero;
	private List<Tear> Tears = new ArrayList<Tear>();
	private long lastTime = 0;

	// A world needs a hero
	public GameWorld(Hero hero)
	{
		this.hero = hero;
		currentRoom = new Room(hero);
	}

	public void processUserInput()
	{
		processKeysForMovement();
		processKeysForShooting();
	}

	public boolean gameOver()
	{
		return false;
	}

	public void updateGameObjects()
	{
		currentRoom.updateRoom();
	}
	
	public void processPhysics() {
		currentRoom.processPhysics();
		processPhysicsTears(Tears);

	}

	public void drawGameObjects()
	{
		currentRoom.drawRoom();
		if (Tears != null) {
			for (Tear larme : Tears) {
				if (larme != null)
					larme.drawGameObject();
					larme.updateGameObject();
			}
		}
	}

	/*
	 * Keys processing
	 */

	private void processKeysForMovement()
	{
		if (StdDraw.isKeyPressed(Controls.goUp))
		{
			hero.goUpNext();
		}
		if (StdDraw.isKeyPressed(Controls.goDown))
		{
			hero.goDownNext();
		}
		if (StdDraw.isKeyPressed(Controls.goRight))
		{
			hero.goRightNext();
		}
		if (StdDraw.isKeyPressed(Controls.goLeft))
		{
			hero.goLeftNext();
		}
	}

	private void processKeysForShooting(){
		if(System.currentTimeMillis() - lastTime > 1000 || System.currentTimeMillis() - lastTime<0){
		if (StdDraw.isKeyPressed(Controls.shootUp))
		{
			Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4), HeroInfos.ISAAC_SPEED, "images/tear.png");
			Tears.add(tear);
			tear.shootUpNext();
			lastTime = System.currentTimeMillis();
		}
		if (StdDraw.isKeyPressed(Controls.shootDown))
		{
			Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4), HeroInfos.ISAAC_SPEED, "images/tear.png");
			Tears.add(tear);
			tear.shootDownNext();
			lastTime = System.currentTimeMillis();
		}
		if (StdDraw.isKeyPressed(Controls.shootRight))
		{
			Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4), HeroInfos.ISAAC_SPEED, "images/tear.png");
			Tears.add(tear);
			tear.shootRightNext();
			lastTime = System.currentTimeMillis();
		}
		if (StdDraw.isKeyPressed(Controls.shootLeft))
		{
			Tear tear = new Tear(hero.getPosition(), RoomInfos.TILE_SIZE.scalarMultiplication(0.4), HeroInfos.ISAAC_SPEED, "images/tear.png");
			Tears.add(tear);
			tear.shootLeftNext();
			lastTime = System.currentTimeMillis();
		}
		
	}

		if (Tears != null) {
			for (Tear larme : Tears) {
				if (larme != null){
						larme.drawGameObject();
						larme.updateGameObject();
				}
			}
		}
		}

		public void processPhysicsTears(List<Tear> Tears) {
			if (Tears != null) {
				//We store the object we will need to delete
				List<Tear> tearToRemove = new ArrayList<Tear>();
	
				//For each, if collision we do something then delete it
				for (Tear larme : Tears) {
					System.out.println(larme);
					if (larme != null && Physics.rectangleCollision(new Vector2(0.5, 0.8), RoomInfos.PICKABLE_SIZE, larme.getPosition(), larme.getSize())) {
						tearToRemove.add(larme);
					}			
				}
	
				//We ACTUALLY remove it (not inside the first loop, it messes up everything)
				for (Tear larme : tearToRemove) {
					Tears.remove(larme);
				}
			}
			
	}
}
