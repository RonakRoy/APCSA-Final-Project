package net.sduhsd.royr6099.firststeamworks;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static Image loadImage(String name) {
		Image image = null;
		
		try {
			image = ImageIO.read(new File("src/net/sduhsd/royr6099/firststeamworks/media/" + name));
		} catch (Exception e) {
			System.out.println("Couldn't load the image \"" + name + "\"");
		}
		
		return image;
	}
}
