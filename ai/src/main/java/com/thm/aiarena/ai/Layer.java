package com.thm.aiarena.ai;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Layer {
    private final int cInput;
    private final int cOutput;
    private final Random rnd;

    double[][] weights;

    public Layer(int cInput, int cOutput, Random rnd) {
        this.rnd = rnd;
        this.cInput = cInput;
        this.cOutput = cOutput;
        weights = new double[this.cOutput][this.cInput];
        // Initialize weights with random values between -1 and 1
        for (int i = 0; i < this.cOutput; i++) {
            for (int j = 0; j < this.cInput; j++) {
                weights[i][j] = rnd.nextDouble() * 2 - 1;
            }
        }
    }

    public double[] forward(double[] input) {
        double[] output = new double[cOutput];
        for (int i = 0; i < weights.length; i++) {
            output[i] = 0;
            for (int j = 0; j < input.length; j++) {
                output[i] += input[j] * weights[i][j];
            }
        }
        return output;
    }

    public void save(DataOutputStream outputStream) throws IOException {

            int rows = weights.length;
            int cols = weights[0].length;

            outputStream.writeInt(rows);
            outputStream.writeInt(cols);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    outputStream.writeDouble(weights[i][j]);
                }
            }

    }

    public void load(DataInputStream inputStream) throws IOException {

        int rows = inputStream.readInt();
        int cols = inputStream.readInt();

        weights = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                weights[i][j] = inputStream.readDouble();
            }
        }

    }
}
