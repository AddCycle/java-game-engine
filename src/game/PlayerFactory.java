package game;

import engine.core.Engine;
import engine.core.Logger;
import engine.entities.AnimatedPlayer2D;
import engine.entities.EntityManager;
import engine.entities.movement.MovementMode;
import engine.inputs.controllers.PlayerController2D;
import engine.inputs.controllers.TopdownController;
import engine.inputs.keybinds.TopdownKeybinds;
import engine.world.map.TileMap;

public class PlayerFactory {
	public static AnimatedPlayer2D createTopdownPlayer(Engine engine, float x, float y, MovementMode movementMode) {
		PlayerController2D controller = new TopdownController(engine.getInput(), new TopdownKeybinds());

		AnimatedPlayer2D player = new AnimatedPlayer2D(controller, 0, 0, 16, 16);
		player.setMovementMode(movementMode);

		AnimationsPresets.topdown(player, engine.getRenderer());

		player.setStateMachine(e -> {
			AnimatedPlayer2D p = (AnimatedPlayer2D) e;

			String dir = p.facing.getName();

			boolean isMoving = p.getMovementMode() == MovementMode.TILE ? p.moving : (p.vx != 0 || p.vy != 0);

			return (isMoving ? "walk_" : "idle_") + dir;
		});

		return player;
	}
}
