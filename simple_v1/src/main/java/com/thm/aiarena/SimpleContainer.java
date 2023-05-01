package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.AResource;
import com.thm.aiarena.model.aobject.Container;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleContainer implements Container {

    private SimpleObject subject;

    private int MAX_CAPACITY = 10000;
    private int resource = 1000;

    public SimpleContainer(SimpleObject subject) {
        this.subject = subject;
    }

    @Override
    public int inventory(int type) {
        log.debug("Container::inventory");
        return resource;
    }

    @Override
    public int max(AResource resource) {
        log.debug("Container::max");
        return MAX_CAPACITY;
    }

    @Override
    public int change(int resource, int delta) {
        log.debug ("Container::change");
        int result = delta;
        resource = Math.max(0, result);
        return result;
    }
}