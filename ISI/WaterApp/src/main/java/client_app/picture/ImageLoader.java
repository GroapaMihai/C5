package client_app.picture;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import client_app.appl.dao.PictureDao;
import client_app.appl.domain.builders.PictureBuilder;
import client_app.appl.domain.entities.Picture;

public class ImageLoader {
	private static ImageLoader instance = null;

	private ImageLoader() {
	}

	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		
		return instance;
	}

    public BufferedImage getImageFromLocal(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public Image getScaledImageFromLocal(String path, int width, int height) {
        Image image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return scaledImage;
    }

    public BufferedImage getImageFromDB(String pictureName) {
    	PictureDao pictureDao = PictureDao.getInstance();
    	PictureBuilder pictureBuilder = new PictureBuilder();
    	Picture picture;
    	List<Picture> matchingPictures;
    	InputStream in;
    	Blob blob;
    	BufferedImage bufferedImage = null;

    	picture = pictureBuilder
    		.withName(pictureName)
    		.build();

		matchingPictures = pictureDao.findMatchingEntities(picture);

    	if (matchingPictures.isEmpty()) {
    		return null;
    	}

		picture = matchingPictures.get(0);
		blob = picture.getPicture();

		try {
			in = blob.getBinaryStream();
			bufferedImage = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bufferedImage;
    }

    public Image getScaledImageFromDB(String pictureName, int width, int height) {
    	PictureDao pictureDao = PictureDao.getInstance();
    	PictureBuilder pictureBuilder = new PictureBuilder();
    	Picture picture;
    	List<Picture> matchingPictures;
    	InputStream in;
    	Blob blob;
    	BufferedImage bufferedImage = null;
    	Image scaledImage;

    	picture = pictureBuilder
    		.withName(pictureName)
    		.build();

		matchingPictures = pictureDao.findMatchingEntities(picture);

    	if (matchingPictures.isEmpty()) {
    		return null;
    	}

		picture = matchingPictures.get(0);
		blob = picture.getPicture();

		try {
			in = blob.getBinaryStream();
			bufferedImage = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

    	return scaledImage;
    }
    
    public Blob getBlobOfFile(File file) {
    	byte[] byteArray;
    	Blob pictureBlob = null;

        try {
            byteArray = Files.readAllBytes(file.toPath());
			pictureBlob = new SerialBlob(byteArray);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}

        return pictureBlob;
    }
}
