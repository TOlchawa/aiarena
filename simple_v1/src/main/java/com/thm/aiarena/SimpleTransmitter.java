package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.aobject.Sensor;
import com.thm.aiarena.model.aobject.Transmitter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SimpleTransmitter implements Transmitter {

    private SimpleObject subject;
    @Override
    public void transmit() {
        log.info("Transmitter::transmit");
    }

    @Override
    public int response() {
        return subject.getSpecie();
    }
}
