package gameloop;

import javax.swing.text.JTextComponent.KeyBinding;

import gameWorld.GameWorld;
import gameWorld.Room;
import gameWorld.StartRoom;
import gameWorld.TestRoom;
import gameobjects.Hero;
import libraries.StdDraw;
import libraries.Timer;
import resources.DisplaySettings;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;
import libraries.Keybinding;

public class Main
{


	public static long updateTime;
	public static long renderTime;
	public static long physicsTime;
	public static Room[] rooms;
	public static GameWorld world;

	public static void main(String[] args)
	{
		// Hero, world and display initialisation.
		Hero isaac = new Hero(RoomInfos.POSITION_DOWN_OF_ROOM, HeroInfos.ISAAC_SIZE, HeroInfos.ISAAC_SPEED, ImagePaths.ISAAC, 6,0);

		rooms = new Room[3];

		rooms[0] = new StartRoom(isaac);
		rooms[1] = new Room(isaac);
		rooms[2] = new TestRoom(isaac);

		world = new GameWorld(isaac, rooms[2]);				
		initializeDisplay();

		// Main loop of the game
		while (!world.gameOver())
		{
			processNextStep(world);
		}
	}

	private static void processNextStep(GameWorld world)
	{
		Timer.beginTimer();
		StdDraw.clear();
		world.processUserInput();
		long tmp = System.nanoTime();
		world.updateGameObjects();
		updateTime = System.nanoTime() - tmp;
		world.processPhysics();
		physicsTime = System.nanoTime() - (tmp + updateTime);
		world.drawGameObjects();
		renderTime = System.nanoTime() - (tmp + updateTime + physicsTime);

		if (DisplaySettings.DRAW_DEBUG_INFO) {
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.text(0.1, 0.05, "UT :" + Math.round(updateTime / 10000d) / 100d);
			StdDraw.text(0.1, 0.08, "PT :" + Math.round(physicsTime / 10000d) / 100d);
			StdDraw.text(0.1, 0.11, "RT :" + Math.round(renderTime / 10000d) / 100d);
		}

		//if (StdDraw.isKeyPressed() {
		//	world.UpdateRoom(rooms[0]);
		//} else if (StdDraw.isKeyPressed()) { 
		//	world.UpdateRoom(rooms[1]);
		//} else if (StdDraw.isKeyPressed()) { 
		//	world.UpdateRoom(rooms[2]);
		//}

		StdDraw.show();
		Timer.waitToMaintainConstantFPS();
	}

	private static void initializeDisplay()
	{
		// Set the window's size, in pixels.
		// It is strongly recommended to keep a square window.
		StdDraw.setCanvasSize(RoomInfos.NB_TILES * DisplaySettings.PIXEL_PER_TILE,
				RoomInfos.NB_TILES * DisplaySettings.PIXEL_PER_TILE);

		// Enables double-buffering.
		// https://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics
		StdDraw.enableDoubleBuffering();
	}
}
