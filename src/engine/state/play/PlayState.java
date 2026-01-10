package engine.state.play;

import engine.core.Engine;
import engine.entities.Entity;
import engine.entities.interaction.InteractionResult;
import engine.entities.interaction.InteractionSystem;
import engine.entities.interaction.InteractionType;
import engine.graphics.Renderer;
import engine.inputs.keybinds.DialogKeybinds;
import engine.inputs.keybinds.Keybinds;
import engine.scene.Scene2D;
import engine.state.GameState;
import engine.state.dialog.DialogState;

public class PlayState implements GameState {
	private Engine engine;
	private Scene2D scene;
	
	public PlayState(Scene2D scene) {
        this.scene = scene; // pass the scene you already have
    }

	@Override
	public void init(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void update(float dt) {
		scene.update(dt);
		
		Entity player = scene.getPlayer();

	    if (player.wantsToInteract()) {
	        InteractionResult result = InteractionSystem.tryInteract(player, player.getInteractionBox(8), scene.getEntities());
	        handleInteraction(result);
	        player.wantsToInteract = false;
	    }
	}

	@Override
	public void render(Renderer renderer) {
		scene.render(renderer, engine.getCamera());
	}

	@Override
	public void dispose() {}

	private void handleInteraction(InteractionResult result) {
		if (result == null) return;

	    if (result.type() == InteractionType.DIALOG) {
	        engine.getGameStateManager().push(
	            new DialogState(result.text(), new DialogKeybinds()), engine
	        );
	    }
	}
	
	@Override
	public boolean blocksRenderBelow() {
		return false;
	}
}
