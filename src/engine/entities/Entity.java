package engine.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import engine.entities.body.BodyType;
import engine.entities.movement.Direction;
import engine.entities.movement.MovementMode;
import engine.graphics.Renderer;
import engine.world.Camera;

public abstract class Entity {
	public float x, y;
	public float vx, vy;
	public float width, height;
	protected int texture;
	public boolean onGround;
	public Direction facing;
	public boolean wantsToInteract;
	public int tileX, tileY;
	public int targetTileX, targetTileY;
	public boolean moving = false;
	public boolean solid = false;
	public BodyType bodyType = BodyType.DYNAMIC;
	
	// for topdown movement
	public int intentDX = 0;
	public int intentDY = 0;
	private MovementMode movementMode = MovementMode.TILE;
    private float speed = 30f;
	
	private final List<Consumer<Entity>> collisionListeners = new ArrayList<>();
	public boolean markedForRemoval;

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
		this.facing = Direction.DOWN;
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
    
    /**
     * Interaction part
     * @param range
     * @return a Floating point rectangle
     */
    public Rectangle.Float getInteractionBox(float range) {
        float ix = x;
        float iy = y;

        if (facing == Direction.LEFT)  ix -= range;
        if (facing == Direction.RIGHT) ix += width;
        if (facing == Direction.UP)    iy -= range;
        if (facing == Direction.DOWN)  iy += height;

        return new Rectangle.Float(ix, iy, range, range);
    }

	public void tryInteract() {
		this.wantsToInteract = true;
	}

	public boolean wantsToInteract() {
		return wantsToInteract;
	}

    public MovementMode getMovementMode() { return movementMode; }
    public void setMovementMode(MovementMode m) { movementMode = m; }

    public void setSpeed(float s) { speed = s; }
	public float getSpeed() {
		return speed;
	}
}