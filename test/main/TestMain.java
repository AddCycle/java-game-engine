package main;

import engine.core.Engine;
import engine.core.Game;

public class TestMain {
	
	public static void main(String[] args) {
		Engine engine = new Engine.Builder()
				.width(1280)
				.height(720)
				.title("Test Features")
				.vsync(true)
				.fullscreen(false)
				.debug()
				.icon("resources/icon.png")
				.build();

		Game game = new TestGame(engine);
		engine.setGame(game);
		engine.run();
	}
}
