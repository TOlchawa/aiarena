package com.thm.aiarena.model.impl;

import com.thm.aiarena.model.aobject.Sensor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleSensor implements Sensor {
    @Override
    public void scan() {
        log.info("Sensor::scan");
    }
}
