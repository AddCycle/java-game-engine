package engine.state.menu;

import engine.state.play.PlayState;
import engine.ui.menu.Menu;
import engine.ui.menu.MenuItem;
import engine.ui.menu.VerticalMenu;

public class MainMenuState extends MenuState {
	private PlayState playState;
//	private OptionState optionState;
	
	public MainMenuState(PlayState playState) {
		this.playState = playState;
	}

	@Override
	protected Menu createMenu() {
		return new VerticalMenu(engine.getInput(),
	            new MenuItem("Start", () -> engine.setState(playState)),
//	            new MenuItem("Options", () -> engine.getGameStateManager()
//	                                                .push(new OptionsState(), engine)),
	            new MenuItem("Quit", () -> System.exit(0))
	        );
	}

}
