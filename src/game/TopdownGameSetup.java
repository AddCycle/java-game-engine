package game;

import java.awt.Color;

import engine.core.Engine;
import engine.entities.AnimatedPlayer2D;
import engine.entities.EntityManager;
import engine.entities.MovementMode;
import engine.scene.AnimatedPlayerScene;
import engine.scene.Scene2D;
import entities.interaction.InteractibleNPC;
import entities.interaction.PlayerInteractionTesting;

public class TopdownGameSetup {

	public static AnimatedPlayerScene create(Engine engine, EntityManager entityManager) {

		AnimatedPlayer2D player = PlayerFactory.createTopdownPlayer(engine, 0, 0, MovementMode.FREE);
		entityManager.add(player);

//		// TODO: spawn from map data (yeah only for testing)
//		InteractibleNPC npc = PlayerInteractionTesting.createTestNPC(Color.RED);
//		entityManager.add(npc);

		float smoothingFactor = 0.1f;
		engine.getCamera().setFollowEntity(player, smoothingFactor);

		AnimatedPlayerScene scene = new AnimatedPlayerScene(null, engine.getCamera(), player, entityManager);
		
		return scene;
    }
}
