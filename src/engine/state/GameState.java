package engine.state;

import engine.core.Engine;
import engine.graphics.Renderer;

public interface GameState {
	void init(Engine engine);           // called when state starts
    void update(float dt);              // per-frame logic
    void render(Renderer renderer);     // per-frame render
    void dispose();                     // cleanup when leaving state

	default boolean blocksRenderBelow() { return true; } // blocking the render of the below state
    default boolean blocksUpdateBelow() { return true; } // block the update of the below state
}