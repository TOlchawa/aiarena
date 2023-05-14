package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.aobject.Sensor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.thm.aiarena.SimpleConst.SCAN_COST;

@Slf4j
@AllArgsConstructor
@ToString
public class SimpleSensor implements Sensor {



    @ToString.Exclude
    private SimpleObject subject;

    @Override
    public void scan() {
        log.debug("Sensor::scan");
        if (subject.getContainer().change(SimpleResource.TYPE, -SCAN_COST) > 0) {
            subject.updateVision();
        }
    }

}
