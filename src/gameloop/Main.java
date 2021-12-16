package gameloop;

import gameWorld.GameWorld;
import gameobjects.Hero;
import gameobjects.ObjectOnGround;
import libraries.StdDraw;
import libraries.Timer;
import resources.DisplaySettings;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;

public class Main
{


	public static long updateTime;
	public static long renderTime;
	public static long physicsTime;

	public static void main(String[] args)
	{
		// Hero, world and display initialisation.
		Hero isaac = new Hero(RoomInfos.POSITION_CENTER_OF_ROOM, HeroInfos.ISAAC_SIZE, HeroInfos.ISAAC_SPEED, ImagePaths.ISAAC, 3,0);
		GameWorld world = new GameWorld(isaac);				
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
