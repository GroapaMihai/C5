package client_app.picture;

import java.io.File;

import javax.swing.*;

import client_app.gui.views.View;

public class ImageBrowseFrame {
    private JFileChooser fc;
    private boolean isImage;
    private String filePath;
    private View view;
    private static String imageExtensions[] = {
        ".ai", ".bmp", ".gif", ".ico", ".jpg", ".jpeg",
        ".png", ".ps", ".psd", ".svg", ".tif", ".tiff"
    };

    public ImageBrowseFrame(View view) {
        this.view = view;
        String userDir = System.getProperty("user.home");
        fc = new JFileChooser(userDir + "/Desktop");
    }

    public void browse() {
        if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            filePath = selectedFile.toPath().toString();
            isImage = fileIsImage(filePath);
        }
    }

    private boolean fileIsImage(String fileName) {
        int pointPosition = fileName.lastIndexOf('.');
        String extension = fileName.substring(pointPosition);

        for (int i = 0; i < imageExtensions.length; i++) {
            if (extension.equalsIgnoreCase(imageExtensions[i])) {
                return true;
            }
        }

        return false;
    }

    public boolean selectedFileIsImage() {
        return isImage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void clearFilePath() {
        if (filePath != null) {
            filePath = null;
        }
    }
}
