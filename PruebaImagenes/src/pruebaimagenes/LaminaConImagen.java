/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimagenes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;

/**
 *
 * @author eduwo
 */
public class LaminaConImagen extends javax.swing.JPanel {

    private int[][] convolucion = {
        {-1, -1, -1},
        {0, 0, 0},
        {1, 1, 1}
    };

    private static int profundidad = 3;

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public int[][] getConvolucion() {
        return convolucion;
    }

    public void setConvolucion(int[][] convolucion) {
        this.convolucion = convolucion;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        File miImagen = new File("src/image.jpg");

        try {

            BufferedImage imagen = ImageIO.read(miImagen);
            g.drawImage(imagen, 0, 0, null);

            WritableRaster raster = imagen.getRaster();
            DataBufferByte imageInBytes = (DataBufferByte) raster.getDataBuffer();

            byte[] data = imageInBytes.getData();

            //cambiarGrises(data, imagen, g);
            // aumentarBrilloRojo(data, imagen, g);
            //aumentarBrilloAzul(data, imagen, g);
            //aumentarBrilloVerde(data, imagen, g);
            //aumentarBrilloGeneral(data, imagen, g);
            recorrerImagen(data, imagen, g);
            System.out.println("");

        } catch (IOException ex) {
            Logger.getLogger(LaminaConImagen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cambiarGrises(byte[] data, BufferedImage imagen, Graphics g) {

        for (int i = 0; i < (imagen.getHeight()); i++) {

            for (int j = 0; j < imagen.getWidth(); j++) {
                int valor = 0;

                for (int k = 0; k < this.getProfundidad(); k++) {

                    int destino = (i * imagen.getWidth() * this.getProfundidad()) + (j * this.getProfundidad()) + k;

                    valor += Byte.toUnsignedInt(data[destino]);
                }

                valor /= 3;

                for (int k = 0; k < this.getProfundidad(); k++) {
                    data[(i * imagen.getWidth() * this.getProfundidad()) + (j * this.getProfundidad()) + k] = (byte) valor;
                }

                /* for (int i = 0; i < data.length; i += 3) {

            int c1 = Byte.toUnsignedInt(data[i]);
            int c2 = Byte.toUnsignedInt(data[i + 1]);
            int c3 = Byte.toUnsignedInt(data[i + 2]);

            int suma = (c1 + c2 + c3) / 3;

            data[i] = (byte) suma;
            data[i + 1] = (byte) suma;
            data[i + 2] = (byte) suma;
       
        }*/
            }

            g.drawImage(imagen, imagen.getWidth(), 0, null);

        }
    }

    public static void aumentarBrilloGeneral(byte[] data, BufferedImage imagen, Graphics g) {

        byte[] imagenOriginal = new byte[data.length];
        System.arraycopy(data, 0, imagenOriginal, 0, data.length);

        for (int i = 0; i < data.length; i += 3) {

            int c1 = Byte.toUnsignedInt(data[i]);
            int c2 = Byte.toUnsignedInt(data[i + 1]);
            int c3 = Byte.toUnsignedInt(data[i + 2]);

            c1 = aumentarBrilloColor(c1);
            c2 = aumentarBrilloColor(c2);
            c3 = aumentarBrilloColor(c3);

            if (c1 > 255) {
                c1 = 255;

            }
            if (c2 > 255) {
                c2 = 255;
            }
            if (c3 > 255) {
                c3 = 255;
            }

            data[i] = (byte) c1;
            data[i + 1] = (byte) c2;
            data[i + 2] = (byte) c3;
        }
        g.drawImage(imagen, imagen.getWidth(), imagen.getHeight() * 2, null);
    }

    public static void aumentarBrilloRojo(byte[] data, BufferedImage imagen, Graphics g) {

        byte[] imagenOriginal = new byte[data.length];
        System.arraycopy(data, 0, imagenOriginal, 0, data.length);

        for (int i = 0; i < data.length; i += 3) {

            int c1 = Byte.toUnsignedInt(data[i]);
            int c2 = Byte.toUnsignedInt(data[i + 1]);
            int c3 = Byte.toUnsignedInt(data[i + 2]);

            c3 = aumentarBrilloColor(c3);

            if (c3 > 255) {
                c3 = (byte) 255;
            }
            data[i] = (byte) c1;
            data[i + 1] = (byte) c2;
            data[i + 2] = (byte) c3;

        }
        g.drawImage(imagen, imagen.getWidth(), 0, null);
        for (int i = 0; i < data.length; i++) {
            data[i] = imagenOriginal[i];
        }

    }

    public static void aumentarBrilloAzul(byte[] data, BufferedImage imagen, Graphics g) {

        byte[] imagenOriginal = new byte[data.length];
        System.arraycopy(data, 0, imagenOriginal, 0, data.length);

        for (int i = 0; i < data.length; i += 3) {

            int c1 = Byte.toUnsignedInt(data[i]);
            int c2 = Byte.toUnsignedInt(data[i + 1]);
            int c3 = Byte.toUnsignedInt(data[i + 2]);

            c1 = aumentarBrilloColor(c1);

            if (c1 > 255) {
                c1 = (byte) 255;
            }
            data[i] = (byte) c1;
            data[i + 1] = (byte) c2;
            data[i + 2] = (byte) c3;
        }
        g.drawImage(imagen, 0, imagen.getHeight(), null);
        for (int i = 0; i < data.length; i++) {
            data[i] = imagenOriginal[i];
        }

    }

    public static void aumentarBrilloVerde(byte[] data, BufferedImage imagen, Graphics g) {

        byte[] imagenOriginal = new byte[data.length];
        System.arraycopy(data, 0, imagenOriginal, 0, data.length);

        for (int i = 0; i < data.length; i += 3) {

            int c1 = Byte.toUnsignedInt(data[i]);
            int c2 = Byte.toUnsignedInt(data[i + 1]);
            int c3 = Byte.toUnsignedInt(data[i + 2]);

            c2 = aumentarBrilloColor(c2);

            if (c2 > 255) {
                c2 = (byte) 255;
            }
            data[i] = (byte) c1;
            data[i + 1] = (byte) c2;
            data[i + 2] = (byte) c3;
        }
        g.drawImage(imagen, imagen.getWidth(), imagen.getHeight(), null);

        for (int i = 0; i < data.length; i++) {
            data[i] = imagenOriginal[i];
        }

    }

    public static int aumentarBrilloColor(int color) {

        color += 255;

        return color;
    }

    public void recorrerImagen(byte[] data, BufferedImage imagen, Graphics g) {

        for (int i = 0; i < imagen.getHeight() - 1; i++) {

            for (int j = 0; j < imagen.getWidth() - 1; j++) {

                if (i == 0 || j == 0 || i == this.getHeight() - 1 || j == this.getWidth() - 1) {

                } else {
                    convolucionImagen(data, imagen, g, i, j);
                }
            }
        }

        g.drawImage(imagen, imagen.getWidth(), imagen.getHeight(), null);

    }

    public void convolucionImagen(byte[] data, BufferedImage imagen, Graphics g, int i, int j) {
        int valor = 0;
        int posicionDestino = 0;
        int posicionOrigen = 0;


                    for (int k = 0; k < this.getProfundidad(); k++) {
                        valor = 0;
                        for (int l = 0; l < this.getConvolucion().length; l++) {
                            for (int m = 0; m < this.getConvolucion()[l].length; m++) {

                                posicionDestino = (i * imagen.getWidth() * this.getProfundidad()) + (j * this.getProfundidad()) + k;
                    posicionOrigen = (((i - 1) + l) * (imagen.getWidth() * this.getProfundidad())) + (((j - 1) + m) * this.getProfundidad()) + k;
                    
                    int valor1 = Byte.toUnsignedInt(data[posicionOrigen]);
                    valor += valor1 * this.getConvolucion()[l][m];
                    //data[posicionDestino] += (Byte.toUnsignedInt(data[posicionOrigen])) * this.getConvolucion()[l][m];
                }
            }
            valor /= calcularTotalPesos();

            if (valor < 0) {

                data[posicionDestino] = (byte) 0;

            } else if (valor> 255) {
                
                data[posicionDestino] = (byte) 255;
            
            } else {

                data[posicionDestino] = (byte)valor;
            }
        }
    }

    public int calcularTotalPesos() {

        int total = 0;

        for (int i = 0; i < getConvolucion().length; i++) {
            for (int j = 0; j < getConvolucion()[i].length; j++) {
                total += getConvolucion()[i][j];
            }
        }
        if (total == 0) {
            total = 1;
        }
        return total;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jButton1)
                .addContainerGap(189, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(139, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(136, 136, 136))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
