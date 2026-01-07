package engine.state.play;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.scene.Scene;
import engine.scene.Scene2D;
import engine.state.GameState;

public class PlayState implements GameState {
	private Engine engine;
	private Scene2D scene;
	
	public PlayState(Scene2D scene) {
        this.scene = scene; // pass the scene you already have
    }

	@Override
	public void init(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void update(float dt) {
		scene.update(dt);
	}

	@Override
	public void render(Renderer renderer) {
		scene.render(renderer, engine.getCamera());
	}

	@Override
	public void dispose() {}

}
