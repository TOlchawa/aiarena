package com.thm.aiarena.ai;

import com.thm.aiarena.array.SimpleLocation;
import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.AResource;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString(callSuper = true)
@With
public class NeuralNetworkSimpleObject extends SimpleObject {

    public NeuralNetworkSimpleObject() {
        super();
    }

    @Override
    public void operate() {
        log.info("operate {}", this);
        SimpleLocation location = (SimpleLocation)getLocation();
        AResource resource = location.getAResources().get(0);
        int operation = 0;
        switch (operation & 0b00001111) {
            case 0b00000000 -> ping();
            case 0b00000001 -> move(operation, location);
            case 0b00000010 -> gain(resource);
            case 0b00000100 -> attack(operation, location);
            case 0b00001000 -> scan();
            default -> confused();
        }
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
        SimpleLocation targetLocation = calculateTargetLocation(operation, location);
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
        SimpleLocation targetLocation = calculateTargetLocation(operation, location);
        attack(targetLocation);
    }

    private SimpleLocation calculateTargetLocation(int operation, SimpleLocation location) {
        return (SimpleLocation) switch ((operation & 0b11110000) >> 4) {
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


}
