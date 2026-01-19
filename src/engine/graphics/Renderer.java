package engine.graphics;

import static engine.math.MathHelper.clampColor;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import engine.core.Logger;
import engine.loader.FontLoader;
import engine.loader.Loader;
import engine.loader.TextureLoader;
import engine.math.MathHelper;
import engine.world.Camera;

/**
 * TODO : renderer too complicated, should only tell how to render / not what to render
 * TODO : make a separate layer around UI to what to draw, dialog boxes...
 */
public class Renderer {
	private TextureLoader textureLoader;
	private FontLoader fontLoader;
	private Camera camera;
//	public static float ZOOM = 1.0f; // 2x, 3x, 0.75f, etc.

	private static final int FONT_COLS = 32;
	private static final int FONT_ROWS = 4;

	public Renderer(Camera camera, Loader loader) {
		this.camera = camera;
		this.textureLoader = loader.getTextureLoader();
		this.fontLoader = loader.getFontLoader();
	}

	public int loadTexture(String texPath) {
		return textureLoader.loadTexture(texPath);
	}

	public void drawTexturedRect(int textureID, float x, float y, float width, float height) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glPushMatrix();

		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureID);

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); glVertex2f(x, y); // bottom-left
	    glTexCoord2f(0, 1); glVertex2f(x, y + height); // top-left
	    glTexCoord2f(1, 1); glVertex2f(x + width, y + height); // top-right
	    glTexCoord2f(1, 0); glVertex2f(x + width, y); // bottom-right
		glEnd();

		GL11.glPopMatrix();
	    GL11.glPopAttrib();
	}
	
	public void drawTile(Tileset tileset, int tileX, int tileY, float x, float y, float width, float height) {
	    float[] uv = tileset.getTileUV(tileX, tileY); // u0,v0,u1,v1
	    float u0 = uv[0], v0 = uv[1], u1 = uv[2], v1 = uv[3];

	    GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
	    GL11.glPushMatrix();

	    glEnable(GL11.GL_TEXTURE_2D);
	    glBindTexture(GL11.GL_TEXTURE_2D, tileset.getTextureID());

	    GL11.glBegin(GL11.GL_QUADS);
	        glTexCoord2f(u0, v0); glVertex2f(x, y);
	        glTexCoord2f(u0, v1); glVertex2f(x, y + height);
	        glTexCoord2f(u1, v1); glVertex2f(x + width, y + height);
	        glTexCoord2f(u1, v0); glVertex2f(x + width, y);
	    GL11.glEnd();

	    GL11.glPopMatrix();
	    GL11.glPopAttrib();
	}
	
	public static void drawRect(float x, float y, float width, float height, Color c) {
		float[] colors = MathHelper.convertColor(c);
		drawRect(x, y, width, height, colors[0], colors[1], colors[2]);
	}

	public static void drawRect(float x, float y, float width, float height, float r, float g, float b) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_CURRENT_COLOR | GL11.GL_CURRENT_BIT);
		GL11.glPushMatrix();
		
		GL11.glColor3f(clampColor(r), clampColor(g), clampColor(b));
		GL11.glBegin(GL11.GL_QUADS);

		glVertex2f(x, y); // bottom-left
	    glVertex2f(x, y + height); // top-left
	    glVertex2f(x + width, y + height); // top-right
	    glVertex2f(x + width, y); // bottom-right
		GL11.glEnd();
		
		GL11.glPopMatrix();
	    GL11.glPopAttrib();
	}

	public static void drawRect(float x, float y, float width, float height) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glPushMatrix();
		
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);

		glVertex2f(x, y); // bottom-left
	    glVertex2f(x, y + height); // top-left
	    glVertex2f(x + width, y + height); // top-right
	    glVertex2f(x + width, y); // bottom-right
		GL11.glEnd();
		
		GL11.glPopMatrix();
	    GL11.glPopAttrib();
	}

	public void updateViewport(int windowW, int windowH) {
		float scaleX = windowW / camera.width;
	    float scaleY = windowH / camera.height;
	    float scale = Math.min(scaleX, scaleY);

	    int viewW = (int)(camera.width * scale);
	    int viewH = (int)(camera.height * scale);

	    int x = (windowW - viewW) / 2;
	    int y = (windowH - viewH) / 2;

	    GL11.glViewport(x, y, viewW, viewH);

	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, camera.width, camera.height, 0, -1, 1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glLoadIdentity();
	    
	    Logger.info("updated viewport %d,%d", windowW, windowH);
	}
	
	// TODO : move & tweak everything inside the UI Layer when done
	public void drawDialogBox(float x, float y, float w, float h) {
	    Renderer.drawRect(x, y, w, h, 1f, 1f, 1f);      // border
	    Renderer.drawRect(x+2, y+2, w-4, h-4, 0f, 0f, 0f); // background
	}

	public void drawTextCenteredH(String text, float x, float y, int charW, int charH) {
		float textW = text.length() * charW;

		float x1 = x - textW / 2f;

		drawText(text, x1, y, charW, charH);
	}

	public void drawTextCenteredV(String text, float x, float y, int charW, int charH) {
		float y1 = y - charH / 2f;

		drawText(text, x, y1, charW, charH);
	}

	public void drawTextCentered(String text, float x, float y, int charW, int charH) {
		float textW = text.length() * charW;

		float x1 = x - textW / 2f;
		float y1 = y - charH / 2f;
		
		drawText(text, x1, y1, charW, charH);
	}
	
	public void drawTextCenteredInBounds(String text, float width, float height) {
		int charW = 8;
		int charH = 8;

		float textW = text.length() * charW;
		float textH = charH;

		float x = width / 2f - textW / 2f;
		float y = height / 2f - textH / 2f;

		drawText(text, x, y, charW, charH);
	}
	
	/* End of moving */

	public void drawText(String text, float x, float y) {
	    float startX = x;
	    int charW = 8;
	    int charH = 8;

	    for (char c : text.toCharArray()) {
	        if (c == '\n') {
	            y += charH;
	            x = startX;
	            continue;
	        }

	        drawChar(c, x, y, charW, charH);
	        x += charW;
	    }
	}
	
	public void drawText(String text, float x, float y, int charW, int charH) {
	    float startX = x;

	    for (char c : text.toCharArray()) {
	        if (c == '\n') {
	            y += charH;
	            x = startX;
	            continue;
	        }

	        drawChar(c, x, y, charW, charH);
	        x += charW;
	    }
	}

	private void drawChar(char c, float x, float y, int charW, int charH) {
	    int ascii = c;

	    int font = fontLoader.getFontTexture();
	    if (font == -1) {
	    		Logger.error("Trying to drawChar without loading a font texture before in Renderer");
	    		return;
	    }

	    int col = ascii % FONT_COLS;
	    int row = ascii / FONT_COLS;

	    float u = col / (float) FONT_COLS;
	    float v = row / (float) FONT_ROWS;
	    float uSize = 1f / FONT_COLS;
	    float vSize = 1f / FONT_ROWS;

	    GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, font);

	    GL11.glBegin(GL11.GL_QUADS);
	    glTexCoord2f(u, v);                 glVertex2f(x, y);
	    glTexCoord2f(u, v + vSize);         glVertex2f(x, y + charH);
	    glTexCoord2f(u + uSize, v + vSize); glVertex2f(x + charW, y + charH);
	    glTexCoord2f(u + uSize, v);         glVertex2f(x + charW, y);
	    GL11.glEnd();

	    GL11.glPopAttrib();
	}
}