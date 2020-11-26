package firetask1;

import java.awt.image.BufferedImage;

import java.awt.image.DataBufferByte;
import java.util.ArrayList;


public class MyImage {


    private final BufferedImage myImg;
    private boolean isGrey;
    private boolean cuadrado = false;
    private ArrayList<Integer> posicionesCuadrado = new ArrayList<>();
    private final byte[] vector;
    private int profundidad = 3;
    private int redChannel = 0;
    private int greenChannel = 0;
    private int blueChannel = 0;
    private int allChannels = 0;
    private int filter = 0;

    public MyImage(BufferedImage myImg, Boolean isGrey) {
        this.myImg = myImg;
        this.isGrey = isGrey;
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

    public boolean isCuadrado() {
        return cuadrado;
    }

    public void setCuadrado(boolean cuadrado) {
        this.cuadrado = cuadrado;
    }

    public Boolean getGrey() {
        return isGrey;
    }

    public void setGrey(Boolean grey) {
        isGrey = grey;
    }

    public BufferedImage getMyImg() {
        return myImg;
    }


    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public byte[] getVector() {
        return vector;
    }

    // Metodos para ajustar el brillo general y por canales
    public void cambiarBrilloGeneral(byte[] original, byte[] copy, int brillo) {
        if (this.getMyImg().getColorModel().hasAlpha()) {
            setProfundidad(4);
        }
        for (int i = 0; i < this.getMyImg().getHeight(); i++) {
            for (int j = 0; j < this.getMyImg().getWidth(); j++) {
                for (int k = 0; k < getProfundidad(); k++) {
                    int posicionVector = (i * this.myImg.getWidth() * getProfundidad()) + (j * getProfundidad()) + k;
                    int value = Byte.toUnsignedInt(original[posicionVector]);
                    value = value * (100 + brillo) / 100;
                    if (value > 255) {
                        copy[posicionVector] = (byte) 255;
                    } else if (value < 0) {
                        copy[posicionVector] = 0;
                    } else {
                        copy[posicionVector] = (byte) value;
                    }
                }
            }
        }
        for (int i = 0; i < copy.length; i++) {
            getVector()[i] = copy[i];
        }

    }

    public void cambiarBrillo(byte[] copy, int brillo, int channel, int tamaño) {
        int posicionVector = 0;
        int value;
        if (this.myImg.getColorModel().hasAlpha()) {
            setProfundidad(4);
            channel += 1;
        }
        for (int i = 0; i < this.myImg.getHeight(); i++) {
            for (int j = 0; j < this.myImg.getWidth(); j++) {
                posicionVector = comprobarPosicion(i, j, channel, tamaño);
                value = Byte.toUnsignedInt(copy[posicionVector]);
                value = value * (100 + brillo) / 100;
                if (value > 255) {
                    this.vector[posicionVector] = (byte) 255;
                } else if (value < 0) {
                    this.vector[posicionVector] = 0;
                } else {
                    this.vector[posicionVector] = (byte) value;
                }
            }

        }
    }


    // Metodo para cambiar a escala de grises
    public void grayScale(BufferedImage img) {
        byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
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

    // Metodos para aplicar el filtro
    public void aplicarFiltro(int repeticiones, BufferedImage imageOrigen, int tamaño) {
        Matriz matriz = new Matriz();
        byte[] vectorOrigen = ((DataBufferByte) imageOrigen.getRaster().getDataBuffer()).getData();
        byte[] aux = new byte[vectorOrigen.length];
        for (int i = 0; i < vectorOrigen.length; i++) {
            aux[i]= vectorOrigen[i];
        }
        if (repeticiones < 0) {
            repeticiones = Math.abs(repeticiones);
            for (int i = 0; i < repeticiones; i++) {
                convolucionImagen(vectorOrigen, tamaño, matriz.getMatrizSharp(), aux);
            }
        } else {
            for (int i = 0; i < repeticiones; i++) {
                convolucionImagen(vectorOrigen, tamaño, matriz.getMatrizUnfocus(), aux);
            }
        }
    }

    public void convolucionImagen(byte[] vectorOrigen, int tamaño, int[][] matrizConvolucion, byte[] aux) {

        int totalk = calcularK(matrizConvolucion);
        int convolucion;

        for (int i = 1; i < this.getMyImg().getHeight() - 1; i++) {
            for (int j = 1; j < this.getMyImg().getWidth() - 1; j++) {
                int destino = (i * this.getMyImg().getWidth() * getProfundidad()) + (j * getProfundidad());
                for (int k = 0; k < getProfundidad(); k++) {

                    convolucion = calcularConvolucion(i, j, k, tamaño, matrizConvolucion, aux);
                    convolucion /= totalk;
                    if (matrizConvolucion[0][0] != 0) {
                        if (convolucion > 255) {
                            convolucion = 255;
                        } else if (convolucion < 0) {
                            convolucion = 0;
                        }
                        this.getVector()[destino + k] = (byte) convolucion;
                        aux[destino + k] = (byte) convolucion;
                    }
                }/*
                if (matrizConvolucion[0][0] == 0) {
                    if ((aux[destino] > 255) || (aux[destino] < 0)
                            || (aux[destino + 1] > 255) || (aux[destino + 1] < 0)
                            || (aux[destino + 2] > 255) || (aux[destino + 2] < 0)) {

                    }else{
                        vectorOrigen[destino] = aux[destino];
                        vectorOrigen[destino + 1] = aux[destino + 1];
                        vectorOrigen[destino + 2] = aux[destino + 2];
                    }
                }*/
            }
        }
    }

    public int calcularConvolucion(int fila, int columna, int capa, int tamaño, int[][] matrizConvolucion, byte[] aux) {

        int convolucion = 0;
        //int destino = comprobarPosicion(fila, columna, capa, tamaño);
        int destino = (fila * this.getMyImg().getWidth() * getProfundidad()) + (columna * getProfundidad()) + capa;
        int origen;

        for (int i = 0; i < matrizConvolucion.length; i++) {
            for (int j = 0; j < matrizConvolucion[i].length; j++) {
                origen = (((fila - 1) + i) * (this.getMyImg().getWidth() * getProfundidad())) + (((columna - 1) + j) * getProfundidad()) + capa;
                int valorByte = (Byte.toUnsignedInt(aux[origen]));
                convolucion += valorByte * matrizConvolucion[i][j];
            }
        }
        return convolucion;
    }

    public int calcularK(int[][] matrizConvolucion) {
        int total = 0;
        for (int i = 0; i < matrizConvolucion.length; i++) {
            for (int j = 0; j < matrizConvolucion[i].length; j++) {
                total += matrizConvolucion[i][j];
            }
        }
        if (total == 0) total = 1;
        return total;
    }

// Metodo auxiliar para posiciones

    private int comprobarPosicion(int i, int j, int channel, int tamaño) {
        int ancho = this.getMyImg().getWidth() * tamaño / 100;
        int alto = this.getMyImg().getHeight() * tamaño / 100;
        int posicionVector = 0;
        if (isCuadrado()) {
            if (i > (this.myImg.getHeight() / 2 - alto) && i < (this.myImg.getHeight() / 2 + alto)) {
                if (j >= ((this.myImg.getWidth()) / 2) - ancho && j <= (this.myImg.getWidth() / 2) + ancho) {
                    posicionVector = (i * this.myImg.getWidth() * getProfundidad()) + (j * getProfundidad()) + channel;
                    posicionesCuadrado.add(posicionVector);
                }
            }
        } else {
            posicionVector = (i * this.myImg.getWidth() * getProfundidad()) + (j * getProfundidad()) + channel;
        }
        return posicionVector;
    }

}
