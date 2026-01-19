package engine.ui.hud;

import java.awt.Color;

import engine.graphics.Renderer;
import engine.ui.UIManager;
import engine.ui.components.UIBox;
import engine.ui.components.UIPanel;
import engine.ui.constraints.Anchor;

public class PlayHUD {
	private UIPanel root;

    public PlayHUD(UIManager ui) {
        root = new UIPanel(0, 0, ui.getDisplayWidth(), ui.getDisplayHeight(), ui.getRoot());

        /* Testing some layout concepts,
         * TODO create components in order to use them properly
         *
         */
        UIBox box = new UIBox(0, 0, 100, 100, root);
        box.setBackgroundColor(Color.black);
        box.setOutlineColor(Color.white);
        box.setAnchor(Anchor.BOTTOM_CENTER);
        box.setBorder(4);
        
        root.add(box);
    }
    
    public void render(Renderer renderer) {
    		root.render(renderer);
    }

    public UIPanel getRoot() {
        return root;
    }
}
