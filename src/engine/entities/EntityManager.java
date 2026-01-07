package engine.entities;

import java.util.ArrayList;
import java.util.List;

import engine.graphics.Renderer;
import engine.world.Camera;
import engine.world.World2D;
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
    }

    private void checkCollisions() {
        for (int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Entity b = entities.get(j);

                if (a.getHitbox().intersects(b.getHitbox())) {
                    a.notifyCollision(b);
                    b.notifyCollision(a);
                }
            }
        }
    }

	public void render(Renderer renderer) {
		// FIXME : remove it after some testing because copy over and over the entities arrays won't be suitable for lots of entities
        for (Entity e : new ArrayList<>(entities))
            e.render(renderer, camera);
    }

	public void clear() {
		entities.clear();
	}
}
