package engine.state.play;

import engine.core.Engine;
import engine.core.Logger;
import engine.entities.Entity;
import engine.entities.interaction.InteractionResult;
import engine.entities.interaction.InteractionSystem;
import engine.entities.interaction.InteractionType;
import engine.graphics.Renderer;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.keybinds.DialogKeybinds;
import engine.inputs.keybinds.Keybinds;
import engine.scene.Scene2D;
import engine.state.GameState;
import engine.state.dialog.DialogState;
import engine.state.menu.presets.PauseState;
import engine.ui.UIManager;
import engine.ui.hud.PlayHUD;

public class PlayState implements GameState {
	private Engine engine;
	private Scene2D scene;
	private Keybinds keybinds;
	private PauseState pauseState;
	private PlayHUD playHud;

	public PlayState(Scene2D scene, Keybinds keybinds) {
		this.scene = scene; // pass the scene you already have
		this.keybinds = keybinds;
	}

	@Override
	public void init(Engine engine) {
		this.engine = engine;
		
		/* TEMP UI Testing */
		UIManager uiManager = engine.getUIManager();
		this.playHud = new PlayHUD(uiManager);
	}

	@Override
	public void update(float dt) {
		Inputs input = engine.getInput();

		scene.update(dt);

		Entity player = scene.getPlayer();

		if (player.wantsToInteract()) {
			InteractionResult result = InteractionSystem.tryInteract(player, player.getInteractionBox(8),
					scene.getEntities());
			handleInteraction(result);
			player.wantsToInteract = false;
		}

		if (keybinds.isJustPressed(input, Action.PAUSE)) {
			if (pauseState == null) {
				Logger.warn("No pauseState found");
			} else {
				Logger.debug("switched to gamestate pause");
				engine.getGameStateManager().push(pauseState, engine);
//			engine.getGameStateManager().push(new engine.state.menu.PauseState(new PauseKeybinds()), engine);
			}
		}
	}

	@Override
	public void render(Renderer renderer) {
		scene.render(renderer, engine.getCamera());
		playHud.render(renderer);
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean blocksRenderBelow() {
		return false;
	}

	private void handleInteraction(InteractionResult result) {
		if (result == null)
			return;

		if (result.type() == InteractionType.DIALOG) {
			engine.getGameStateManager().push(new DialogState(result.text(), new DialogKeybinds()), engine);
		}
	}

	public void setPauseState(PauseState pauseState) {
		this.pauseState = pauseState;
	}
}