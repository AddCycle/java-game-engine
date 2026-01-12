package engine.entities;

import static engine.entities.body.BodyType.DYNAMIC;
import static engine.entities.body.BodyType.STATIC;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import engine.graphics.Renderer;
import engine.world.Camera;
import engine.world.World2D;
import engine.world.map.TileMap;
import engine.world.physics.Physics2D;

/**
 * For now only for World2D TODO expand later on
 */
public class EntityManager {
	private List<Entity> entities = new ArrayList<>();
	private Camera camera;
	private Physics2D physics;
	
	public EntityManager(Camera cam, Physics2D physics) {
		camera = cam;
		this.physics = physics;
	}
	
	public void setPhysics(Physics2D physics) {
		this.physics = physics;
	}

    public void add(Entity e) {
        entities.add(e);
    }

	public void remove(Entity e) {
		entities.remove(e);
	}

    public void update(float dt, World2D world) {
        for (Entity e : entities) {
            e.update(dt);
            physics.update(e, world, dt);
        }
        
        checkCollisions();
        
        entities.removeIf(e -> e.markedForRemoval);
    }

    private void checkCollisions() {
        for (int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Entity b = entities.get(j);

                if (a.getHitbox().intersects(b.getHitbox())) {
                    a.notifyCollision(b);
                    b.notifyCollision(a);

                    if (a.solid && b.solid) {
                        resolveCollision(a, b);
                    }
                }
            }
        }
    }

	public void render(Renderer renderer) {
        for (Entity e : entities)
            e.render(renderer, camera);
    }

	public void clear() {
		entities.clear();
	}

	public List<Entity> getEntities() {
		return entities;
	}
	
	private void resolveCollision(Entity a, Entity b) {
	    if (a.bodyType == STATIC && b.bodyType == STATIC) return;

	    if (a.bodyType == DYNAMIC && b.bodyType == STATIC) {
	        pushOut(a, b);
	    } else if (a.bodyType == STATIC && b.bodyType == DYNAMIC) {
	        pushOut(b, a);
	    } else {
	        // both dynamic → optional split
	        pushOut(a, b);
	    }
	}
	
	private void pushOut(Entity mover, Entity solid) {
	    Rectangle.Float ra = mover.getHitbox();
	    Rectangle.Float rb = solid.getHitbox();

	    float dx = (ra.x + ra.width / 2) - (rb.x + rb.width / 2);
	    float dy = (ra.y + ra.height / 2) - (rb.y + rb.height / 2);

	    float overlapX = (ra.width + rb.width) / 2 - Math.abs(dx);
	    float overlapY = (ra.height + rb.height) / 2 - Math.abs(dy);

	    if (overlapX < overlapY) {
	        mover.x += dx > 0 ? overlapX : -overlapX;
	        mover.vx = 0;
	    } else {
	        mover.y += dy > 0 ? overlapY : -overlapY;
	        mover.vy = 0;
	    }
	}
	
	public boolean isEntityBlockingTile(int tileX, int tileY, Entity self, TileMap map) {
	    float x = tileX * map.getTileSize();
	    float y = tileY * map.getTileSize();

	    Rectangle.Float tileRect =
	        new Rectangle.Float(x, y, map.getTileSize(), map.getTileSize());

	    for (Entity e : entities) {
	        if (e == self) continue;
	        if (!e.solid) continue;

	        if (tileRect.intersects(e.getHitbox())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private void resolveSolidCollision(Entity a, Entity b) {
	    Rectangle.Float ra = a.getHitbox();
	    Rectangle.Float rb = b.getHitbox();

	    float dx = (ra.x + ra.width / 2) - (rb.x + rb.width / 2);
	    float dy = (ra.y + ra.height / 2) - (rb.y + rb.height / 2);

	    float overlapX = (ra.width + rb.width) / 2 - Math.abs(dx);
	    float overlapY = (ra.height + rb.height) / 2 - Math.abs(dy);

	    if (overlapX < overlapY) {
	        a.x += dx > 0 ? overlapX : -overlapX;
	        a.vx = 0;
	    } else {
	        a.y += dy > 0 ? overlapY : -overlapY;
	        a.vy = 0;
	    }
	}
}
