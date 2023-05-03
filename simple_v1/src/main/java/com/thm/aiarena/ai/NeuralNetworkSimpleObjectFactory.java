package com.thm.aiarena.ai;

import com.thm.aiarena.*;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.Creator;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class NeuralNetworkSimpleObjectFactory implements Creator {
    private static final int NEW_CHILD_VALUE = 10000;

    public NeuralNetworkSimpleObject create(int species) {
        NeuralNetworkSimpleObject result = createNeuralSimpleObject(species);
        result.setAi(SimpleNeuralNetworkFactory.createRandomNeuralNetwork());
        return result;
    }

    public NeuralNetworkSimpleObject create(String nnFileName, int species) {
        NeuralNetworkSimpleObject result = createNeuralSimpleObject(species);
        SimpleNeuralNetwork randomNeuralNetwork = SimpleNeuralNetworkFactory.createRandomNeuralNetwork();
        randomNeuralNetwork.load(nnFileName);
        result.setAi(randomNeuralNetwork);
        return result;
    }

    private NeuralNetworkSimpleObject createNeuralSimpleObject(int species) {
        NeuralNetworkSimpleObject result = new NeuralNetworkSimpleObject();
        result.setContainer(new SimpleContainer(result));
        result.setManipulator(new SimpleManipulator(result));
        result.setLocomotion(new SimpleLocomotion(result));
        result.setSensor(new SimpleSensor(result));
        result.setMemory(new SimpleMemory(result));
        result.setWeapon(new SimpleWeapon(result));
        result.setTransmitter(new SimpleTransmitter(result));
        result.setSpecies(species);
        result.setRandom(new Random());
        result.setCreator(this);
        return result;
    }

    @Override
    public AObject newChild(AObject parentObject) {


        NeuralNetworkSimpleObject parent = (NeuralNetworkSimpleObject) parentObject;
        NeuralNetworkSimpleObject child = create(parent.getTransmitter().response());
        SimpleContainer simpleContainer = (SimpleContainer)child.getContainer();
        simpleContainer.setValue(SimpleResource.TYPE, NEW_CHILD_VALUE);
        child.getContainer().change(SimpleResource.TYPE, -NEW_CHILD_VALUE);
        child.setArena(parent.getArena());
        child.setLocation(parent.getLocation());

        log.info("NEW BORN: {} --> {}",parentObject, child);

        return child;
    }
}
