package com.thm.aiarena.ai;

import com.thm.aiarena.*;

public class NeuralNetworkSimpleObjectFactory {
    public NeuralNetworkSimpleObject create() {
        NeuralNetworkSimpleObject result = new NeuralNetworkSimpleObject();
        result.setContainer(new SimpleContainer());
        result.setManipulator(new SimpleManipulator());
        result.setMotorics(new SimpleMotorics());
        result.setSensor(new SimpleSensor());
        result.setWeapon(new SimpleWeapon());
        return result;
    }
}
