package engine.inputs.controllers;

import static engine.entities.Direction.DOWN;
import static engine.entities.Direction.LEFT;
import static engine.entities.Direction.RIGHT;
import static engine.entities.Direction.UP;

import engine.entities.Entity;
import engine.entities.MovementMode;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.keybinds.Keybinds;

public class TopdownController implements PlayerController2D {
	private Inputs input;
	private Keybinds keybinds;

	public TopdownController(Inputs input, Keybinds keybinds) {
		this.input = input;
		this.keybinds = keybinds;
	}

	@Override
	public void update(Entity player, float dt) {
		player.intentDX = 0;
		player.intentDY = 0;

		MovementMode mode = player.getMovementMode();
		if (mode == MovementMode.TILE) {
			getAxisInputs(player);
		} else if (mode == MovementMode.FREE) {
			getRawInputs(player);
		}

		// Interaction controls
		if (keybinds.isJustPressed(input, Action.INTERACT)) {
			player.tryInteract();
		}
	}

	private void getAxisInputs(Entity player) {
		if (player.moving) return;

		if (keybinds.isDown(input, Action.MOVE_LEFT)) {
			player.facing = LEFT;
			player.intentDX = -1;
		} else if (keybinds.isDown(input, Action.MOVE_RIGHT)) {
			player.facing = RIGHT;
			player.intentDX = 1;
		} else if (keybinds.isDown(input, Action.MOVE_UP)) {
			player.facing = UP;
			player.intentDY = -1;
		} else if (keybinds.isDown(input, Action.MOVE_DOWN)) {
			player.facing = DOWN;
			player.intentDY = 1;
		}
	}

	private void getRawInputs(Entity player) {
		if (player.moving) return;

		if (keybinds.isDown(input, Action.MOVE_UP)) {
			player.facing = UP;
			player.intentDY = -1;
		} else if (keybinds.isDown(input, Action.MOVE_DOWN)) {
			player.facing = DOWN;
			player.intentDY = 1;
		}

		if (keybinds.isDown(input, Action.MOVE_LEFT)) {
			player.facing = LEFT;
			player.intentDX = -1;
		} else if (keybinds.isDown(input, Action.MOVE_RIGHT)) {
			player.facing = RIGHT;
			player.intentDX = 1;
		}
	}
}