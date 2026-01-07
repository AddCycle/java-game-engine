package engine.state;

import java.util.ArrayDeque;
import java.util.Deque;

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
        if (removed != null) removed.dispose();
    }

    public void set(GameState state, Engine engine) {
        while (!states.isEmpty()) pop();
        push(state, engine);
    }

    public void update(float dt) {
        if (!states.isEmpty()) states.peekFirst().update(dt);
    }

    public void render(Renderer renderer) {
        if (!states.isEmpty()) states.peekFirst().render(renderer);
    }

    public GameState peek() {
        return states.peekFirst();
    }
}
