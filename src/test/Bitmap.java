package test;

public class Bitmap {

	private String characterSequence = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!\"$%-_&()[]?\\/<>.;:;|";

	public final int width, height;
	public int[] pixels;

	public Bitmap(int w, int h) {
		width = w;
		height = h;

		pixels = new int[width * height];

	}

	public void draw(Bitmap b, int dx, int dy) {
		draw(b, dx, dy, 0, 0, b.width, b.height, 1);

	}

	public void draw(Bitmap b, int dx, int dy, int sx, int sy, int sw, int sh, int scale) {
		for (int x = 0; x < sw; x++) {
			for (int y = 0; y < sh; y++) {
				int srcX = x + sx;
				int srcY = y + sy;
				if (srcX < 0 || srcX >= b.width)
					continue;
				if (srcY < 0 || srcY >= b.height)
					continue;

				int dstX = dx + x;
				int dstY = dy + y;

				if (dstX < 0 || dstX > width - 1)
					continue;
				if (dstY < 0 || dstY > height - 1)
					continue;

				int srcP = b.pixels[srcY * b.width + srcX];
				int dstP = pixels[dstY * width + dstX];
				
				int tmp0 = ArtCompositor.blendPixel_Add(dstP, srcP);
				int tmp1 = ArtCompositor.unpremultiply( tmp0 );
								
				// Assign the new color
				pixels[dstY * width + dstX] = tmp0;

			}

		}

	}

	public void drawString(Bitmap b, String t, int dx, int dy) {
		if (t == null || t.isEmpty())
			return;

		int charWidth = 8;
		int charStepX = 0;
		int charStepY = 0;

		for (int i = 0; i < t.length(); i++) {
			char c = t.charAt(i);

			// linebreak
			if (c == '\n') {
				charStepX = 0;
				charStepY += 10;
				continue;
			}

			int charIndex = characterSequence.indexOf(c);
			if (charIndex == -1) {
				charStepX += charWidth;
				continue;
			}

			int sx = charIndex % 16;
			int sy = charIndex / 16;

			draw(b, dx + charStepX, dy + charStepY, sx * 8, sy * 8, 8, 8, 1);

			charStepX += charWidth;

		}

	}

	public void clear(int color) {
		java.util.Arrays.fill(pixels, color);
	}

}
