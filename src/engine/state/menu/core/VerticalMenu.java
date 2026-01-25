package engine.state.menu.core;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.inputs.Inputs;
import engine.inputs.action.Action;
import engine.inputs.keybinds.Keybinds;
import engine.inputs.keybinds.MenuKeybinds;
import engine.ui.UIManager;
import engine.ui.components.UIBox;
import engine.ui.components.UILabel;
import engine.ui.constraints.Anchor;
import engine.ui.constraints.Margin;
import engine.ui.constraints.Padding;

/**
 * TODO : refactor this class to integrate the UIComponent class inside of it
 * And maybe refactor also the menu to use some of the UI
 */
public class VerticalMenu implements Menu {
	private MenuItem[] items;
	private UILabel[] itemLabels;
	private UILabel caret;
	private int selected = 0;
	private Keybinds keybinds;
	private Engine engine;
	private Inputs inputs;

	public VerticalMenu(Engine engine, MenuItem... items) {
		this.engine = engine;
		this.items = items;
		this.keybinds = new MenuKeybinds();
		this.inputs = engine.getInput();
		createMenuBox();
	}

	@Override
	public void onOpen() {
	}

	/**
	 * FIXME : autoresizing with text length is working but clean the code a little or maybe introduce a layer ?
	 */
	public void createMenuBox() {
		UIManager uiManager = engine.getUIManager();
		
		float maxW = 0;
		float maxH = 0;
		for (int i = 0; i < items.length; i++) {
			int[] dims = Renderer.calculateTextDimensions(items[i].label);
			if (dims[0] > maxW) maxW = dims[0];
			if (dims[1] > maxH) maxH = dims[1];
		}
		
		float little_margin = 40;
		UIBox box = new UIBox(0, 0, little_margin + maxW, 140, uiManager.getRoot()); // TODO : compute maxH too
		box.setMargin(new Margin(0, 10, 0, 0));
		box.setPadding(new Padding(5, 5, 5, 0));
		box.setAnchor(Anchor.CENTER_RIGHT);

		itemLabels = new UILabel[items.length];
		
		int margin_top = 10;
		for (int i = 0; i < items.length; i++) {
	        UILabel label = new UILabel(0, 0, box);
	        label.setMargin(new Margin(0, 0, margin_top, 0));
	        label.setText(items[i].label);
	        label.setAnchor(Anchor.TOP_CENTER);
	        box.add(label);
	        itemLabels[i] = label;
	        margin_top += 20;
	    }
		
		caret = new UILabel(0, 10, box);
	    caret.setText(">");
	    box.add(caret);
		
		uiManager.getRoot().add(box);
	}

	@Override
	public void update(float dt) {
		if (keybinds.isJustPressed(inputs, Action.MOVE_UP))
			selected = (selected - 1 + items.length) % items.length;

		if (keybinds.isJustPressed(inputs, Action.MOVE_DOWN))
			selected = (selected + 1) % items.length;

		if (keybinds.isJustPressed(inputs, Action.CONFIRM))
			items[selected].action.run();
		
		updateCaret();
	}

	@Override
	public void render(Renderer renderer) {
		engine.getUIManager().getRoot().render(renderer);
	}

	@Override
	public void onClose() {
		engine.getUIManager().getRoot().popChild();
	}
	
	private void updateCaret() {
	    UILabel target = itemLabels[selected];
	    caret.setY(target.getY());
	}
}