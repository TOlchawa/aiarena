package com.thm.aiarena.ai;

import java.util.Random;

public class SimpleNeuralNetwork {

    private final NeuralNetwork ai;

    public SimpleNeuralNetwork(Random neuralNetworkRandom) {
        ai = new NeuralNetwork(neuralNetworkRandom);
    }

    public int think(int input) {
        return ai.think(input);
    }

    public void save(String filename) {
        ai.save(filename);
    }

    public void load(String filename) {
        ai.load(filename);
    }
}
