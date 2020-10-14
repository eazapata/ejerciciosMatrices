package pruebaconvolucion;

public class PruebaConvolucion {

    public static void main(String[] args) {

        int[][] matrizOrigen = new int[4][5];
        int[][] matrizDestino = new int[4][5];
        final int[][] matrizConvolucion = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};

        for (int i = 0; i < matrizOrigen.length; i++) {

            for (int j = 0; j < matrizOrigen[i].length; j++) {
                matrizOrigen[i][j] = (int) (Math.random() * 100);
                System.out.print(matrizOrigen[i][j] + " ");
            }
            System.out.println("");

        }
        for (int i = 0; i < matrizDestino.length; i++) {

            for (int j = 0; j < matrizDestino[i].length; j++) {
                
                if ((i == 0) || (i == (matrizDestino.length - 1)) || (j == 0) || (j == (matrizDestino[i].length - 1))) {
                    matrizDestino[i][j] = 0;

                } else {
                    int sumaConvolucion = 0;
                    for (int k = 0; k < matrizConvolucion.length; k++) {
                        
                        for (int l = 0; l < matrizConvolucion[k].length; l++) {
                            
                            int origen = matrizOrigen[(i +k) - 1][(j +l)- 1];
                            int convolucion = matrizConvolucion[k][l];
                            matrizDestino[i][j] +=  origen * convolucion;
                            sumaConvolucion += convolucion;
                        }
                    }
                    if(sumaConvolucion <= 0){
                        sumaConvolucion = 1;
                    }
                    matrizDestino[i][j] = matrizDestino[i][j]/sumaConvolucion; 
                }

            }

        }
        System.out.println("");
        for (int i = 0; i < matrizDestino.length; i++) {
            
            for (int j = 0; j < matrizDestino[i].length; j++) {
                System.out.print(matrizDestino[i][j] + "   ");
            }
            System.out.println("");
        }

    }

}
