package engine.ui;

import engine.core.Engine;
import engine.graphics.Renderer;
import engine.ui.components.UIComponent;
import engine.ui.components.UIPanel;

/**
 * TODO : make it hold every UIComponent and handles update & render ?
 * Like an arraylist and a way too add remove, hide or show components
 * To later use them in a gamestate like inventory or other...
 * Also if you want to add a way to make a background render on the mainmenustate optionally it can be good
 */
public class UIManager {
	private Engine engine;
	private float displayWidth, displayHeight;
	private UIComponent root;
	
	public UIManager(Engine engine) {
		this.engine = engine;
		displayWidth = engine.getCamera().width;
	    displayHeight = engine.getCamera().height;
	    root = new UIPanel(0, 0, displayWidth, displayHeight, null);
	}
	
	public UIComponent getRoot() {
        return root;
    }

    public void render(Renderer r) {
        root.render(r);
    }
	
	public float getDisplayWidth() {
		return displayWidth;
	}
	
	public float getDisplayHeight() {
		return displayHeight;
	}
}