    import javax.imageio.ImageIO;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.awt.image.DataBufferByte;
    import java.awt.image.WritableRaster;
    import java.io.File;
    import java.io.IOException;

public class Panel extends JPanel {

    private static int profuncidad = 3;
    private int altura;
    private int ancho;
    private int[][] matrizConvolucion = {
            {-2, -1, 0},
            {-1, 1, 1},
            {0, 1, 2}
    };


    public int[][] getMatrizConvolucion() {
        return matrizConvolucion;
    }

    public void setMatrizConvolucion(int[][] matrizConvolucion) {
        this.matrizConvolucion = matrizConvolucion;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public static int getProfundidad() {
        return profuncidad;
    }

    public static void setProfuncidad(int profuncidad) {
        Panel.profuncidad = profuncidad;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage imagen = ImageIO.read(new File("./src/subaru.jpg"));
            g.drawImage(imagen, 5, 5, null);

            setAltura(imagen.getHeight());
            setAncho(imagen.getWidth());

            WritableRaster raster = imagen.getRaster();
            DataBufferByte imageInBytes = (DataBufferByte) raster.getDataBuffer();

            byte[] data = imageInBytes.getData();

            // cambiarGrises(data,g,imagen);
            convolucionImagen(data, imagen, g);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cambiarGrises(byte[] data, Graphics g, BufferedImage imagen) {
        int colorAvg;
        for (int i = 0; i < getAltura(); i++) {
            for (int j = 0; j < getAncho(); j++) {
                colorAvg = 0;
                for (int k = 0; k < getProfundidad(); k++) {
                    colorAvg += Byte.toUnsignedInt(data[(i * getAncho() * getProfundidad()) + (j * getProfundidad()) + k]);
                }
                colorAvg /= 3;
                for (int k = 0; k < getProfundidad(); k++) {
                    data[(i * getAncho() * getProfundidad()) + (j * getProfundidad()) + k] = (byte) colorAvg;
                }
            }
        }
        g.drawImage(imagen, imagen.getWidth(), 5, null);
    }

    public void convolucionImagen(byte[] data, BufferedImage imagen, Graphics g) {
        byte[] copy = new byte[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        int totalk = calcularK();
        for (int i = 1; i < getAltura() - 1; i++) {

            for (int j = 1; j < getAncho() - 1; j++) {

                for (int k = 0; k < getProfundidad(); k++) {

                    calcularConvolucion(i, j, k, data, imagen, g, totalk, copy);
                }
            }
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = copy[i];
        }
        g.drawImage(imagen, imagen.getWidth(), 5, null);

    }

    public void calcularConvolucion(int fila, int columna, int capa, byte[] data, BufferedImage imagen, Graphics g, int totalk, byte[] copy) {

        int convolucion = 0;
        int destino = (fila * getAncho() * getProfundidad()) + (columna * getProfundidad()) + capa;
        int origen = 0;

        for (int i = 0; i < getMatrizConvolucion().length; i++) {
            for (int j = 0; j < getMatrizConvolucion()[i].length; j++) {

                origen = (((fila - 1) + i) * (getAncho() * getProfundidad())) + (((columna - 1) + j) * getProfundidad()) + capa;
                int valorByte = (Byte.toUnsignedInt(data[origen]));
                convolucion += valorByte * getMatrizConvolucion()[i][j];

            }
        }

        convolucion /= totalk;

        if (convolucion > 255) {

            convolucion = 255;

        } else if (convolucion < 0) {

            convolucion = 0;
        }
        copy[destino] = (byte) convolucion;

    }

    public int calcularK() {
        int total = 0;
        for (int i = 0; i < getMatrizConvolucion().length; i++) {
            for (int j = 0; j < getMatrizConvolucion()[i].length; j++) {
                total += getMatrizConvolucion()[i][j];
            }
        }
        if (total == 0) total = 1;
        return total;
    }


}

