package com.thm.aiarena.ai;

import com.thm.aiarena.ai.util.Utils;
import com.thm.aiarena.array.SimpleLocation;
import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.AResource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString(callSuper = true)
public class NeuralNetworkSimpleObject extends SimpleObject {

    private SimpleNeuralNetwork ai;

    public NeuralNetworkSimpleObject() {
        super();
    }

    @Override
    public void operate() {
        log.debug("operate {}", this);
        SimpleLocation location = (SimpleLocation)getLocation();
        int x = location.getX();
        int y = location.getY();
        AResource resource = location.getAResources().get(0);

        int input = prepareInput();

//        println(input);
//        System.out.print(" --> ");

        int operation = ai.think(input);

//        println(operation);
//        System.out.println();

        switch (operation & 0b00001111) {
            case 0b0000 -> scan();
            case 0b0001 -> move(operation, location);
            case 0b0010 -> gain(resource);
            case 0b0100 -> attack(operation, location);
            case 0b1000 -> ping();
            default -> confused();
        }
    }

    private static void println(int operation) {
        for(int i=0; i<16; i++) {
            System.out.print(Utils.extractBit(operation, i, 1));
        }
    }

    private int prepareInput() {
        int input = (int) getContainer().inventory(SimpleResource.TYPE);
        input >>= 8;
        input <<= 4;
        input += getFiends();
        input <<= 4;
        input += getEnemies();
        return input;
    }

    private void ping() {
        getTransmitter().transmit();
    }

    private void confused() {
        scan();
    }

    private void scan() {
        getSensor().scan();
    }

    private void move(int operation, SimpleLocation location) {
        int moveDirection = ( operation >> 4 ) & 0b1111;
        SimpleLocation targetLocation = calculateTargetLocation(moveDirection, location);
        move(targetLocation);
    }

    private void move(SimpleLocation targetLocation) {
        if (targetLocation == null || !targetLocation.getAObjects().isEmpty()) {
            confused();
        } else {
            getMotorics().move(targetLocation);
        }
    }

    private void attack(int operation, SimpleLocation location) {
        int attackDirection = ( operation >> 8 ) & 0b1111;
        SimpleLocation targetLocation = calculateTargetLocation(attackDirection, location);
        attack(targetLocation);
    }

    private void attack(SimpleLocation targetLocation) {
        if (targetLocation == null || targetLocation.getAObjects().isEmpty()) {
            confused();
        } else {
            AObject target = targetLocation.getAObjects().get(0);
            getWeapon().attack(targetLocation, target);
        }
    }

    private void gain(AResource resource) {
        getManipulator().gain(resource);
    }

    private SimpleLocation calculateTargetLocation(int operation, SimpleLocation location) {
        return (SimpleLocation) switch (operation) {
            case 0b1000 -> getArena().getLocation(location.getX(), location.getY() + 1, 0); // up
            case 0b0010 -> getArena().getLocation(location.getX(), location.getY() - 1, 0); // down
            case 0b0100 -> getArena().getLocation(location.getX() + 1, location.getY(), 0); // right
            case 0b0001 -> getArena().getLocation(location.getX() - 1, location.getY(), 0); // left
            case 0b1000 | 0b0001 -> getArena().getLocation(location.getX() - 1, location.getY() + 1, 0); // up + left
            case 0b0010 | 0b0001 -> getArena().getLocation(location.getX() - 1, location.getY() - 1, 0); // down + left
            case 0b1000 | 0b0100 -> getArena().getLocation(location.getX() + 1, location.getY() + 1, 0); // up + right
            case 0b0010 | 0b0100 -> getArena().getLocation(location.getX() + 1, location.getY() - 1, 0); // down + right
            default -> null;
        };
    }

}
