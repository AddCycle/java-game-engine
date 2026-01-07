package engine.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import engine.graphics.Renderer;
import engine.world.Camera;

public abstract class Entity {
	public float x, y;
	public float vx, vy;
	public float width, height;
	protected int texture;
	public boolean onGround;
	
	private final List<Consumer<Entity>> collisionListeners = new ArrayList<>();

	public Entity(float x, float y, float width, float height, int texture) {
		this(x, y, width, height, 0, 0, texture);
	}

	public Entity(float x, float y, float width, float height, float vx, float vy, int texture) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}
	
	public abstract void update(float dt);
    public abstract void render(Renderer renderer, Camera camera);
    
    public Rectangle.Float getHitbox() {
        return new Rectangle.Float(x, y, width, height);
    }
    
    public void addCollisionListener(Consumer<Entity> callback) {
        collisionListeners.add(callback);
    }

    public void notifyCollision(Entity other) {
        for (var cb : collisionListeners) cb.accept(other);
    }
}