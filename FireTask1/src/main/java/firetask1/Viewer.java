package firetask1;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Viewer extends Canvas {

    private final BufferedImage[] imgs;
    public Viewer() {
        this.imgs = new BufferedImage[4];
    }

    public void setImgs(BufferedImage img, int position) {

        imgs[position] = img;
    }

    public BufferedImage getImg(int position) {
        return imgs[position];
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (imgs.length != 0) {
            g.drawImage(getImg(0), 0, 0, getWidth() / 2, getHeight() / 2, null);
            g.drawImage(getImg(1), this.getWidth() / 2, 0, getWidth() / 2, getHeight() / 2, null);
            g.drawImage(getImg(2), 0, getHeight() / 2, getWidth() / 2, getHeight() / 2, null);
            g.drawImage(getImg(3), this.getWidth() / 2, this.getHeight() / 2, getWidth() / 2, getHeight() / 2, null);
        }
    }
}
