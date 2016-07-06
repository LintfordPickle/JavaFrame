package com.ruse.javabase.graphics;

// http://www.java-gaming.org/index.php?topic=24529.0
public class ArtCompositor {

	public static final int ALPHA_MASK = 0xff000000;
	public static final int RED_MASK = 0x00ff0000;
	public static final int GREEN_MASK = 0x0000ff00;
	public static final int BLUE_MASK = 0x000000ff;

	public static int premultiply(int argb) {
		int a = argb >>> 24;

		if (a == 0)
			return 0;
		else if (a == 255)
			return argb;
		else
			return (a << 24) | multiplyRGB(argb, a);
	}

	public static int multiplyRGB(int argb, int a) {
		a++;
		return ((argb & RED_MASK) * a) >> 8 & RED_MASK | ((argb & GREEN_MASK) * a) >> 8 & GREEN_MASK | ((argb & BLUE_MASK) * a) >> 8 & BLUE_MASK;
	}

	public static int unpremultiply(int preARGBColor) {
		int a = preARGBColor >>> 24;

		if (a == 0) {
			return 0;
		} else if (a == 255) {
			return preARGBColor;
		} else {
			int r = (preARGBColor >> 16) & 0xff;
			int g = (preARGBColor >> 8) & 0xff;
			int b = preARGBColor & 0xff;

			r = 255 * r / a;
			g = 255 * g / a;
			b = 255 * b / a;
			return (a << 24) | (r << 16) | (g << 8) | b;
		}
	}

	public static int blendPixel_Multiply(int dstPx, int srcPx, int alpha) {
		int destPx = dstPx;
		int srcA = srcPx >>> 24;
		int srcR = (srcPx & RED_MASK) >>> 16;
		int srcG = (srcPx & GREEN_MASK) >>> 8;
		int srcB = (srcPx & BLUE_MASK);

		if (alpha != 255) {
			srcR = mult(srcR, alpha);
			srcG = mult(srcG, alpha);
			srcB = mult(srcB, alpha);
			srcA = mult(srcA, alpha);
		}

		int destA = (destPx) >>> 24;
		int destR = (destPx & RED_MASK) >>> 16;
		int destG = (destPx & GREEN_MASK) >>> 8;
		int destB = (destPx & BLUE_MASK);
		final int oneMinusSrcA = 0xff - srcA, oneMinusDstA = 0xff - destA;
		return blend(srcA, destA, srcA) << 24 | (mult(srcR, destR) + mult(destR, oneMinusSrcA) + mult(srcR, oneMinusDstA)) << 16 | (mult(srcG, destG) + mult(destG, oneMinusSrcA) + mult(srcG, oneMinusDstA)) << 8 | mult(srcB, destB) + mult(destB, oneMinusSrcA) + mult(srcB, oneMinusDstA);
	}

	public static int blendPixel_Add(int dstPx, int srcPx) {
		int destPx = dstPx;
		int srcA = srcPx >>> 24;
		int srcR = (srcPx & RED_MASK) >>> 16;
		int srcG = (srcPx & GREEN_MASK) >>> 8;
		int srcB = (srcPx & BLUE_MASK);

		int destA = (destPx) >>> 24;
		int destR = (destPx & RED_MASK) >>> 16;
		int destG = (destPx & GREEN_MASK) >>> 8;
		int destB = (destPx & BLUE_MASK);

		return min(srcA + destA, 0xff) << 24 | min(srcR + destR, 0xff) << 16 | min(srcG + destG, 0xff) << 8 | min(srcB + destB, 0xff);
	}

	public static int blend(int src, int dest, int alpha) {
		return src + mult(dest, 0xFF - alpha);
	}

	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static int mult(int a, int b) {
		final int t = a * b + 128;
		return (t >> 8) + t >> 8;
	}

}
