package com.thm.aiarena.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
public class SimpleNeuralNetworkFactory {

    public static final long SEED = 1234567890L;
    private static Random neuralNetworkRandom = new Random(SEED);

    public static SimpleNeuralNetwork createNeuralNetwork() {
        return new SimpleNeuralNetwork(neuralNetworkRandom);
    }

    public static SimpleNeuralNetwork createRandomNeuralNetwork() {
        return new SimpleNeuralNetwork(new Random());
    }
}
