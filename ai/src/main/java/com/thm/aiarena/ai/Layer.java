package com.thm.aiarena.ai;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class Layer {
    private static final double ANOMALY_FACTOR = 0.05d;
    private int cInput;
    private int cOutput;
    private Random rnd;


    double[][] weights;

    public Layer() {
        cInput = 0;
        cOutput = 0;
        rnd = new Random();
    }
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

    public Layer(Layer parent) {
        this.rnd = new Random();
        this.cInput = parent.cInput;
        this.cOutput = parent.cOutput;
        this.weights = new double[this.cOutput][this.cInput];
        for (int i = 0; i < this.cOutput; i++) {
            for (int j = 0; j < this.cInput; j++) {
                this.weights[i][j] = parent.weights[i][j];
            }
        }
    }

    public void randomize(double factor) {
        for (int i = 0; i < this.cOutput; i++) {
            for (int j = 0; j < this.cInput; j++) {
                this.weights[i][j] += ( this.weights[i][j] * factor * (rnd.nextDouble(2) - 1));
            }
        }
    }

    public double[] forward(double[] input) {
        double[] output = new double[cOutput];
        for (int i = 0; i < weights.length; i++) {
            output[i] = 0;
            for (int j = 0; j < input.length; j++) {
                output[i] += input[j] * ( weights[i][j] + anomaly(weights[i][j]));
            }
        }
        return output;
    }

    private double anomaly(double weight) {
        double factor = 2 * Math.random() * ANOMALY_FACTOR - ANOMALY_FACTOR;
        return factor * weight;
    }

    public void save(DataOutputStream outputStream) throws IOException {

            outputStream.writeInt(this.cInput);
            outputStream.writeInt(this.cOutput);

            for (int i = 0; i < this.cOutput; i++) {
                for (int j = 0; j < this.cInput; j++) {
                    outputStream.writeDouble(weights[i][j]);
                }
            }

    }

    public void load(DataInputStream inputStream) throws IOException {

        this.cInput = inputStream.readInt();
        this.cOutput = inputStream.readInt();

        weights = new double[cOutput][cInput];

        for (int i = 0; i < cOutput; i++) {
            for (int j = 0; j < cInput; j++) {
                weights[i][j] = inputStream.readDouble();
            }
        }

    }
}
