package main;

import engine.core.Engine;
import engine.core.Game;
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
//		selectionTest.update();
	}

	@Override
	public void debug(int fps) {
		// TODO Auto-generated method stub
		
	}
}