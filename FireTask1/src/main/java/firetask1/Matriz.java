package firetask1;

public class Matriz {
    private int[][] matrizUnfocus = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    };
    private int[][] matrizSharp={
            {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
    };
    public int[][] getMatrizUnfocus() {
        return matrizUnfocus;
    }
    public int[][] getMatrizSharp() {
        return matrizSharp;
    }
}
