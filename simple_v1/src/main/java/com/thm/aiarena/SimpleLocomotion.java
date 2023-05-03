package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.aobject.Locomotion;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@ToString
public class SimpleLocomotion implements Locomotion {

    public static int MOVE_COST = 200;

    @ToString.Exclude
    private SimpleObject subject;
    @Override
    public void moveTo(ALocation location) {
        log.info("Motorics::move {}", location);
        if (subject.getContainer().change(SimpleResource.TYPE, -MOVE_COST) > 0) {
            subject.getArena().moveAObject(subject, location);
        }
    }
}
