package levels;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.state.GameState;

public class LevelSelectionState implements GameState {
	private Engine engine;
	private LevelSelectionTesting levelSelection;
	
	public LevelSelectionState(LevelSelectionTesting levelSelection) {
		this.levelSelection = levelSelection;
	}

	@Override
	public void init(Engine engine) {
		this.engine = engine;
		levelSelection.init(engine);
	}

	@Override
	public void update(float dt) {
//			engine.getInput(); // movement in the map
		levelSelection.update();
	}

	@Override
	public void render(Renderer renderer) {
		levelSelection.render(renderer);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}