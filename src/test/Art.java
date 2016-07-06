package test;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Art {

	public static Bitmap font = loadBitmap("/art/font.png");
	public static Bitmap items = loadBitmap("/art/items.png");
	
	public static Bitmap loadBitmap(String resourceName) {
		
		try{
		
			System.out.println( "loading art resource: " + resourceName );
			BufferedImage img = ImageIO.read(Art.class.getResource(resourceName));
			
			int w = img.getWidth();
			int h = img.getHeight();
			
			Bitmap result = new Bitmap(w, h);
			img.getRGB(0, 0, w, h, result.pixels, 0, w);
			
			// pre-multiply the alpha for the bitmaps
			// c = as*cs+(1-as) * cd
			// cp = as*cs  // pre-multiplied
			// c = cp + (1 - as) * cd
			for(int i = 0; i < w * h; i++){
				result.pixels[i] = ArtCompositor.premultiply(result.pixels[i]);
				
			}
			
			return result;
			
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
}
