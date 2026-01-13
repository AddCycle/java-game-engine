package engine.state;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

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
		List<GameState> toRender = new ArrayList<>();

	    Iterator<GameState> it = states.iterator(); // top → bottom
	    while (it.hasNext()) {
	        GameState state = it.next();
	        toRender.add(state);
	        if (state.blocksRenderBelow()) {
	            break;
	        }
	    }

	    // Render bottom → top
	    for (int i = toRender.size() - 1; i >= 0; i--) {
	        toRender.get(i).render(renderer);
	    }
	}

	public GameState peek() {
		return states.peekFirst();
	}
}