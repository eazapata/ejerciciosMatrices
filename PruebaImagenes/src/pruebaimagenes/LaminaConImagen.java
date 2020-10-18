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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.ImageIcon;
import static sun.security.krb5.Confounder.*;

/**
 *
 * @author eduwo
 */
public class LaminaConImagen extends javax.swing.JPanel {

    private int profundidad;

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        File miImagen = new File("src/image.jpg");

        try {

            this.setProfundidad(this.getProfundidad() + 3);

            BufferedImage imagen = ImageIO.read(miImagen);
            g.drawImage(imagen, 0, 0, null);

            WritableRaster raster = imagen.getRaster();
            DataBufferByte imageInBytes = (DataBufferByte) raster.getDataBuffer();

            byte[] data = imageInBytes.getData();

            cambiarGrises(data, imagen, g);
            /*aumentarBrilloRojo(data, imagen, g);
            aumentarBrilloAzul(data, imagen, g);
            aumentarBrilloVerde(data, imagen, g);
            aumentarBrilloGeneral(data, imagen, g);*/

            System.out.println("");

        } catch (IOException ex) {
            Logger.getLogger(LaminaConImagen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void cambiarGrises(byte[] data, BufferedImage imagen, Graphics g) {

        
        int valor = 0;
        
        for (int i = 0; i < (imagen.getHeight()) * 3; i++) {

            for (int j = 0; j < imagen.getWidth() / 3 ; j++) {

                for (int k = 0; k < 3; k++) {
                    
                    
                    int posicionVectorDestino = (imagen.getWidth() * i) + (3 * j) + k;
                    int posicionVectorOrigen = posicionVectorDestino;
                    if (k == 1) {
                        posicionVectorDestino -= 1;
                    } else if (k == 2) {
                        posicionVectorDestino -= 2;
                    }
                    valor += Byte.toUnsignedInt(data[posicionVectorOrigen]);
                   

                    if (k == 2) {
                        valor /= 3;
                        data[posicionVectorDestino] = (byte) valor;
                        data[posicionVectorDestino + 1] = (byte) valor;
                        data[posicionVectorDestino + 2] = (byte) valor;
                    }

                    //data[poscionVector] += 
                }
                valor = 0;
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

        color += 255;//(color *50)/100;

        return color;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
