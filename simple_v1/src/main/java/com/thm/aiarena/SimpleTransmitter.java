package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.aobject.Sensor;
import com.thm.aiarena.model.aobject.Transmitter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SimpleTransmitter implements Transmitter {

    public static int TRANSMIT_COST = 50;
    private SimpleObject subject;
    @Override
    public void transmit() {
        if (subject.getContainer().change(SimpleResource.TYPE, -TRANSMIT_COST) > 0) {
            log.debug("Transmitter::transmit");
        }
    }

    @Override
    public int response() {
        return subject.getSpecie();
    }
}
