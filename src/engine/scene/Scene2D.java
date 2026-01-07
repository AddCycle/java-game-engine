package engine.scene;

import java.util.List;

import engine.entities.Entity;
import engine.entities.Player2D;
import engine.graphics.Renderer;
import engine.inputs.controllers.PlayerController2D;
import engine.world.Camera;
import engine.world.World2D;

public interface Scene2D {
	public void update(float dt);
    public void render(Renderer renderer, Camera camera);
    public void setWorld(World2D newWorld, PlayerController2D newController);
	public World2D getWorld();
	public List<Entity> getEntities();
	public Player2D getPlayer();
}