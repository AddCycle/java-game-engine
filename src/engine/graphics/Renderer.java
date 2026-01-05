package engine.graphics;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.GL11;

import engine.loader.TextureLoader;

public class Renderer {
	private TextureLoader textureLoader;
	public static float ZOOM = 16.0f; // 2x, 3x, 0.75f, etc.

	public Renderer(TextureLoader texLoader) {
		this.textureLoader = texLoader;
	}
	
	public int loadTexture(String texPath) {
		return textureLoader.loadTexture(texPath);
	}

	public void drawTexturedRect(int textureID, float x, float y, float width, float height) {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureID);

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); glVertex2f(x, y); // bottom-left
	    glTexCoord2f(0, 1); glVertex2f(x, y + height); // top-left
	    glTexCoord2f(1, 1); glVertex2f(x + width, y + height); // top-right
	    glTexCoord2f(1, 0); glVertex2f(x + width, y); // bottom-right
		glEnd();
	}

	public static void drawRect(float x, float y, float width, float height) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);

		glVertex2f(x, y); // bottom-left
	    glVertex2f(x, y + height); // top-left
	    glVertex2f(x + width, y + height); // top-right
	    glVertex2f(x + width, y); // bottom-right
		GL11.glEnd();
	}

	public static void updateViewport(int windowW, int windowH) {
	    float worldW = windowW / ZOOM;
	    float worldH = windowH / ZOOM;

	    int viewW = (int)(worldW * ZOOM);
	    int viewH = (int)(worldH * ZOOM);

	    int x = (windowW - viewW) / 2;
	    int y = (windowH - viewH) / 2;

	    GL11.glViewport(x, y, viewW, viewH);

	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, worldW, worldH, 0, -1, 1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glLoadIdentity();
	}
}