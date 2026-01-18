package game;

import engine.core.Engine;
import engine.core.Game;

public class Main {

	public static void main(String[] args) {
		Engine engine = new Engine.Builder()
				.width(1280)
				.height(720)
				.title("Nameless Adventure")
				.vsync(true) // FIXME : see if it is really that performance saving
				.fullscreen(false)
				.debug()
				.icon("resources/icon.png")
				.build();
		Game game = new NamelessAdventure(engine);
		engine.setGame(game);
		engine.run();
	}
}