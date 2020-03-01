import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class MaxIsoscelesTriangle {

    public static void main(String[] args) throws IOException {

        try {

            // There should be only 2 program arguments:
            // the name of the input file and the name of the output file.
            if (args.length == 2) {

                String inputFileName = args[0];
                String outputFileName = args[1];

                int[] coordinatesOfMaxIsoscelesTriangle = findCoordinates(inputFileName);
                writeToFile(outputFileName, coordinatesOfMaxIsoscelesTriangle);

            } else {
                System.out.println("2 program arguments are expected.");
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static int[] findCoordinates(String inputFileName) throws IOException {

        File file = new File(inputFileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        double maxArea = -1, area;
        int[] maxCoordinates = new int[6];

        try {

            String line = reader.readLine();

            // If the line is "null", then the end of the file is reached and the program terminates.
            while (line != null) {

                final Scanner scanner = new Scanner(line);

                int[] coordinates = new int[6];
                int itemCounter = 0;

                // Writing the first six integers from a string to an array of coordinates.
                while (scanner.hasNextInt() && itemCounter < 6) {

                    coordinates[itemCounter] = scanner.nextInt();
                    itemCounter++;

                }

                // An array of coordinates is correct if it has 6 integers.
                if (itemCounter == 6) {

                    area = findArea(coordinates);

                    if (area > maxArea) {

                        maxArea = area;
                        maxCoordinates = Arrays.copyOf(coordinates, coordinates.length);

                    }

                }

                line = reader.readLine();

            }


        } finally {
            reader.close();
        }

        return maxCoordinates;

    }

    public static double findArea(int[] coordinates) {

        double[] sideAndBase = findSideAndBaseOfIsoscelesTriangle(coordinates);
        double side = sideAndBase[0];
        double base = sideAndBase[1];
        double area = -1.0;

        // Check if the coordinates of the points are the coordinates of the vertices of the triangle.
        if (side != 0 && base != 0) {
            area = 0.25 * base * Math.sqrt(4 * side * side - base * base);
        }

        return area;

    }

    public static double[] findSideAndBaseOfIsoscelesTriangle(int[] coordinates)  {

        int x1 = coordinates[0];
        int y1 = coordinates[1];
        int x2 = coordinates[2];
        int y2 = coordinates[3];
        int x3 = coordinates[4];
        int y3 = coordinates[5];

        // Search for the lengths of the sides of a triangle.
        double side12 = findDistanceBetweenPoints(x1, y1, x2, y2);
        double side23 = findDistanceBetweenPoints(x2, y2, x3, y3);
        double side31 = findDistanceBetweenPoints(x3, y3, x1, y1);

        // “side” is the length of the equal sides of an isosceles triangle.
        // “base” is the length of the third side of an isosceles triangle.
        double side = 0.0, base = 0.0;

        if (side12 == side23) {

            side = side12;
            base = side31;

        } else if (side23 == side31) {

            side = side23;
            base = side12;

        } else if (side31 == side12) {

            side = side31;
            base = side23;

        }

        double[] sideAndBase = {side, base};

        return sideAndBase;

    }

    public static double findDistanceBetweenPoints(int x1, int y1, int x2, int y2) {

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

    }

    public static void writeToFile(String outputFileName, int[] array) {

        try(FileWriter writer = new FileWriter(outputFileName, false)) {

            for (int i = 0; i < array.length; i++) {

                // By default, the coordinate array is filled with zeros.
                // If no triangle of the necessary parameters was found, then the array has not changed
                // and the output file remains empty.
                if (array[i] != 0) {

                    for (int ar : array) {
                        writer.write(ar + " ");
                    }

                    break;

                }

            }

            writer.flush();

        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

}
