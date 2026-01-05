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

	public Renderer(TextureLoader texLoader) {
		this.textureLoader = texLoader;
	}
	
	public int loadTexture(String texPath) {
		return textureLoader.loadTexture(texPath);
	}

	public void drawTexturedRect(int textureID) {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureID);

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); glVertex2f(-0.5f, -0.5f); // bottom-left
	    glTexCoord2f(0, 1); glVertex2f(-0.5f,  0.5f); // top-left
	    glTexCoord2f(1, 1); glVertex2f( 0.5f,  0.5f); // top-right
	    glTexCoord2f(1, 0); glVertex2f( 0.5f, -0.5f); // bottom-right
		glEnd();
	}

	public static void drawRect() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(-0.5f, -0.5f);
		GL11.glVertex2f(-0.5f, 0.5f);
		GL11.glVertex2f(0.5f, 0.5f);
		GL11.glVertex2f(0.5f, -0.5f);
		GL11.glEnd();
	}
}
