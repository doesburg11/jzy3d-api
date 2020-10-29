package org.jzy3d.plot3d.rendering.view;

import java.nio.ByteBuffer;

import org.jzy3d.painters.Painter;

import com.jogamp.opengl.GL;

public class ImageRenderer {
	public static void renderImage(Painter painter, ByteBuffer image, int imageWidth, int imageHeight, int screenWidth,
			int screenHeight) {
		renderImage(painter, image, imageWidth, imageHeight, screenWidth, screenHeight, 0.75f);
	}

	public static void renderImage(Painter painter, ByteBuffer image, int imageWidth, int imageHeight, int screenWidth,
			int screenHeight, float z) {
		if (image == null)
			return;

		float xratio = 1;
		float yratio = 1;
		int xpict = 0;
		int ypict = 0;

		if (imageWidth < screenWidth)
			xpict = (int) ((float) screenWidth / 2 - (float) imageWidth / 2);
		else
			xratio = ((float) screenWidth) / ((float) imageWidth);

		if (imageHeight < screenHeight)
			ypict = (int) ((float) screenHeight / 2 - (float) imageHeight / 2);
		else
			yratio = ((float) screenHeight) / ((float) imageHeight);

		// Draw
		painter.glPixelZoom(xratio, yratio);
		painter.glRasterPos3f(xpict, ypict, z);

		synchronized (image) { // we don't want to draw image while it is being set by setImage
			painter.glDrawPixels(imageWidth, imageHeight, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, image);
		}
	}
}