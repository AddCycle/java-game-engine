package engine.state.menu.core;

import engine.graphics.Renderer;

public interface Menu {
	void update(float dt);
    void render(Renderer renderer);

    default void onOpen() {}
    default void onClose() {}
}