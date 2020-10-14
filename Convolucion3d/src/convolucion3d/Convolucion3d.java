package convolucion3d;

public class Convolucion3d {

    public static void main(String[] args) {

        int[][][] matrizOrigen = new int[4][5][3];
        int[][][] matrizDestino = new int[4][5][3];
        final int[][] matrizConvolucion = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};

        matrizOrigen = rellenarMatriz(matrizOrigen);
        mostrarDimensiones(matrizOrigen);

        for (int i = 0; i < matrizDestino.length; i++) {

            for (int j = 0; j < matrizDestino[i].length; j++) {

                if ((i == 0) || (i == (matrizDestino.length - 1)) || (j == 0) || (j == (matrizDestino[i].length - 1))) {

                    for (int k = 0; k < matrizDestino[i][j].length; k++) {

                        matrizDestino[i][j][k] = 0;
                    }

                } else {
                    realizarConvolucion(matrizOrigen, matrizDestino, matrizConvolucion, i, j);
                }
            }
        }
        mostrarResultado(matrizDestino);

    }

    public static void realizarConvolucion(int[][][] matrizOrigen, int[][][] matrizDestino, int[][] matrizConvolucion, int i, int j) {

        int sumaConvolucion = 0;
        for (int l = 0; l < matrizOrigen[i][j].length; l++) {

            for (int m = 0; m < matrizConvolucion.length; m++) {

                for (int n = 0; n < matrizConvolucion[i].length; n++) {

                    int origen = matrizOrigen[(i + m) - 1][(j + n) - 1][l];
                    int convolucion = matrizConvolucion[m][n];
                    matrizDestino[i][j][l] += origen * convolucion;
                    sumaConvolucion += convolucion;

                }
            }

            if (sumaConvolucion <= 0) {
                sumaConvolucion = 1;
            }

        }
    }

    public static int[][][] rellenarMatriz(int[][][] matrizOrigen) {

        for (int i = 0; i < matrizOrigen.length; i++) {

            for (int j = 0; j < matrizOrigen[i].length; j++) {

                for (int k = 0; k < matrizOrigen[i][j].length; k++) {

                    matrizOrigen[i][j][k] = (int) (Math.random() * 100);

                }
            }
            System.out.println("");

        }
        return matrizOrigen;
    }

    public static void mostrarDimensiones(int[][][] matrizOrigen) {
        for (int i = 0; i < matrizOrigen.length; i++) {
            for (int j = 0; j < matrizOrigen[i].length; j++) {

                System.out.print(matrizOrigen[i][j][0] + " ");
            }
            System.out.println("");

        }

        System.out.println("");

        for (int i = 0; i < matrizOrigen.length; i++) {
            for (int j = 0; j < matrizOrigen[i].length; j++) {

                System.out.print(matrizOrigen[i][j][1] + " ");
            }
            System.out.println("");

        }

        System.out.println("");
        for (int i = 0; i < matrizOrigen.length; i++) {
            for (int j = 0; j < matrizOrigen[i].length; j++) {

                System.out.print(matrizOrigen[i][j][2] + " ");
            }
            System.out.println("");

        }
    }

    public static void mostrarResultado(int[][][] matrizDestino) {
        System.out.println("");
        for (int i = 0; i < matrizDestino.length; i++) {
            for (int j = 0; j < matrizDestino[i].length; j++) {
                for (int k = 0; k < matrizDestino[i][j].length; k++) {

                    System.out.print(matrizDestino[i][j][k] + "   ");
                }
            }
            System.out.println("");
        }
    }
}
