package convolucion3d;

import java.util.Enumeration;
import java.util.Vector;

public class Convolucion3d {

    public static void main(String[] args) {

        int[][][] matrizOrigen = new int[4][5][3];
        int[][][] matrizDestino = new int[4][5][3];
        final int[][] matrizConvolucion = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
        int[] vectorOrigen = null;
        int[] vectorDestino = null;

        matrizOrigen = rellenarMatriz(matrizOrigen);

        mostrarDimensiones(matrizOrigen);

        vectorOrigen = rellenarVector(matrizOrigen);
        vectorDestino = new int[vectorOrigen.length];

        for (int i = 0; i < matrizDestino.length; i++) {

            for (int j = 0; j < matrizDestino[i].length; j++) {

                if ((i == 0) || (i == (matrizDestino.length - 1)) || (j == 0) || (j == (matrizDestino[i].length - 1))) {

                   for (int k = 0; k < matrizDestino[i][j].length; k++) {

                        matrizDestino[i][j][k] = 0;
                    }

                } else {
                    realizarConvolucion(matrizOrigen, matrizDestino, matrizConvolucion, i, j, vectorOrigen, vectorDestino);

                    vectorDestino = realizarConvolucionVector(vectorOrigen, vectorDestino, matrizOrigen, matrizConvolucion, i, j);

                }
            }
        }

        mostrarResultado(matrizDestino, vectorDestino);

    }

    public static void realizarConvolucion(int[][][] matrizOrigen, int[][][] matrizDestino, int[][] matrizConvolucion, int i, int j, int[] vectorOrigen, int[] vectorDestino) {

        int sumaConvolucion = 0;

        
        for (int k = 0; k < matrizOrigen[i][j].length; k++) {

            for (int l = 0; l < matrizConvolucion.length; l++) {

                for (int m = 0; m < matrizConvolucion[l].length; m++) {
                    matrizDestino[i][j][k] += matrizOrigen[i - 1 + l][j - 1 + m][k] * matrizConvolucion[l][m];
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

    public static void mostrarResultado(int[][][] matrizDestino, int[] vectorDestino) {
        System.out.println("");
        for (int i = 0; i < matrizDestino.length; i++) {
            for (int j = 0; j < matrizDestino[i].length; j++) {
                for (int k = 0; k < matrizDestino[i][j].length; k++) {

                    System.out.print(matrizDestino[i][j][k] + "   ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        for (int i = 0; i < vectorDestino.length; i++) {
            System.out.print(vectorDestino[i] + " ");
        }
        System.out.println("");
    }

    public static int[] rellenarVector(int[][][] matrizOrigen) {

        int longitudVector = (matrizOrigen.length) * (matrizOrigen[0].length) * matrizOrigen[0][0].length;
        int[] vector = new int[longitudVector];

        for (int i = 0; i < vector.length; i++) {

            for (int j = 0; j < matrizOrigen.length; j++) {

                for (int k = 0; k < matrizOrigen[j].length; k++) {

                    for (int l = 0; l < matrizOrigen[j][k].length; l++) {

                        vector[i] = matrizOrigen[j][k][l];
                        i++;
                    }
                }

            }

        }
        System.out.println("");
        for (int i = 0; i < vector.length; i++) {

            System.out.print(vector[i] + " ");
        }
        System.out.println("");

        return vector;

    }

    public static int[] realizarConvolucionVector(int[] vectorOrigen, int[] vectorDestino, int[][][] matrizOrigen, int[][] matrizConvolucion, int i, int j) {

        int sumaConvolucion = 0;

        for (int k = 0; k < matrizOrigen[i][j].length; k++) {

            for (int l = 0; l < matrizConvolucion.length; l++) {

                for (int m = 0; m < matrizConvolucion[l].length; m++) {
                    
                    int posicionVectorDestino = (matrizOrigen[0][0].length * matrizOrigen[0].length * i) + (matrizOrigen[0][0].length * j) + k;

                    int posicionVectorOrigen = (matrizOrigen[0][0].length * matrizOrigen[0].length * (i - 1 + l)) + (matrizOrigen[0][0].length * (j - 1 + m)) + k;

                    vectorDestino[posicionVectorDestino] += vectorOrigen[posicionVectorOrigen] * matrizConvolucion[l][m];
                }

            }
        }
        return vectorDestino;

    }
}
