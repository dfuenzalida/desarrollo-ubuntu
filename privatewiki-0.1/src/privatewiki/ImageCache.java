package privatewiki;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

public class ImageCache {
	
	private HashMap hashmap;
	
	public ImageCache(){
		hashmap = new HashMap();
	}
	
	public Image getImage(String path){
		
		Image image;
		
		if (hashmap.containsKey(path)){
			image = (Image)hashmap.get(path);
		} else {
			image = Toolkit.getDefaultToolkit().createImage( ImageCache.class.getResource(path));
			hashmap.put(path, image);
		}
		
		return image;
	}

}
