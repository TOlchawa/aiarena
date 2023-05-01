package com.thm.aiarena.ai;

import com.thm.aiarena.*;

public class NeuralNetworkSimpleObjectFactory {
    public NeuralNetworkSimpleObject create(int spieces) {
        NeuralNetworkSimpleObject result = new NeuralNetworkSimpleObject();
        result.setContainer(new SimpleContainer(result));
        result.setManipulator(new SimpleManipulator(result));
        result.setMotorics(new SimpleMotorics(result));
        result.setSensor(new SimpleSensor(result));
        result.setWeapon(new SimpleWeapon(result));
        result.setTransmitter(new SimpleTransmitter(result));
        result.setSpecies(spieces);
        result.setAi(SimpleNeuralNetworkFactory.createRandomNeuralNetwork());
        return result;
    }
}
