import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Ventana miVentana = new Ventana();
        miVentana.setVisible(true);
        Panel contenedor = new Panel();
        miVentana.add(contenedor);

        miVentana.setSize(1920,1080);

        miVentana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
