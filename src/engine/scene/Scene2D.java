package engine.scene;

import engine.graphics.Renderer;
import engine.inputs.controllers.PlayerController2D;
import engine.world.Camera;
import engine.world.World2D;

public interface Scene2D {
	public void update(float dt);
    public void render(Renderer renderer, Camera camera);
    public void setWorld(World2D newWorld, PlayerController2D newController);
	public World2D getWorld();
}