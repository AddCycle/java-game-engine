package engine.scene;

import java.util.List;

import engine.entities.AnimatedPlayer2D;
import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.graphics.Renderer;
import engine.inputs.controllers.PlayerController2D;
import engine.world.Camera;
import engine.world.World2D;

public class AnimatedPlayerScene implements Scene2D {
	private World2D world;
	private Camera camera;
	private AnimatedPlayer2D player;
	private EntityManager entities;

	public AnimatedPlayerScene(World2D world, Camera cam, AnimatedPlayer2D player, EntityManager entities) {
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
	public void setWorld(World2D newWorld, PlayerController2D newController) {
		this.world = newWorld;
		player.setController(newController);
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
	public AnimatedPlayer2D getPlayer() {
		return player;
	}
}