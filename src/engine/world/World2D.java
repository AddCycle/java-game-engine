package engine.world;

import engine.graphics.Renderer;

public interface World2D {
	float getWidth();
	float getHeight();
	void update(float dt);
	void render(Renderer renderer, Camera camera);
	public boolean isColliding(float x, float y, float width, float height);
}
