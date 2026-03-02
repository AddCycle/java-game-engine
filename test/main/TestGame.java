package main;

import org.lwjgl.glfw.GLFW;

import engine.core.Engine;
import engine.core.Game;
import engine.inputs.Inputs;
import levels.LevelSelectionState;
import levels.LevelSelectionTesting;

public class TestGame implements Game {
	private Engine engine;
	private LevelSelectionTesting selectionTest = new LevelSelectionTesting();

	public TestGame(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void init() {
		engine.setState(new LevelSelectionState(selectionTest));
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

		if (input.isKeyJustPressed(GLFW.GLFW_KEY_F11)) {
			engine.getWindow().setFullScreen(!engine.getWindow().isFullscreen());
		}
		
		if (input.isKeyJustPressed(GLFW.GLFW_KEY_ESCAPE)) {
			engine.stop();
		}
	}

	@Override
	public void debug(int fps) {
		// TODO Auto-generated method stub
		
	}
}