package map;

import java.awt.image.BufferedImage;
import java.io.Serializable;
/**
 * Used to wrap a BufferedImage for serialization
 * Not fully implemented yet.
 * Unused
 * @author Alexander
 * @version 1.0 2016-03-04
 */
public class SerializeableBufferedImage implements Serializable{
	private static final long serialVersionUID = -8565146954702818348L;
	private transient BufferedImage image;
	
	/**
	 * Constructs a BufferedImage of one of the predefined image types.
	 * @param width width of the created image 
	 * @param height height of the created image
	 * @param imageType type of the created image
	 */
	public SerializeableBufferedImage(int width, int height, int imageType){
		image = new BufferedImage(width, height, imageType);
	}
	
	/**
	 * Used to get the wrapped BufferedImage
	 * @return the current image
	 */
	public BufferedImage getImage(){
		return image;
	}
	
	/**
	 * Used to set the wrapped BufferedImage
	 * @param img the new BufferedImage
	 */
	public void setImage(BufferedImage img){
		image = img;
	}
	
	/**
	 * http://stackoverflow.com/questions/15058663/how-to-serialize-an-object-that-includes-bufferedimages
	 * @param out
	 * @throws IOException
	 */
	/*private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(image, "png", out); // png is lossless
    }*/
	
	/**
	 * http://stackoverflow.com/questions/15058663/how-to-serialize-an-object-that-includes-bufferedimages
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
   /* private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    	in.defaultReadObject();
        image = ImageIO.read(in);
    }*/
}
