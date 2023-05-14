package com.thm.aiarena.ai;

import com.thm.aiarena.ai.util.Utils;
import com.thm.aiarena.array.SimpleLocation;
import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.AEvent;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.AResource;
import com.thm.aiarena.model.aobject.Memory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.thm.aiarena.SimpleConst.*;

@Slf4j
@Getter
@Setter
@ToString(callSuper = true)
public class NeuralNetworkSimpleObject extends SimpleObject {

    public static final int UP    = 0b00001000;
    public static final int DOWN  = 0b00000010;
    public static final int RIGHT = 0b00000100;
    public static final int LEFT  = 0b00000001;

    public static final int SCAN     = 0b0000000000000000;
    public static final int MOVE     = 0b0000000000000001;
    public static final int GAIN     = 0b0000000000000010;
    public static final int ATTACK   = 0b0000000000000100;
    public static final int TRANSMIT = 0b0000000000001000;
    public static final int BIRTH    = 0b0000000010000000;

    public static final int MEMORY   = 0b1111000000000000;
    public static final int ACTION   = 0b0000000010001111;


    private Random random;
    private SimpleNeuralNetwork ai;

    public NeuralNetworkSimpleObject() {
        super();
    }

    @Override
    public void operate() {
        log.debug("operate {}", this);
        SimpleLocation location = (SimpleLocation)getLocation();

        AResource resource = location.getAResources().get(0);
        int input = prepareInput();
        int order = ai.think(input);

        switch (order & ACTION) {
            case SCAN -> scan();
            case MOVE -> move(order, location);
            case GAIN -> gain(resource);
            case ATTACK -> attack(order, location);
            case TRANSMIT -> transmit();
            case BIRTH -> birth();
            default -> confused();
        }

        getMemory().remember(order & MEMORY >> 12 );

        if (!alive()) {
            die();
        } else {
            live();
        }

    }

    private void live() {
        getContainer().change(SimpleResource.TYPE, -LIVE_COST);
    }

    private void die() {
        log("D");
        getArena().removeAObject(this);
        getArena().getAllAObjects().remove(this);
        setAi(null);
        setLocation(null);
        setCreator(null);
        setTransmitter(null);
        setContainer(null);
        setManipulator(null);
        setMemory(null);
        setSensor(null);
        setWeapon(null);
        setArena(null);
        setLocomotion(null);
    }

    private boolean alive() {
        return getContainer().inventory(SimpleResource.TYPE) > 0;
    }

    private void birth() {
        if (getContainer().inventory(SimpleResource.TYPE) > ((2 * NEW_CHILD_VALUE) + BIRTH_COST)) {
            SimpleLocation freeLocation = calculateFreeLocation();
            if (freeLocation != null) {
                getContainer().change(SimpleResource.TYPE, -BIRTH_COST);
                birth(freeLocation);
            }
        }
    }

    private void birth(SimpleLocation freeLocation) {
        log("B");
        NeuralNetworkSimpleObject aObject = (NeuralNetworkSimpleObject) getCreator().newChild(this);
        aObject.getLocomotion().moveTo(freeLocation);
        aObject.getArena().getAllAObjects().add(aObject);
        log.debug("new life ; type: {}", aObject.getTransmitter().response());
        getArena().process(new AEvent(AEvent.NEW_BORN, aObject, freeLocation));
    }

    private SimpleLocation calculateFreeLocation() {
        SimpleLocation location = (SimpleLocation)getLocation();
        int x = location.getX();
        int y = location.getY();
        SimpleLocation freeLocation = findFreeLocation(getArena(), x, y);
        return freeLocation;
    }

    SimpleLocation findFreeLocation(AArena arena, int x, int y) {
        List<SimpleLocation> allFreeLocations = new ArrayList<>(9);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    SimpleLocation loc = (SimpleLocation) arena.getLocation(x + dx, y + dy, 0);
                    if (loc.getAObjects().isEmpty()) {
                        allFreeLocations.add(loc);
                    }
                }
            }
        }
        if (allFreeLocations.isEmpty()) {
            return null;
        } else {
            int l = allFreeLocations.size();
            int idx = getRandom().nextInt(l);
            return allFreeLocations.get(idx);
        }
    }

    private static void println(int operation) {
        for(int i=0; i<16; i++) {
            System.out.print(Utils.extractBit(operation, i, 1));
        }
    }

    private int prepareInput() {
        int memory = provideMemoryValue(getMemory());                   // 0b1111000000000000
        int inventory = getContainer().inventory(SimpleResource.TYPE);  // 0b0000111100000000
        int friends = getFiends();                                      // 0b0000000011110000
        int enemies = getEnemies();                                     // 0b0000000000001111
        return prepareInput(memory, inventory, friends, enemies);
    }

    private int provideMemoryValue(Memory memoryProvider) {
        int memory = 0;
        Object memoryObject =  memoryProvider.memory();
        if (memoryObject != null) {
            memory = ((AtomicInteger) memoryObject).get();
        }
        return memory;
    }

    int prepareInput(int memory, int inventory, int friends, int enemies) {
        int result = ( memory & 0b0000000000001111 );
        result <<= 4;
        result |= ( ( inventory >> 8 ) & 0b0000000000001111 );
        result <<= 4;
        result |= ( friends & 0b0000000000001111 );
        result <<= 4;
        result |= ( enemies & 0b0000000000001111 );
        return result;
    }

    private void transmit() {
//        log("T");
        getTransmitter().transmit();
    }

    private void confused() {
        scan();
    }

    private void scan() {
//        log("S");
        getSensor().scan();
    }

    private void log(String action) {
        System.out.print(action);
    }

    private void move(int operation, SimpleLocation location) {
        log("M");
        int moveDirection = ( operation >> 4 ) & 0b1111;
        SimpleLocation targetLocation = calculateTargetLocation(moveDirection, location);
        move(targetLocation);
    }

    private void move(SimpleLocation targetLocation) {
        if (targetLocation == null || !targetLocation.getAObjects().isEmpty()) {
            confused();
        } else {
            getLocomotion().moveTo(targetLocation);
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
            log("A");
            getWeapon().attack(targetLocation, target);
        }
    }

    private void gain(AResource resource) {
//        log("G");
        getManipulator().gain(resource);
    }

    private SimpleLocation calculateTargetLocation(int operation, SimpleLocation location) {
        return (SimpleLocation) switch (operation) {
            case UP -> getArena().getLocation(location.getX(), location.getY() + 1, 0); // up
            case DOWN -> getArena().getLocation(location.getX(), location.getY() - 1, 0); // down
            case RIGHT -> getArena().getLocation(location.getX() + 1, location.getY(), 0); // right
            case LEFT -> getArena().getLocation(location.getX() - 1, location.getY(), 0); // left
            case UP | LEFT -> getArena().getLocation(location.getX() - 1, location.getY() + 1, 0); // up + left
            case DOWN | LEFT -> getArena().getLocation(location.getX() - 1, location.getY() - 1, 0); // down + left
            case UP | RIGHT -> getArena().getLocation(location.getX() + 1, location.getY() + 1, 0); // up + right
            case DOWN | RIGHT -> getArena().getLocation(location.getX() + 1, location.getY() - 1, 0); // down + right
            default -> null;
        };
    }

}
