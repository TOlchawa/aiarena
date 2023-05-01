package com.thm.aiarena;

import com.thm.aiarena.model.aobject.Sensor;
import com.thm.aiarena.model.aobject.Transmitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTransmitter implements Transmitter {
    @Override
    public void transmit() {
        log.info("Transmitter::transmit");
    }
}
