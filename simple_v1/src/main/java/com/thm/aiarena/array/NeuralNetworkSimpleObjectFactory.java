package com.thm.aiarena.array;

import com.thm.aiarena.*;

import java.util.Properties;

public class NeuralNetworkSimpleObjectFactory {
    public NeuralNetworkSimpleObject create() {
        NeuralNetworkSimpleObject result = new NeuralNetworkSimpleObject();
        result
            .withContainer(new SimpleContainer())
            .withManipulator(new SimpleManipulator())
            .withMotorics(new SimpleMotorics())
            .withSensor(new SimpleSensor())
            .withWeapon(new SimpleWeapon());
        return result;
    }
}
