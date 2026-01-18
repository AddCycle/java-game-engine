package game;

import engine.core.Engine;
import engine.entities.AnimatedPlayer2D;
import engine.entities.EntityManager;
import engine.entities.movement.MovementMode;
import engine.scene.AnimatedPlayerScene;

public class TopdownGameSetup {

	public static AnimatedPlayerScene create(Engine engine, EntityManager entityManager) {

		AnimatedPlayer2D player = PlayerFactory.createTopdownPlayer(engine, 0, 0, MovementMode.FREE);
		entityManager.setPlayer(player);

		float smoothingFactor = 0.1f;
		engine.getCamera().setFollowEntity(player, smoothingFactor);

		AnimatedPlayerScene scene = new AnimatedPlayerScene(null, engine.getCamera(), player, entityManager);
		
		return scene;
    }
}
