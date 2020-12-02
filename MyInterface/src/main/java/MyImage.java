import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class MyImage {
    private final BufferedImage myImg;
    private boolean generalGray;
    private boolean grayInside;
    private int widthStart;
    private int widthEnd;
    private int heightStart;
    private int heightEnd;
    private byte[] vector;
    private int deep;
    private int redChannelValue;
    private int redInside;
    private int greenChannelValue;
    private int greenInside;
    private int blueChannelValue;
    private int blueInside;
    private int generalBrightness;
    private int brightInside;
    private int filter;
    private int filterInside;
    private int squareSize;

    // Constructor

    public MyImage(BufferedImage myImg) {
        this.myImg = myImg;
        this.deep = 3;
        this.vector = ((DataBufferByte) this.myImg.getRaster().getDataBuffer()).getData();
    }

    //-----------------------------------GETTER/SETTER-----------------------------------

    public BufferedImage myImg() {
        return myImg;
    }

    public Boolean getGeneralGray() {
        return generalGray;
    }

    public void setGeneralGray(Boolean generalGray) {
        this.generalGray = generalGray;
    }

    public boolean isGrayInside() {
        return grayInside;
    }

    public void setGrayInside(boolean grayInside) {
        this.grayInside = grayInside;
    }

    public byte[] getVector() {
        return vector;
    }

    public int getRedChannelValue() {
        return redChannelValue;
    }

    public void setRedChannelValue(int redChannelValue) {
        this.redChannelValue = redChannelValue;
    }

    public int getRedInside() {
        return redInside;
    }

    public void setRedInside(int redInside) {
        this.redInside = redInside;
    }

    public int getGreenChannelValue() {
        return greenChannelValue;
    }

    public void setGreenChannelValue(int greenChannelValue) {
        this.greenChannelValue = greenChannelValue;
    }

    public int getGreenInside() {
        return greenInside;
    }

    public void setGreenInside(int greenInside) {
        this.greenInside = greenInside;
    }

    public int getBlueChannelValue() {
        return blueChannelValue;
    }

    public void setBlueChannelValue(int blueChannelValue) {
        this.blueChannelValue = blueChannelValue;
    }

    public int getBlueInside() {
        return blueInside;
    }

    public void setBlueInside(int blueInside) {
        this.blueInside = blueInside;
    }

    public int getGeneralBrightness() {
        return generalBrightness;
    }

    public void setGeneralBrightness(int generalBrightness) {
        this.generalBrightness = generalBrightness;
    }

    public int getBrightInside() {
        return brightInside;
    }

    public void setBrightInside(int brightInside) {
        this.brightInside = brightInside;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public int getFilterInside() {
        return filterInside;
    }

    public void setFilterInside(int filterInside) {
        this.filterInside = filterInside;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public void setSquareSize(int squareSize) {
        this.squareSize = squareSize;
    }

// -------------------------------------------METODOS DE LA CLASE -------------------------------

    /**
     * Metodo que toma una un array de bytes como referencia para modificar el brillo general del vector de la imagen actual
     * @param original Array de bytes que toma como valor de referencia
     */

    public void changeGeneralBrightness(byte[] original) {

        if (this.myImg.getColorModel().hasAlpha()) {
            this.deep = 4;
        }
        int intitialK = 0;
        if (this.deep == 4 ) {
            intitialK = 1;
        }
        for (int i = 0; i < this.myImg.getHeight(); i++) {
            for (int j = 0; j < this.myImg.getWidth(); j++) {
                for (int k = intitialK; k < this.deep; k++) {

                    int vectorPosition = (i * this.myImg.getWidth() * this.deep) + (j * this.deep) + k;
                    int value = Byte.toUnsignedInt(original[vectorPosition]);
                    if (i > this.heightStart && i < this.heightEnd
                            && j > this.widthStart && j < this.widthEnd) {

                        value = value * (100 + this.brightInside) / 100;
                    } else {
                        value = value * (100 + this.generalBrightness) / 100;
                    }
                    value = checkValue(value);
                    this.vector[vectorPosition] = (byte) value;
                }
            }
        }
    }

    /**
     * Metodo que modifica el brillo por canales, recibiendo el canal sobre el que trabajar
     * @param channel Candal RGB que será actualizado
     */

    public void changeBrightnessChannel(int channel) {

        int brightness;
        int brightnessInside;
        byte[] copy = new byte[this.vector.length];
        System.arraycopy(this.vector,0,copy,0,this.vector.length);

        if (channel == 0) {
            brightness = getBlueChannelValue();
            brightnessInside = getBlueInside();
        } else if (channel == 1) {
            brightness = getGreenChannelValue();
            brightnessInside = getGreenInside();
        } else {
            brightness = getRedChannelValue();
            brightnessInside = getRedInside();
        }
        if (this.deep == 4) {
            channel += 1;
        }
        for (int i = 0; i < this.myImg.getHeight(); i++) {
            for (int j = 0; j < this.myImg.getWidth(); j++) {
                int vectorPosition = (i * this.myImg.getWidth() * this.deep) + (j * this.deep) + channel;

                int value = Byte.toUnsignedInt(copy[vectorPosition]);
                if (i > this.heightStart && i < this.heightEnd
                        && j > this.widthStart && j < this.widthEnd) {

                    value = value * (100 + brightnessInside) / 100;
                } else {
                    value = value * (100 + brightness) / 100;
                }
                value = checkValue(value);
                this.vector[vectorPosition] = (byte) value;
            }
        }
    }

    /**
     * Metodo que recorre un vector y por cada pixel llama a otro metodo para calcular su valor en escala de grises.
     */

    public void grayChange() {
        int positionVector;
        for (int i = 0; i < this.myImg.getHeight(); ++i) {
            for (int j = 0; j < this.myImg.getWidth(); ++j) {

                if (i > this.heightStart & i < this.heightEnd
                        & j > this.widthStart & j < this.widthEnd) {

                    if (this.grayInside) {
                        positionVector = i * this.myImg.getWidth() * this.deep + (j * this.deep);
                        grayCalculate(positionVector);
                    }
                } else if (this.generalGray) {
                    positionVector = i * this.myImg.getWidth() * this.deep + (j * this.deep);
                    grayCalculate(positionVector);
                }
            }
        }

    }

    /**
     * Metodo que recibe una posicion de un array para calcular su valor a escala de grises
     * @param positionVector Posición del byte dentro del vector de la imagen
     */

    private void grayCalculate(int positionVector) {
        if (this.deep == 4) {
            positionVector += 1;
        }
        int value1 = Byte.toUnsignedInt(this.vector[positionVector]);
        int value2 = Byte.toUnsignedInt(this.vector[positionVector + 1]);
        int value3 = Byte.toUnsignedInt(this.vector[positionVector + 2]);
        int avg = (value1 + value2 + value3) / 3;
        avg = checkValue(avg);
        this.vector[positionVector] = (byte) avg;
        this.vector[positionVector + 1] = (byte) avg;
        this.vector[positionVector + 2] = (byte) avg;
    }

    /**
     * Método para aplicar el filtro, dependiendo de los valores almacenados en filter y filterInside calculará la convolución
     * del vecto con un kernel u otro.
     */

    public void applyFilter() {

        KernelConvolution kernel = new KernelConvolution();
        byte[] copy = new byte[this.vector.length];
        System.arraycopy(this.vector, 0, copy, 0, this.vector.length);

        if (this.filter < 0) {
            int repetitionsExterior = Math.abs(this.filter);
            for (int i = 0; i < repetitionsExterior; i++) {
                applyConvolution(kernel.getSharp(), copy);
                System.arraycopy(this.vector, 0, copy, 0, this.vector.length);
            }
        }
        if (this.filterInside < 0) {
            int repetitionsInterior = Math.abs(this.filterInside);
            for (int i = 0; i < repetitionsInterior; i++) {
                applyConvolutionInside(kernel.getSharp(), copy);
                System.arraycopy(this.vector, 0, copy, 0, this.vector.length);
            }

        }
        if (this.filter > 0) {
            for (int i = 0; i < this.filter; i++) {
                applyConvolution(kernel.getUnfocus(), copy);
                System.arraycopy(this.vector, 0, copy, 0, this.vector.length);
            }
        }
        if (this.filterInside > 0)
            for (int i = 0; i < this.filterInside; i++) {
                applyConvolutionInside(kernel.getUnfocus(), copy);
                System.arraycopy(this.vector, 0, copy, 0, this.vector.length);
            }
    }

    /**
     * Metodo para realizar la convolucion de la zona del recuadro de la imagen.
     * @param kernel kernel de convolución con el que se realizará el calculo
     * @param copy copia del vector de la imagen antes de empezar a realizar los cálculos de esta convolución.
     */
    private void applyConvolutionInside(int[][] kernel, byte[] copy) {

        int totalK = calculateK(kernel);
        int convolution;
        int intialK = 0;
        if (this.deep == 4) {
            intialK = 1;
        }
        for (int i = 1; i < this.myImg.getHeight() - 1; i++) {
            for (int j = 1; j < this.myImg.getWidth() - 1; j++) {
                int destino;
                for (int k = intialK; k < this.deep; k++) {

                    destino = (i * this.myImg.getWidth() * this.deep) + (j * this.deep);
                    if (i > this.heightStart & i < this.heightEnd
                            & j > this.widthStart & j < this.widthEnd) {
                        convolution = calculateConvolution(i, j, k, kernel, copy);
                        convolution /= totalK;
                        k = checkConvolution(convolution, kernel, destino, k, copy);
                    }
                }
            }
        }
    }

    /**
     * Metodo para calcular la convolucion de la zona exterior al recuadro de la imagen.
     * @param kernel kernel de convolución con el que se realizará el calculo
     * @param copy copia del vector de la imagen antes de empezar a realizar los cálculos de esta convolución.
     */
    private void applyConvolution(int[][] kernel, byte[] copy) {

        int total = calculateK(kernel);
        int convolucion;
        int intialK = 0;
        if (this.deep == 4) {
            intialK = 1;
        }

        for (int i = 1; i < this.myImg.getHeight() - 1; i++) {
            for (int j = 1; j < this.myImg.getWidth() - 1; j++) {
                int destino;
                destino = (i * this.myImg.getWidth() * this.deep) + (j * this.deep);
                for (int k = intialK; k < this.deep; k++) {

                    if (i > this.heightStart && i < this.heightEnd
                            && j > this.widthStart && j < this.widthEnd) {
                        this.vector[destino + k] = copy[destino + k];
                    } else {
                        convolucion = calculateConvolution(i, j, k, kernel, copy);
                        convolucion /= total;
                        k = checkConvolution(convolucion, kernel, destino, k, copy);

                    }
                }
            }
        }
    }

    /**
     * Metodo que calcula la convolucion de una imagen pasando previamente los valores que corresponden a la "fila" de pixels,a "columna" de pixels, el canal sobre
     * el que se trabajará, el kernel y la copia del vector en el caso que sea necesario rectificar algún valor.
     * @param line corresponde al valor de i del metodo applyConvolution/applyCovolutionInside.
     * @param column corresponde al valor de j del metodo applyConvolution/applyCovolutionInside.
     * @param channel corresponde al valor de k del metodo applyConvolution/applyCovolutionInside.
     * @param kernel kernel de convolucion que se usa de base para las operaciones.
     * @param copy copia del vector original para rectificar valores.
     * @return Devuelve el valor de la convolucion de un unico canal de un pixel.
     */
    private int calculateConvolution(int line, int column, int channel, int[][] kernel, byte[] copy) {
        int convolution = 0;
        int origen;

        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                origen = (((line - 1) + i) * (this.myImg.getWidth() * this.deep)) + (((column - 1) + j) * this.deep) + channel;
                int valorByte = (Byte.toUnsignedInt(copy[origen]));
                convolution += valorByte * kernel[i][j];
            }
        }
        return convolution;
    }

    /**
     * Calcula del valor del kernel para la posterior division de la convolución.
     * @param kernel Kernel del cual extraemos los valores para calcular el total.
     * @return Devuelve la suma de todos los valores del interior del kernel, en caso de ser negativo lo rectifica y lo pone a 0.
     */
    private int calculateK(int[][] kernel) {
        int total = 0;
        for (int[] values : kernel) {
            for (int value : values) {
                total += value;
            }
        }
        if (total <= 0) {
            total = 1;
        }
        return total;
    }

    /**
     * Método que comprueba si la convolución está bien aplicada independientemente del filtro con el que estamos
     * trabajando.
     * @param convolucion Valor de la convolucion que queremos comprobar.
     * @param kernel Kernel usado para comprobar cual kernel se ha aplicado.
     * @param destino Posicion del array de bytes en la que se guarda el valor de la convolución.
     * @param k Canal de la imagen que se está comprobando.
     * @param copy Copia de la cual extraeremos los valores en que caso de ser necesario rectificarlos.
     * @return
     */

    private int checkConvolution(int convolucion, int[][] kernel, int destino, int k, byte[] copy) {
        if (kernel[0][0] != 0) {
            this.vector[destino + k] = (byte) convolucion;
        } else {
            if (convolucion > 255 || convolucion < 0) {
                this.vector[destino + k] = copy[destino + k];
                this.vector[destino + k + 1] = copy[destino + k + 1];
                this.vector[destino + k + 2] = copy[destino + k + 2];
                k = this.deep;

            } else {
                this.vector[destino + k] = (byte) convolucion;
            }
        }
        return k;
    }

    /**
     * Metodo auxiliares para dimensionar la zona interna y comprobar que los valores no superen los limites
      * @param size Valor entero del cual se calcula el tamaño del cuadrado
     */
    public void sizeSquare(int size) {
        int widthSquare = this.myImg.getWidth() * size / 100;
        int heightSquare = this.myImg.getHeight() * size / 100;
        this.widthStart = this.myImg.getWidth() / 2 - widthSquare;
        this.widthEnd = this.myImg.getWidth() / 2 + widthSquare;
        this.heightStart = this.myImg.getHeight() / 2 - heightSquare;
        this.heightEnd = this.myImg.getHeight() / 2 + heightSquare;
    }

    /**
     * Metodo para comprobar que durante la modificación de la imagen no se superan el valor máximo ni el valor mínimo.
     * @param value Valor que se quiere comprobar que no pase de los limtes establecidos
     * @return Devuelve el valor rectificado
     */

    private int checkValue(int value) {
        if (value > 255) {
            value = 255;
        } else if (value < 0) {
            value = 0;
        }
        return value;
    }

}
