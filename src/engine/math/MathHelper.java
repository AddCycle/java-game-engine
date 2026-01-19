package engine.math;

import java.awt.Color;

public class MathHelper {

	/**
	 * This function does linear interpolation between a and b with smoothing factor
	 * @param a
	 * @param b
	 * @param smoothing between 0 and 1
	 * @return
	 */
	public static float lerp(float a, float b, float smoothing) {
	    return a + (b - a) * smoothing;
	}
	
	/**
	 * This function returns a clamped color between 0 and 1 (useful for OpenGL colors)
	 * @param c
	 * @return float between 0f and 1f
	 */
	public static float clampColor(float c) {
		return Math.clamp(c, 0f, 1f);
	}

	/**
	 * Converts a java.awt.Color to a float[3] {r, g, b} between 0f & 1f
	 * @param c
	 * @return the three color components without alpha value [r,g,b]
	 */
	public static float[] convertColor(Color c) {
		float[] colors = c.getRGBColorComponents(null);
		return colors;
	}
}
