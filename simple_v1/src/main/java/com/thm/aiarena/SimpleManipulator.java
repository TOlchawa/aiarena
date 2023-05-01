package com.thm.aiarena;

import com.thm.aiarena.model.AResource;
import com.thm.aiarena.model.aobject.Manipulator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleManipulator implements Manipulator {
    @Override
    public void gain(AResource resource) {
        log.info("Manipulator::gain");
    }
}
