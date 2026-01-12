package game;

import engine.core.Engine;
import engine.entities.AnimatedPlayer2D;
import engine.entities.EntityManager;
import engine.inputs.keybinds.PlayKeybinds;
import engine.loader.TiledLoader;
import engine.scene.AnimatedPlayerScene;
import engine.scene.Scene2D;
import engine.state.play.PlayState;
import engine.world.TopdownWorld;
import engine.world.World2D;
import engine.world.map.Tile;
import engine.world.map.TileMap;
import engine.world.physics.TopdownPhysics;
import entities.interaction.InteractibleNPC;
import entities.interaction.PlayerInteractionTesting;

public class TopdownGameSetup {

	public static Scene2D create(Engine engine) {
		int[] textures = TexturePresets.basicTiles(engine);

		/* TileMap setup */
		Tile[] tuiles = TilePresets.basicTiles(engine, textures);
		TileMap tilemap = TiledLoader.loadFromJSON("resources/maps/sample1.json", tuiles);
		/* end tileMap setup */

		World2D world = new TopdownWorld(tilemap);

		EntityManager entityManager = new EntityManager(engine.getCamera(), new TopdownPhysics());

		boolean tileBasedMovement = true;
		AnimatedPlayer2D player = PlayerFactory.createTopdownPlayer(engine, entityManager, tilemap, tileBasedMovement);

		// TODO: spawn from map data (yeah only for testing)
		InteractibleNPC npc = PlayerInteractionTesting.createTestNPC();
		entityManager.add(npc);

		float smoothingFactor = 0.1f;
		engine.getCamera().setWorld(world);
		engine.getCamera().setFollowEntity(player, smoothingFactor);

		Scene2D scene = new AnimatedPlayerScene(world, engine.getCamera(), player, entityManager);
		engine.setState(new PlayState(scene, new PlayKeybinds()));
		
		return scene;
    }
}
