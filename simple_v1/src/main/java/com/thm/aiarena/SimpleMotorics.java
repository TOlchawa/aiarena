package com.thm.aiarena;

import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.aobject.Motorics;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMotorics implements Motorics {
    @Override
    public void move(ALocation location) {
        log.info("Motorics::move");
    }
}
