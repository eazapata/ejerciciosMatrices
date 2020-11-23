package firetask1;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;


public class MyImage {

    private int height, width, imageType;
    private BufferedImage myImg;
    private Boolean imgFiltered = false;
    private Boolean edited;
    private byte[] vector;
    private int profundidad = 3;
    private int redChannel = 0;
    private int greenChannel = 0;
    private int blueChannel = 0;
    private int allChannels = 0;


    public MyImage(int width, int height, int imageType, BufferedImage myImg, Boolean edited) {
        this.width = width;
        this.height = height;
        this.imageType = imageType;
        this.myImg = myImg;
        this.edited = edited;
        this.vector = ((DataBufferByte) this.myImg.getRaster().getDataBuffer()).getData();


    }

    public int getRedChannel() {
        return redChannel;
    }

    public void setRedChannel(int redChannel) {
        this.redChannel = redChannel;
    }

    public int getGreenChannel() {
        return greenChannel;
    }

    public void setGreenChannel(int greenChannel) {
        this.greenChannel = greenChannel;
    }

    public int getBlueChannel() {
        return blueChannel;
    }

    public void setBlueChannel(int blueChannel) {
        this.blueChannel = blueChannel;
    }

    public int getAllChannels() {
        return allChannels;
    }

    public void setAllChannels(int allChannels) {
        this.allChannels = allChannels;
    }

    public Boolean getImgFiltered() {
        return imgFiltered;
    }

    public void setImgFiltered(Boolean imgFiltered) {
        this.imgFiltered = imgFiltered;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public BufferedImage getMyImg() {
        return myImg;
    }

    public void setMyImg(BufferedImage myImg) {
        this.myImg = myImg;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void cambiarBrilloGeneral(BufferedImage img, int brillo) {
        byte[] aux = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        byte[] aux2 = new byte[vector.length];
        for (int i = 0; i < vector.length; i++) {
            aux2[i] = aux[i];
        }
        if (img.getColorModel().hasAlpha()) {
            setProfundidad(4);
        }
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                for (int k = 0; k < getProfundidad(); k++) {
                    int posicionVector = (i * this.myImg.getWidth() * getProfundidad()) + (j * getProfundidad()) + k;
                    int value = Byte.toUnsignedInt(aux[posicionVector]);
                    value = value * (100 + brillo) / 100;
                   // value +=Byte.toUnsignedInt(this.vector[posicionVector];
                    if (value > 255) {
                        vector[posicionVector] = (byte) 255;
                    } else if (value < 0) {
                        vector[posicionVector] = 0;
                    } else {
                        vector[posicionVector] = (byte) value;
                    }
                }
            }
        }
    }

    public void cambiarBrillo(BufferedImage imagen, int brillo, int channel) {

        byte[] aux = ((DataBufferByte) imagen.getRaster().getDataBuffer()).getData();
        //byte[] copy = new byte[aux.length];
        if (this.myImg.getColorModel().hasAlpha()) {
            setProfundidad(4);
            channel += 1;
        }
        for (int i = 0; i < this.myImg.getHeight(); ++i) {
            for (int j = 0; j < this.myImg.getWidth(); ++j) {

                int posicionVector = (i * this.myImg.getWidth() * getProfundidad()) + (j * getProfundidad()) + channel;
                int value = Byte.toUnsignedInt(aux[posicionVector]);
                value = value * (100 + brillo) / 100;
                if (value > 255) {
                    vector[posicionVector] = (byte) 255;
                } else if (value < 0) {
                    vector[posicionVector] = 0;
                } else {
                    vector[posicionVector] = (byte) value;
                }
            }
        }
    }

    public void grayScale(BufferedImage img) {
        WritableRaster raster = img.getRaster();
        DataBufferByte dataBufferByte = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBufferByte.getData();

        int colorAvg;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                colorAvg = 0;
                for (int k = 0; k < getProfundidad(); k++) {
                    if (img.getColorModel().hasAlpha() && k == 0) {
                        setProfundidad(4);
                        k += 1;
                    }
                    colorAvg += Byte.toUnsignedInt(data[(i * img.getWidth() * getProfundidad()) + (j * getProfundidad()) + k]);
                }
                colorAvg /= 3;
                for (int k = 0; k < getProfundidad(); k++) {
                    if (img.getColorModel().hasAlpha() && k == 0) {
                        k += 1;
                    }
                    this.vector[(i * img.getWidth() * getProfundidad()) + (j * getProfundidad()) + k] = (byte) colorAvg;
                }
            }
        }

    }

    public BufferedImage reiniciarBrillo(BufferedImage origin, BufferedImage copy) {
        WritableRaster rasterOrigin = origin.getRaster();
        WritableRaster rasterCopy = copy.getRaster();
        DataBufferByte dataBufferByteOrigin = (DataBufferByte) rasterOrigin.getDataBuffer();
        DataBufferByte dataBufferByteCopy = (DataBufferByte) rasterCopy.getDataBuffer();
        byte[] dataOrigin = dataBufferByteOrigin.getData();
        byte[] dataCopy = dataBufferByteCopy.getData();

        System.arraycopy(dataOrigin, 0, dataCopy, 0, dataOrigin.length);

        return copy;

    }

    public void aplicarFiltro(int aplicaciones,BufferedImage original) {
        byte[] dataOriginal = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
        byte[] copy = new byte[dataOriginal.length];
        System.arraycopy(dataOriginal,0,copy,0,dataOriginal.length);
        for (int i = 0; i < aplicaciones; i++) {
            convolucionImagen(copy);
        }
    }
    public void convolucionImagen(byte[] dataOriginal) {

        Matriz matriz = new Matriz();
        int totalk = calcularK(matriz);
        for (int i = 1; i < this.getMyImg().getHeight() - 1; i++) {
            for (int j = 1; j < this.getMyImg().getWidth() - 1; j++) {
                for (int k = 0; k < getProfundidad(); k++) {
                    calcularConvolucion(i, j, k, totalk, dataOriginal);
                }
            }
        }
       /* for (int i = 0; i < this.vector.length; i++) {
            this.vector[i] = copy[i];
        }*/
    }

    public void calcularConvolucion(int fila, int columna, int capa, int totalk, byte[] dataOriginal) {
        Matriz matriz = new Matriz();
        int convolucion = 0;
        int destino = (fila * this.getMyImg().getWidth() * getProfundidad()) + (columna * getProfundidad()) + capa;
        int origen = 0;

        for (int i = 0; i < matriz.getMatrizConvolucion().length; i++) {
            for (int j = 0; j < matriz.getMatrizConvolucion()[i].length; j++) {
                origen = (((fila - 1) + i) * (this.getMyImg().getWidth() * getProfundidad())) + (((columna - 1) + j) * getProfundidad()) + capa;
                int valorByte = (Byte.toUnsignedInt(dataOriginal[origen]));
                convolucion += valorByte * matriz.getMatrizConvolucion()[i][j];
            }
        }
        convolucion /= totalk;
        if (convolucion > 255) {
            convolucion = 255;
        } else if (convolucion < 0) {
            convolucion = 0;
        }
        this.vector[destino] = (byte) convolucion;
    }

    public int calcularK(Matriz matriz) {
        int total = 0;
        for (int i = 0; i < matriz.getMatrizConvolucion().length; i++) {
            for (int j = 0; j < matriz.getMatrizConvolucion()[i].length; j++) {
                total += matriz.getMatrizConvolucion()[i][j];
            }
        }
        if (total == 0) total = 1;
        return total;
    }

}
