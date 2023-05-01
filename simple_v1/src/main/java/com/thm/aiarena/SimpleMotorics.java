package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.aobject.Motorics;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SimpleMotorics implements Motorics {

    private SimpleObject subject;
    @Override
    public void move(ALocation location) {
        subject.getArena().moveAObject(subject, location);
        log.info("Motorics::move");
    }
}
