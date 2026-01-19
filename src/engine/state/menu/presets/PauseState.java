package engine.state.menu.presets;

import engine.state.menu.MainMenuState;
import engine.state.menu.MenuState;
import engine.state.menu.core.Menu;
import engine.state.menu.core.MenuItem;
import engine.state.menu.core.VerticalMenu;
import engine.state.play.PlayState;

public class PauseState extends MenuState {
	private PlayState playState;
	
	public PauseState(PlayState playState) {
		this.playState = playState;
	}

	@Override
	protected Menu createMenu() {
		return new VerticalMenu(engine,
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