package gameloop;

import gameWorld.BossRoom;
import gameWorld.GameWorld;
import gameWorld.Room;
import gameWorld.Shop;
import gameWorld.StartRoom;
import gameWorld.MonsterRoom;
import gameobjects.Hero;
import libraries.StdDraw;
import libraries.Timer;
import monsters.GaperBehavior;
import resources.DisplaySettings;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;

public class Main {

	public static long updateTime;
	public static long renderTime;
	public static long physicsTime;
	public static GameWorld world;

	public static void main(String[] args) {
		// Hero, world and display initialisation.
		Hero isaac = new Hero(RoomInfos.POSITION_DOWN_OF_ROOM, HeroInfos.ISAAC_SIZE, HeroInfos.ISAAC_SPEED,
				ImagePaths.ISAAC, 6, 0, 1);


		//On créée les rooms
		Room rooms1 = new MonsterRoom(isaac);
		Room rooms2 = new MonsterRoom(isaac);
		Room rooms3 = new Shop(isaac);
		BossRoom bossRoom = new BossRoom(isaac);

		//On créée notre tableau
		Room[] rooms = { rooms1, rooms2, rooms3 };

		//Génération du donjon
		Room dungeon = Room.createDungeon(rooms, bossRoom, isaac);

		//On créée le monde
		world = new GameWorld(isaac, dungeon);
		initializeDisplay();

		// Main loop of the game
		while (!world.gameOver() && bossRoom.win() == false) {
			processNextStep(world);
			if (world.gameOver()) break;
		}

		if (bossRoom.win() == true) {
			StdDraw.picture(0.5, 0.5, ImagePaths.WIN_SCREEN, 1, 1);
			StdDraw.show();
		} else {
			StdDraw.picture(0.5, 0.5, ImagePaths.LOSE_SCREEN, 1, 1);
			StdDraw.show();
		}

	}

	private static void processNextStep(GameWorld world) {
		Timer.beginTimer();
		StdDraw.clear();
		world.processUserInput();
		long tmp = System.nanoTime();
		world.updateGameObjects();
		updateTime = System.nanoTime() - tmp;
		world.drawGameObjects();
		renderTime = System.nanoTime() - (tmp + updateTime);

		if (DisplaySettings.DRAW_DEBUG_INFO) {
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.text(0.1, 0.05, "UT :" + Math.round(updateTime / 10000d) / 100d);
			StdDraw.text(0.1, 0.08, "RT :" + Math.round(renderTime / 10000d) / 100d);
		}

		StdDraw.show();
		Timer.waitToMaintainConstantFPS();
	}

	private static void initializeDisplay() {
		// Set the window's size, in pixels.
		// It is strongly recommended to keep a square window.
		StdDraw.setCanvasSize(RoomInfos.NB_TILES * DisplaySettings.PIXEL_PER_TILE,
				RoomInfos.NB_TILES * DisplaySettings.PIXEL_PER_TILE);

		// Enables double-buffering.
		// https://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics
		StdDraw.enableDoubleBuffering();
	}
}
