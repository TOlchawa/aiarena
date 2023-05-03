package com.thm.aiarena;

import com.thm.aiarena.array.SimpleLocation;
import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.aobject.Sensor;
import com.thm.aiarena.model.aobject.Transmitter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
@AllArgsConstructor
@ToString
public class SimpleTransmitter implements Transmitter {

    public static int TRANSMIT_COST = 50;
    public static int NEW_LIFE_COST = 50000;

    @ToString.Exclude
    private SimpleObject subject;

    @Override
    public void transmit() {
//        if (subject.getContainer().change(SimpleResource.TYPE, -TRANSMIT_COST) > 0) {
//            log.debug("Transmitter::transmit");
//            if (subject.getContainer().inventory(SimpleResource.TYPE) > NEW_LIFE_COST) {
//                log.debug("Transmitter::transmit DUPLICATE");
//                SimpleLocation location = (SimpleLocation)subject.getLocation();
//                int x = location.getX();
//                int y = location.getY();
//                SimpleLocation freeLocation = findFreeLocation(x, y);
//                if (freeLocation != null) {
//                    // born
//                }
//            }
//        }
    }

//    private SimpleLocation findFreeLocation(int x, int y) {
//        for (int dx=-1; dx<=1; dx++) {
//            for (int dy=-1; dy<=1; dy++) {
//                if ( dx !=0 || dy != 0) {
//                    SimpleLocation loc = (SimpleLocation)subject.getArena().getLocation(x + dx, y + dy, 0);
//                    if (loc.getAObjects().isEmpty()) {
//                        return loc;
//                    }
//                }
//            }
//        }
//        return null;
//    }

    @Override
    public int response() {
        return subject.getSpecie();
    }
}
