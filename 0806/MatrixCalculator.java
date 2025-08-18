

public class MatrixCalculator {

    public static void main(String[] args) {
        int[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] matrixB = {
            {7, 8, 9},
            {10, 11, 12}
        };

        int[][] matrixC = {
            {1, 2},
            {3, 4},
            {5, 6}
        };

        System.out.println("=== 矩陣 A ===");
        printMatrix(matrixA);

        System.out.println("=== 矩陣 B ===");
        printMatrix(matrixB);

        System.out.println("=== A + B ===");
        printMatrix(addMatrices(matrixA, matrixB));

        System.out.println("=== A x C ===");
        printMatrix(multiplyMatrices(matrixA, matrixC));

        System.out.println("=== A 的轉置 ===");
        printMatrix(transposeMatrix(matrixA));

        System.out.println("=== A 的最大值與最小值 ===");
        int[] minMax = findMinMax(matrixA);
        System.out.println("最大值: " + minMax[1]);
        System.out.println("最小值: " + minMax[0]);
    }

    // 矩陣加法
    public static int[][] addMatrices(int[][] a, int[][] b) {
        int rows = a.length;
        int cols = a[0].length;
        int[][] result = new int[rows][cols];

        if (rows != b.length || cols != b[0].length) {
            throw new IllegalArgumentException("矩陣維度不相同，無法相加。");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }

        return result;
    }

    // 矩陣乘法
    public static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int rowsB = b.length;
        int colsB = b[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("矩陣不可相乘，維度不符合。");
        }

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    // 矩陣轉置
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }

        return transposed;
    }

    // 找出矩陣中的最大值與最小值
    public static int[] findMinMax(int[][] matrix) {
        int min = matrix[0][0];
        int max = matrix[0][0];

        for (int[] row : matrix) {
            for (int value : row) {
                if (value < min) min = value;
                if (value > max) max = value;
            }
        }

        return new int[]{min, max};
    }

    // 輔助方法：印出矩陣
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.printf("%4d", value);
            }
            System.out.println();
        }
        System.out.println();
    }
}