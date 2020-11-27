package firetask1;

import java.awt.image.BufferedImage;

import java.awt.image.DataBufferByte;
import java.util.HashMap;


public class MyImage {


    private final BufferedImage myImg;
    private boolean grisGeneral;
    private boolean grisInterior;
    private boolean cuadrado = false;
    private boolean todo = true;
    private int inicioCuadrado;
    private int finCuadrado;
    private int inicioAncho;
    private int finAncho;
    private int inicioAlto;
    private int finAlto;
    private final byte[] vector;
    private byte[] vectorCopia;
    private int profundidad = 3;
    private int redChannel;
    private int rojoInterior;
    private int greenChannel;
    private int verdeInterior;
    private int blueChannel;
    private int azulInterior;
    private int allChannels;
    private int GeneralInterior;
    private int filter;
    private int filtroInterior;
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    public MyImage(BufferedImage myImg, Boolean isGrey) {
        this.myImg = myImg;
        this.grisGeneral = isGrey;
        this.vector = ((DataBufferByte) this.myImg.getRaster().getDataBuffer()).getData();
        this.vectorCopia = new byte[this.vector.length];
    }

    public byte[] getVectorCopia() {
        return vectorCopia;
    }

    public int getFiltroInterior() {
        return filtroInterior;
    }

    public void setFiltroInterior(int filtroInterior) {
        this.filtroInterior = filtroInterior;
    }

    public int getRojoInterior() {
        return rojoInterior;
    }

    public void setRojoInterior(int rojoInterior) {
        this.rojoInterior = rojoInterior;
    }

    public int getVerdeInterior() {
        return verdeInterior;
    }

    public void setVerdeInterior(int verdeInterior) {
        this.verdeInterior = verdeInterior;
    }

    public int getAzulInterior() {
        return azulInterior;
    }

    public void setAzulInterior(int azulInterior) {
        this.azulInterior = azulInterior;
    }

    public int getGeneralInterior() {
        return GeneralInterior;
    }

    public void setGeneralInterior(int generalInterior) {
        GeneralInterior = generalInterior;
    }

    public BufferedImage getMyImg() {
        return myImg;
    }


    public boolean isCuadrado() {
        return cuadrado;
    }

    public boolean isTodo() {
        return todo;
    }

    public void setTodo(boolean todo) {
        this.todo = todo;
    }

    public void setCuadrado(boolean cuadrado) {
        this.cuadrado = cuadrado;
    }

    public int getInicioCuadrado() {
        return inicioCuadrado;
    }

    public void setInicioCuadrado(int inicioCuadrado) {
        this.inicioCuadrado = inicioCuadrado;
    }

    public int getFinCuadrado() {
        return finCuadrado;
    }

    public void setFinCuadrado(int finCuadrado) {
        this.finCuadrado = finCuadrado;
    }


    public int getInicioAncho() {
        return inicioAncho;
    }

    public void setInicioAncho(int inicioAncho) {
        this.inicioAncho = inicioAncho;
    }

    public int getFinAncho() {
        return finAncho;
    }

    public void setFinAncho(int finAncho) {
        this.finAncho = finAncho;
    }

    public int getInicioAlto() {
        return inicioAlto;
    }

    public void setInicioAlto(int inicioAlto) {
        this.inicioAlto = inicioAlto;
    }

    public int getFinAlto() {
        return finAlto;
    }

    public void setFinAlto(int finAlto) {
        this.finAlto = finAlto;
    }

    public Boolean getGrisGeneral() {
        return grisGeneral;
    }

    public void setGrisGeneral(Boolean grisGeneral) {
        this.grisGeneral = grisGeneral;
    }

    public boolean isGrisInterior() {
        return grisInterior;
    }

    public void setGrisInterior(boolean grisInterior) {
        this.grisInterior = grisInterior;
    }

    public int getRedChannel() {
        return redChannel;
    }

    public byte[] getVector() {
        return vector;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
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


    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }


    // Metodos para ajustar el brillo general y por canales
    public void cambiarBrilloGeneral(byte[] original, int brillo) {
        if (this.getMyImg().getColorModel().hasAlpha()) {
            setProfundidad(4);
        }

        for (int i = 0; i < this.getMyImg().getHeight(); i++) {
            for (int j = 0; j < this.getMyImg().getWidth(); j++) {
                for (int k = 0; k < getProfundidad(); k++) {
                    int posicionVector = (i * this.myImg.getWidth() * getProfundidad()) + (j * getProfundidad()) + k;
                    //int posicionVector = comprobarPosicion(i, j, k, tamaño);
                    if (i > getInicioAlto() && i < getFinAlto()
                            && j > getInicioAncho() && j < getFinAncho()) {
                        int value = Byte.toUnsignedInt(original[posicionVector]);
                        value = value * (100 + getGeneralInterior()) / 100;
                        value = comprobarValor(value);
                        this.getVector()[posicionVector] = (byte) value;
                    } else {
                        int value = Byte.toUnsignedInt(original[posicionVector]);
                        value = value * (100 + getAllChannels()) / 100;
                        value = comprobarValor(value);
                        this.getVector()[posicionVector] = (byte) value;
                    }

                    // copy[posicionVector] = (byte) value;
                    //comprobarCuadrado(i, j, original, this.getVector(), posicionVector, value);
                }
            }
        }
    }

    public void cambiarBrillo(byte[] copy, int brillo, int brilloInterior, int channel) {

        if (this.myImg.getColorModel().hasAlpha()) {
            setProfundidad(4);
            channel += 1;
        }
        byte[] copia = new byte[this.getVector().length];
        for (int i = 0; i < this.getVector().length; i++) {
            copia[i] = this.getVector()[i];
        }
        if (channel == 0) {
            brillo = getBlueChannel();
            brilloInterior = getAzulInterior();
        } else if (channel == 1) {
            brillo = getGreenChannel();
            brilloInterior = getVerdeInterior();
        } else {
            brillo = getRedChannel();
            brilloInterior = getRojoInterior();
        }
        for (int i = 0; i < this.myImg.getHeight(); i++) {
            for (int j = 0; j < this.myImg.getWidth(); j++) {
                int posicionVector = (i * this.myImg.getWidth() * getProfundidad()) + (j * getProfundidad()) + channel;

                if (i > getInicioAlto() && i < getFinAlto()
                        && j > getInicioAncho() && j < getFinAncho()) {
                    int value = Byte.toUnsignedInt(copia[posicionVector]);
                    value = value * (100 + brilloInterior) / 100;
                    value = comprobarValor(value);
                    this.getVector()[posicionVector] = (byte) value;
                } else {
                    int value = Byte.toUnsignedInt(copia[posicionVector]);
                    value = value * (100 + brillo) / 100;
                    value = comprobarValor(value);
                    this.getVector()[posicionVector] = (byte) value;
                }
            }
        }
    }

    // Metodo para cambiar a escala de grises
    public void cambiarGrises(BufferedImage img, int tamaño) {
        int posicionVector = 0;
        for (int i = 0; i < this.getMyImg().getHeight(); ++i) {
            for (int j = 0; j < this.getMyImg().getWidth(); ++j) {
                if (i > getInicioAlto() & i < getFinAlto() & j > getInicioAncho() & j < getFinAncho()) {
                    if (isGrisInterior()) {
                        posicionVector = i * img.getWidth() * getProfundidad() + (j * getProfundidad());
                        calcularGris(posicionVector, this.getVector());
                    }
                } else if (getGrisGeneral()) {
                    posicionVector = i * img.getWidth() * getProfundidad() + (j * getProfundidad());
                    calcularGris(posicionVector, this.getVector());
                }
            }
        }

    }

    private void calcularGris(int posicionVector, byte[] data) {
        int media;
        int valor1;
        int valor2;
        int valor3;

        valor1 = Byte.toUnsignedInt(data[posicionVector]);
        valor2 = Byte.toUnsignedInt(data[posicionVector + 1]);
        valor3 = Byte.toUnsignedInt(data[posicionVector + 2]);
        media = (valor1 + valor2 + valor3) / 3;

        media = comprobarValor(media);

        this.getVector()[posicionVector] = (byte) media;
        this.getVector()[posicionVector + 1] = (byte) media;
        this.getVector()[posicionVector + 2] = (byte) media;
    }

    // Metodos para aplicar el filtro
    public void aplicarFiltro(int repeticiones, BufferedImage imageOrigen, int tamaño) {

        Matriz matriz = new Matriz();
        byte[] vectorOrigen = ((DataBufferByte) imageOrigen.getRaster().getDataBuffer()).getData();
        byte[] copia = new byte[vectorOrigen.length];
        for (int i = 0; i < vectorOrigen.length; i++) {
            copia[i] = this.getVector()[i];
        }
        if (repeticiones < 0) {
            repeticiones = Math.abs(repeticiones);
            for (int i = 0; i < repeticiones; i++) {
                aplicarConvolucionInterior(tamaño, matriz.getMatrizSharp(), copia);
                for (int j = 0; j < vectorOrigen.length; j++) {
                    copia[j] = this.getVector()[j];
                }
            }
        } else {
            for (int i = 0; i < repeticiones; i++) {
                aplicarConvolucionInterior(tamaño, matriz.getMatrizUnfocus(), copia);
                for (int j = 0; j < vectorOrigen.length; j++) {
                    copia[j] = this.getVector()[j];
                }
            }
        }
    }

    private void aplicarConvolucionInterior(int tamaño, int[][] matrizConvolucion, byte[] aux) {

        int totalk = calcularK(matrizConvolucion);
        int convolucion;

        for (int i = 1; i < this.getMyImg().getHeight() - 1; i++) {
            for (int j = 1; j < this.getMyImg().getWidth() - 1; j++) {
                int destino;
                    for (int k = 0; k < getProfundidad(); k++) {
                        destino = (i * this.getMyImg().getWidth() * getProfundidad()) + (j * getProfundidad());
                        convolucion = calcularConvolucion(i, j, k, matrizConvolucion, aux);
                        convolucion /= totalk;
                        k = comprobarConvolucion(convolucion, matrizConvolucion, destino, k, aux);

                    }
                }
            }
        }





    private int comprobarConvolucion(int convolucion, int[][] matrizConvolucion, int destino, int k, byte[] aux) {
        if (matrizConvolucion[0][0] != 0) {
            convolucion = comprobarValor(convolucion);
            this.getVector()[destino + k] = (byte) convolucion;
        } else {
            if (convolucion > 255 || convolucion < 0) {
                this.getVector()[destino + k] = aux[destino + k];
                this.getVector()[destino + k + 1] = aux[destino + k + 1];
                this.getVector()[destino + k + 2] = aux[destino + k + 2];
                k = getProfundidad();

            } else {
                this.getVector()[destino + k] = (byte) convolucion;
            }
        }
        return k;
    }


    private int calcularConvolucion(int fila, int columna, int capa, int[][] matrizConvolucion, byte[] aux) {
        int convolucion = 0;
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

    private int calcularK(int[][] matrizConvolucion) {
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

    public void dimensionarCuadrado(int tamaño) {
        int anchoCuadrado = this.getMyImg().getWidth() * tamaño / 100;
        int altoCuadrado = this.getMyImg().getHeight() * tamaño / 100;
        setInicioAncho(getMyImg().getWidth() / 2 - anchoCuadrado);
        setFinAncho(getMyImg().getWidth() / 2 + anchoCuadrado);
        setInicioAlto(getMyImg().getHeight() / 2 - altoCuadrado);
        setFinAlto(getMyImg().getHeight() / 2 + altoCuadrado);
    }

    private int comprobarValor(int valor) {
        if (valor > 255) {
            valor = 255;
        } else if (valor < 0) {
            valor = 0;
        }
        return valor;
    }

}
