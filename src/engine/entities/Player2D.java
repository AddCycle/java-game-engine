package engine.entities;

import engine.graphics.Renderer;
import engine.inputs.controllers.PlayerController2D;
import engine.world.Camera;

public class Player2D extends Entity {
	private PlayerController2D controller;
	public int tileX, tileY;
	public int targetTileX, targetTileY;
	public boolean moving = false;

	public Player2D(PlayerController2D controller, int texture) {
		this(controller, 0, 0, texture);
	}

	public Player2D(PlayerController2D controller, float x, float y, int texture) {
		super(x, y, 16, 16, texture);
		this.controller = controller;
	}

	@Override
	public void update(float dt) {
		if (controller != null)
			controller.update(this, dt);
	}

	@Override
	public void render(Renderer renderer, Camera camera) {
		float camX = camera.x;
		float camY = camera.y;
		renderer.drawTexturedRect(texture, x - camX, y - camY, width, height);
	}

	public void setController(PlayerController2D newController) {
		controller = newController;
	}

	public void reset() {
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
	}
}