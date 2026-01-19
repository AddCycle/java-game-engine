package engine.state.menu;

import engine.state.menu.core.Menu;
import engine.state.menu.core.MenuItem;
import engine.state.menu.core.VerticalMenu;
import engine.state.play.PlayState;

public class MainMenuState extends MenuState {
	private PlayState playState;
//	private OptionState optionState;
	
	public MainMenuState(PlayState playState) {
		this.playState = playState;
	}

	@Override
	protected Menu createMenu() {
		return new VerticalMenu(engine,
	            new MenuItem("Start", () -> engine.setState(playState)),
//	            new MenuItem("Options", () -> engine.getGameStateManager()
//	                                                .push(new OptionsState(), engine)),
	            new MenuItem("Quit", () -> engine.stop())
	        );
	}

}
