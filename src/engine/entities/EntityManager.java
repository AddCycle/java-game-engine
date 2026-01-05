package engine.entities;

import java.util.ArrayList;
import java.util.List;

import engine.graphics.Renderer;
import engine.world.Camera;

public class EntityManager {
	private List<Entity> entities = new ArrayList<>();
	private Camera camera;
	
	public EntityManager(Camera cam) {
		camera = cam;
	}

    public void add(Entity e) {
        entities.add(e);
    }

    public void update(float dt) {
        for (Entity e : entities)
            e.update(dt);
    }

    public void render(Renderer renderer) {
        for (Entity e : entities)
            e.render(renderer, camera);
    }
}
