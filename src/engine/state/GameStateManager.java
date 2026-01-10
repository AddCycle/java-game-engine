package engine.state;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import engine.core.Engine;
import engine.graphics.Renderer;

public class GameStateManager {
	private Deque<GameState> states = new ArrayDeque<>();

	public void push(GameState state, Engine engine) {
		states.peekFirst();
		states.addFirst(state);
		state.init(engine);
	}

	public void pop() {
		GameState removed = states.pollFirst();
		if (removed != null)
			removed.dispose();
	}

	public void set(GameState state, Engine engine) {
		while (!states.isEmpty())
			pop();
		push(state, engine);
	}

	public void update(float dt) {
//		if (!states.isEmpty()) {
//			states.peekFirst().update(dt);
//		}
		Iterator<GameState> it = states.iterator(); // top → bottom
	    while (it.hasNext()) {
	        GameState state = it.next();
	        state.update(dt);
	        if (state.blocksUpdateBelow()) {
	            break;
	        }
	    }
	}

	public void render(Renderer renderer) {
		Iterator<GameState> it = states.descendingIterator(); // bottom → top
		while (it.hasNext()) {
			GameState state = it.next();
			state.render(renderer);
			if (state.blocksRenderBelow()) {
				break;
			}
		}
	}

	public GameState peek() {
		return states.peekFirst();
	}
}