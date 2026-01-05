package engine.entities;

import engine.graphics.Renderer;
import engine.world.Camera;

public abstract class Entity {
	public float x, y;
	public float width, height;
	protected int texture;

	public Entity(float x, float y, float width, float height, int texture) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}
	
	public abstract void update(float dt);
    public abstract void render(Renderer renderer, Camera camera);
}
