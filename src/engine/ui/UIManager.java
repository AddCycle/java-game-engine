package engine.ui;

import engine.core.Engine;

/**
 * TODO : make it hold every UIComponent and handles update & render ?
 * Like an arraylist and a way too add remove, hide or show components
 * To later use them in a gamestate like inventory or other...
 * Also if you want to add a way to make a background render on the mainmenustate optionally it can be good
 */
public class UIManager {
	private Engine engine;
	private float displayWidth, displayHeight;
	
	public UIManager(Engine engine) {
		this.engine = engine;
		getDisplayGeometry();
	}
	
	public void getDisplayGeometry() {
		displayWidth = engine.getCamera().width;
		displayHeight = engine.getCamera().height;
	}
}