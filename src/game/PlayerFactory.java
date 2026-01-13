package game;

import engine.core.Engine;
import engine.core.Logger;
import engine.entities.AnimatedPlayer2D;
import engine.entities.EntityManager;
import engine.inputs.controllers.PlayerController2D;
import engine.inputs.controllers.TopdownController;
import engine.inputs.keybinds.TopdownKeybinds;
import engine.world.map.TileMap;

public class PlayerFactory {
	public static AnimatedPlayer2D createTopdownPlayer(Engine engine, EntityManager em, TileMap tilemap, boolean tileBasedMovement) {
        PlayerController2D controller =
            new TopdownController(
                engine.getInput(),
                new TopdownKeybinds(),
                tilemap,
                em,
                tileBasedMovement
            );

        AnimatedPlayer2D player = new AnimatedPlayer2D(controller, 16, 16, 16, 16);
        player.tileX = (int) Math.floor(player.x / tilemap.getTileSize());
        player.tileY = (int) Math.floor(player.y / tilemap.getTileSize());
        player.targetTileX = player.tileX;
        player.targetTileY = player.tileY;
        Logger.info("Player created at tileX, tileY: %d, %d", player.tileX, player.tileY);

        AnimationsPresets.topdown(player, engine.getRenderer());
        
		player.setStateMachine(e -> {
			AnimatedPlayer2D p = (AnimatedPlayer2D) e;

			String dir = p.facing.getName();

			boolean isMoving = tileBasedMovement ? p.moving : (p.vx != 0 || p.vy != 0);

			return (isMoving ? "walk_" : "idle_") + dir;
		});

        em.add(player);
        return player;
    }
}
