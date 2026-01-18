package engine.scene;

import java.util.List;

import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.entities.Player2D;
import engine.graphics.Renderer;
import engine.world.Camera;
import engine.world.World2D;

// TODO(refactor): make a scene superclass that gets all the basics things to avoid re-writing things
public class Scene implements Scene2D {
	private World2D world;
	private Camera camera;
    private Player2D player;
    private EntityManager entities;
    
	public Scene(World2D world, Camera cam, Player2D player, EntityManager entities) {
		this.world = world;
		this.player = player;
		this.entities = entities;
		this.camera = cam;
	}

	@Override
	public void update(float dt) {
		world.update(dt);
        player.update(dt);
        entities.update(dt, world);
	}

	@Override
	public void render(Renderer renderer, Camera camera) {
		world.render(renderer, camera);
        entities.render(renderer);
        player.render(renderer, camera);
	}

	@Override
	public void setWorld(World2D newWorld) {
		this.world = newWorld;

		camera.setWorld(newWorld);
		
		player.reset();
	}

	@Override
	public World2D getWorld() {
		return world;
	}

	@Override
	public List<Entity> getEntities() {
		return entities.getEntities();
	}
	
	@Override
	public Player2D getPlayer() {
		return player;
	}

	@Override
	public EntityManager getEntityManager() {
		return entities;
	}
}