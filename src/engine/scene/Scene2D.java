package engine.scene;

import java.util.List;

import engine.entities.Entity;
import engine.entities.EntityManager;
import engine.graphics.Renderer;
import engine.world.Camera;
import engine.world.World2D;

public interface Scene2D {
	public void update(float dt);
    public void render(Renderer renderer, Camera camera);
    public void setWorld(World2D newWorld);
	public World2D getWorld();
	public EntityManager getEntityManager();
	public List<Entity> getEntities();
	public Entity getPlayer();
}