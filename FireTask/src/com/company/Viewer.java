package com.company;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

public class Viewer extends Canvas {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        File file = new File(".src/com/company/subaru.png");
        try {

            if(!file.exists()) System.out.println("funciona");
            BufferedImage img = ImageIO.read(file);

            WritableRaster raster = img.getRaster();
            DataBufferByte imageInBytes = (DataBufferByte) raster.getDataBuffer();

            byte[] data = imageInBytes.getData();
            g.drawImage(img, 0, 0, null);

        } catch (Exception e) {
            System.out.println("peta");
        }
    }

    public BufferedImage createImage() {

        // Constructs a BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        try {

            ImageIO.write(bufferedImage, "png", new File("img1.png"));

        } catch (Exception e) {

        }
        return bufferedImage;
    }
}
