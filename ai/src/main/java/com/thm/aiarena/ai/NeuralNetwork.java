package com.thm.aiarena.ai;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.stream.IntStream;

public class NeuralNetwork {

    private Layer[] layers;

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
            result[i] = Math.min(1.0d, Math.max( -1.0d, buf[i]));
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

    public NeuralNetwork clone() {
        NeuralNetwork result = new NeuralNetwork(new Random());
        Layer[] copyLayers = new Layer[layers.length];
        for (int i=0; i<layers.length; i++) {
            copyLayers[i] = new Layer(layers[i]);
            copyLayers[i].randomize(0.001d);
        }
        result.setLayers(copyLayers);
        return result;
    }

    private void setLayers(Layer[] layers) {
        this.layers = layers;
    }

    public void save(String fileName) {
        Path filePath = Path.of(fileName);

        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(filePath))) {
            outputStream.writeInt(layers.length);
            for (int i=0; i<layers.length; i++) {
                layers[i].save(outputStream);
            }
        } catch (IOException e) {
            System.err.println("Error saving weights to file: " + e.getMessage());
        }

    }

    public void load(String fileName) {
        Path filePath = Path.of(fileName);

        try (DataInputStream inputStream = new DataInputStream(Files.newInputStream(filePath))) {
            int layersCount = inputStream.readInt();
            layers = new Layer[layersCount];
            for (int i=0; i<layersCount; i++) {
                layers[i] = new Layer();
                layers[i].load(inputStream);
            }
        } catch (IOException e) {
            System.err.println("Error saving weights to file: " + e.getMessage());
        }

    }

}
