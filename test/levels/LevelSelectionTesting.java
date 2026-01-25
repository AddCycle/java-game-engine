package levels;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.state.GameState;
import engine.world.level.WorldMap;

public class LevelSelectionTesting {
	public WorldMap selectionWorld;

	public LevelSelectionTesting() {
		selectionWorld = new WorldMap();
	}
	
	public void init(Engine engine) {
		selectionWorld.create(engine);
	}
	
	public void render(Renderer r) {
		selectionWorld.render(r);
	}
	
	public void update() {
		selectionWorld.update();
	}
}