package engine.state.menu.presets;

import engine.state.menu.MainMenuState;
import engine.state.menu.MenuState;
import engine.state.play.PlayState;
import engine.ui.menu.Menu;
import engine.ui.menu.MenuItem;
import engine.ui.menu.VerticalMenu;

public class PauseState extends MenuState {
	private PlayState playState;
	
	public PauseState(PlayState playState) {
		this.playState = playState;
	}

	@Override
	protected Menu createMenu() {
		return new VerticalMenu(engine.getInput(),
	            new MenuItem("Resume", () ->
	                engine.getGameStateManager().pop()),
	            new MenuItem("Quit to Title", () ->
	                engine.getGameStateManager().set(new MainMenuState(playState), engine))
	        );
	}
	
	@Override
	public boolean blocksRenderBelow() {
		return false;
	}
}