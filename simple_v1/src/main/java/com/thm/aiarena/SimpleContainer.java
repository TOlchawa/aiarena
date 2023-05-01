package com.thm.aiarena;

import com.thm.aiarena.model.AResource;
import com.thm.aiarena.model.aobject.Container;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleContainer implements Container {
    @Override
    public long inventory(AResource resource) {
        log.info("Container::inventory");
        return 0;
    }

    @Override
    public long max(AResource resource) {
        log.info("Container::max");
        return 0;
    }

    @Override
    public long change(AResource resource, long delta) {
        log.info("Container::change");
        return 0;
    }
}
