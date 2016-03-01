package map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class MyBufferedImage implements Serializable{
	private static final long serialVersionUID = -8565146954702818348L;
	private transient BufferedImage image;
	
	public MyBufferedImage(){
		image = null;
	}
	
	public MyBufferedImage(int width, int height, int type){
		image = new BufferedImage(width, height, type);
	}
	
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage img){
		image = img;
	}
	
	/**
	 * http://stackoverflow.com/questions/15058663/how-to-serialize-an-object-that-includes-bufferedimages
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(image, "png", out); // png is lossless
    }
	
	/**
	 * http://stackoverflow.com/questions/15058663/how-to-serialize-an-object-that-includes-bufferedimages
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = ImageIO.read(in);
    }
}
