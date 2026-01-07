package engine.inputs.controllers;

import org.lwjgl.glfw.GLFW;

import engine.entities.Player2D;
import engine.inputs.Inputs;
import engine.world.TileMap;

public class TopdownController implements PlayerController2D {
	private Inputs input;
	private float speed;
	private TileMap map;
	private boolean tileBasedMovement;

	public TopdownController(Inputs input, TileMap map) {
		this(input, map, true);
	}

	public TopdownController(Inputs input, TileMap map, boolean tileBasedMovement) {
		this.input = input;
		this.map = map;
		this.tileBasedMovement = tileBasedMovement;
		if (tileBasedMovement) {
			speed = 30f;
		} else {
			speed = 60f;
		}
	}

	@Override
	public void update(Player2D player, float dt) {
		if (tileBasedMovement)
			tileMovement(player, dt);
		else
			basicMovement(player, dt);
	}
	
	private void tileMovement(Player2D player, float dt) {
		if (player.moving) {
	        moveToTarget(player, dt);
	        return;
	    }

		// TODO : make a keybinds class so that the settings can be modified at any key
	    if (input.isKeyDown(GLFW.GLFW_KEY_A) || input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT)) startMove(player, -1, 0);
	    else if (input.isKeyDown(GLFW.GLFW_KEY_D) || input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT)) startMove(player, 1, 0);
	    else if (input.isKeyDown(GLFW.GLFW_KEY_W) || input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP)) startMove(player, 0, -1);
	    else if (input.isKeyDown(GLFW.GLFW_KEY_S) || input.isControllerButtonDown(GLFW.GLFW_JOYSTICK_1, GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN)) startMove(player, 0, 1);
	}

	private void basicMovement(Player2D player, float dt) {
		player.vx = 0;
		player.vy = 0;

		if (input.isKeyDown(GLFW.GLFW_KEY_A)) player.vx = -speed;
		if (input.isKeyDown(GLFW.GLFW_KEY_D)) player.vx = speed;
		if (input.isKeyDown(GLFW.GLFW_KEY_W)) player.vy = -speed;
		if (input.isKeyDown(GLFW.GLFW_KEY_S)) player.vy = speed;
	}

	private void startMove(Player2D p, int dx, int dy) {
	    p.targetTileX = p.tileX + dx;
	    p.targetTileY = p.tileY + dy;

	    if (map.isSolid(p.targetTileX, p.targetTileY)) return;

	    p.moving = true;
	}
	
	private void moveToTarget(Player2D p, float dt) {
	    float targetX = p.targetTileX * map.getTileSize();
	    float targetY = p.targetTileY * map.getTileSize();

	    float speed = this.speed;

	    p.x = approach(p.x, targetX, speed * dt);
	    p.y = approach(p.y, targetY, speed * dt);

	    if (p.x == targetX && p.y == targetY) {
	        p.tileX = p.targetTileX;
	        p.tileY = p.targetTileY;
	        p.moving = false;
	    }
	}
	
	private float approach(float current, float target, float maxDelta) {
	    if (current < target)
	        return Math.min(current + maxDelta, target);
	    return Math.max(current - maxDelta, target);
	}
}