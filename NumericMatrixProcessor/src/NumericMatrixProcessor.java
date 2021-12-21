import java.util.Scanner;

public class NumericMatrixProcessor {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            printOptions();
            int input = scanner.nextInt();
            if (input == 0) break;
            else if (input == 1) {
                double[][] temp = addMatrices();
                if (temp != null) printMatrix(temp);
            } else if (input == 2) {
                printMatrix(multiplyMatrixByConstant());
            } else if (input == 3) {
                double[][] temp = multiplyMatrices();
                if (temp != null) printMatrix(temp);
            } else if (input == 4) {
                printMatrix(transposeMatrix());
            } else if (input == 5) {
                calculateDeterminant();
            } else if (input == 6) {
                printMatrix(inverseMatrix());
            }
        }
    }

    public static void printOptions() {
        System.out.println("""
                1. Add matrices
                2. Multiply matrix by a constant
                3. Multiply matrices
                4. Transpose matrix
                5. Calculate a determinant
                6. Inverse matrix
                0. Exit
                Your choice:""");
    }

    public static double[][] addMatrices() {
        System.out.println("Enter size of first matrix:");
        double[][] firstMatrix = new double[scanner.nextInt()][scanner.nextInt()];
        System.out.println("Enter first matrix:");
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[0].length; j++) {
                firstMatrix[i][j] = scanner.nextDouble();
            }
        }
        System.out.println("Enter size of second matrix:");
        double[][] secondMatrix = new double[scanner.nextInt()][scanner.nextInt()];
        if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
            System.out.println("The operation cannot be performed.");
            return null;
        }
        System.out.println("Enter second matrix:");
        for (int i = 0; i < secondMatrix.length; i++) {
            for (int j = 0; j < secondMatrix[0].length; j++) {
                secondMatrix[i][j] = scanner.nextDouble();
            }
        }
        double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
            }
        }
        return result;
    }

    public static double[][] multiplyMatrixByConstant() {
        System.out.println("Enter size of matrix:");
        double[][] matrix = new double[scanner.nextInt()][scanner.nextInt()];
        System.out.println("Enter matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        int constant = scanner.nextInt();
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = matrix[i][j] * constant;
            }
        }
        return result;
    }

    public static double[][] multiplyMatrices() {
        System.out.println("Enter size of first matrix:");
        double[][] firstMatrix = new double[scanner.nextInt()][scanner.nextInt()];
        System.out.println("Enter first matrix:");
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[0].length; j++) {
                firstMatrix[i][j] = scanner.nextDouble();
            }
        }
        System.out.println("Enter size of second matrix:");
        double[][] secondMatrix = new double[scanner.nextInt()][scanner.nextInt()];
        if (firstMatrix[0].length != secondMatrix.length) {
            System.out.println("The operation cannot be performed.");
            return null;
        }
        System.out.println("Enter second matrix:");
        for (int i = 0; i < secondMatrix.length; i++) {
            for (int j = 0; j < secondMatrix[0].length; j++) {
                secondMatrix[i][j] = scanner.nextDouble();
            }
        }
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < secondMatrix.length; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] transposeMatrix() {
        System.out.println("""
                1. Main diagonal
                2. Side diagonal
                3. Vertical line
                4. Horizontal line
                Your choice:""");
        int input = scanner.nextInt();
        System.out.println("Enter the size of the matrix");
        double[][] matrix = new double[scanner.nextInt()][scanner.nextInt()];
        System.out.println("Enter matrix values");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        double[][] result;
        switch (input) {
            case 1 -> {
                result = new double[matrix[0].length][matrix.length];
                for (int i = 0; i < result.length; i++) {
                    for (int j = 0; j < result[0].length; j++) {
                        result[i][j] = matrix[j][i];
                    }
                }
                return result;
            }
            case 2 -> {
                result = new double[matrix[0].length][matrix.length];
                for (int i = 0; i < result.length; i++) {
                    for (int j = 0; j < result[0].length; j++) {
                        result[i][j] = matrix[matrix[0].length - 1 - j][matrix.length - 1 - i];
                    }
                }
                return result;
            }
            case 3 -> {
                result = new double[matrix.length][matrix[0].length];
                for (int i = 0; i < result.length; i++) {
                    for (int j = 0; j < result[0].length; j++) {
                        result[i][j] = matrix[i][matrix[0].length - 1 - j];
                    }
                }
                return result;
            }
            case 4 -> {
                result = new double[matrix.length][matrix[0].length];
                for (int i = 0; i < result.length; i++) {
                    System.arraycopy(matrix[matrix.length - 1 - i], 0, result[i], 0, result[0].length);
                }
                return result;
            }
        }
        return null;
    }

    public static void calculateDeterminant() {
        System.out.println("Enter matrix size:");
        double[][] matrix = new double[scanner.nextInt()][scanner.nextInt()];
        System.out.println("Enter matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        System.out.println(calcDet(matrix));
    }

    public static double calcDet(double[][] matrix) {
        if  (matrix.length == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        double det = 0;
        for (int i = 0; i < matrix.length; i++) {
            det += matrix[0][i] * Math.pow(-1, i) * calcDet(minorMatrix(matrix, 0, i));
        }
        return det;
    }

    public static double[][] minorMatrix(double[][] matrix, int y, int x) {
        double[][] result = new double[matrix.length - 1][matrix.length - 1];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                if (i < y && j < x) result[i][j] = matrix[i][j];
                else if (i < y) result[i][j] = matrix[i][j+1];
                else if (j < x) result[i][j] = matrix[i+1][j];
                else result[i][j] = matrix[i+1][j+1];
            }
        }
        return result;
    }

    public static double[][] inverseMatrix() {
        System.out.println("Enter matrix size:");
        double[][] matrix = new double[scanner.nextInt()][scanner.nextInt()];
        System.out.println("Enter matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        double[][] cofactorMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                cofactorMatrix[i][j] = Math.pow(-1, i + j) * calcDet(minorMatrix(matrix, i, j));
            }
        }
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = cofactorMatrix[j][i];
            }
        }
        double temp = 1 / calcDet(matrix);
        double[][] out = new double[matrix.length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                out[i][j] = result[i][j] * temp;
            }
        }
        return out;
    }

    public static void printMatrix(double[][] matrix) {
        System.out.println("The result is:");
        for (double[] doubles : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(doubles[j] + " ");
            }
            System.out.println();
        }
    }
}
