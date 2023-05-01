package com.thm.aiarena.ai;

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

}
