package engine.inputs.controllers;

import static engine.entities.Direction.*;
import engine.entities.Player2D;
import engine.inputs.Inputs;
import engine.inputs.Keybinds;
import engine.inputs.action.Action;
import engine.world.TileMap;

public class TopdownController implements PlayerController2D {
	private Inputs input;
	private Keybinds keybinds;
	private float speed;
	private TileMap map;
	private boolean tileBasedMovement;

	public TopdownController(Inputs input, TileMap map) {
		this(input, map, true);
	}

	public TopdownController(Inputs input, TileMap map, boolean tileBasedMovement) {
		this.input = input;
		this.keybinds = new Keybinds();
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

		// Interaction controls
		if (keybinds.isJustPressed(input, Action.INTERACT)) {
			player.tryInteract();
		}
	}

	private void tileMovement(Player2D player, float dt) {
		if (player.moving) {
			moveToTarget(player, dt);
			return;
		}

		if (keybinds.isDown(input, Action.MOVE_LEFT)) {
			startMove(player, -1, 0);
			player.facing = LEFT;
		} else if (keybinds.isDown(input, Action.MOVE_RIGHT)) {
			startMove(player, 1, 0);
			player.facing = RIGHT;
		} else if (keybinds.isDown(input, Action.MOVE_UP)) {
			player.facing = UP;
			startMove(player, 0, -1);
		} else if (keybinds.isDown(input, Action.MOVE_DOWN)) {
			player.facing = DOWN;
			startMove(player, 0, 1);
		}
	}

	private void basicMovement(Player2D player, float dt) {
		player.vx = 0;
		player.vy = 0;

		if (keybinds.isDown(input, Action.MOVE_LEFT)) {
			player.facing = LEFT;
			player.vx = -speed;
		}
		if (keybinds.isDown(input, Action.MOVE_RIGHT)) {
			player.facing = RIGHT;
			player.vx = speed;
		}
		if (keybinds.isDown(input, Action.MOVE_UP)) {
			player.facing = UP;
			player.vy = -speed;
		}
		if (keybinds.isDown(input, Action.MOVE_DOWN)) {
			player.facing = DOWN;
			player.vy = speed;
		}
	}

	private void startMove(Player2D p, int dx, int dy) {
		p.targetTileX = p.tileX + dx;
		p.targetTileY = p.tileY + dy;

		if (map.isSolid(p.targetTileX, p.targetTileY))
			return;

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