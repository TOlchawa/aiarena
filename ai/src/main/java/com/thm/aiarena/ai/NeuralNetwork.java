package com.thm.aiarena.ai;

import java.util.Random;
import java.util.stream.IntStream;

public class NeuralNetwork {

    private final Layer[] layers;

    public NeuralNetwork(Random neuralNetworkRandom) {
        layers = new Layer[4];
        layers[0] = new Layer(16, 32, neuralNetworkRandom);
        layers[1] = new Layer(32, 32, neuralNetworkRandom);
        layers[2] = new Layer(32, 32, neuralNetworkRandom);
        layers[3] = new Layer(32, 16, neuralNetworkRandom);
    }

    public int think(int input) {
        double[] in = new double[16];
        for (int i=0; i<16; i++) {
            in[i] =  extractBit(input, i, 1);
        }
        double[] buf = in;

        for (int i=0; i<layers.length; i++) {
            buf = layers[i].forward(buf);
            buf = normalize(buf);
        }

        return encode(buf);
    }

    private double[] normalize(double[] buf) {
        double[] result = new double[buf.length];
        for( int i=0; i<16; i++) {
            result[i] = buf[i] > 0.0d ? 1.0d : 0.0d;
        }
        return result;
    }

    int extractBit(int input, int startPosition, int numBits) {
        int mask = (1 << numBits) - 1;
        return (input >> startPosition) & mask;
    }

    int encode(double[] in) {
        int result = 0;
        for (int i=0; i<16; i++) {
            if (in[i] > 0) {
                result += 1;
            }
            result = result << 1;
        }
        return result;
    }

}
