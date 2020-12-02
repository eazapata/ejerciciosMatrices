public class KernelConvolution {
    private final int[][] unfocus = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    };
    private final int[][] sharp ={
            {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
    };

    public int[][] getUnfocus() {
        return unfocus;
    }

    public int[][] getSharp() {
        return sharp;
    }
}
