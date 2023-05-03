package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.AResource;
import com.thm.aiarena.model.aobject.Container;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class SimpleContainer implements Container {

    @ToString.Exclude
    private SimpleObject subject;

    private int MAX_CAPACITY = 100000;
    private int value = 10000;

    public SimpleContainer(SimpleObject subject) {
        this.subject = subject;
    }

    @Override
    public int inventory(int type) {
        log.debug("Container::inventory");
        return value;
    }

    @Override
    public int max(AResource resource) {
        log.debug("Container::max");
        return MAX_CAPACITY;
    }

    @Override
    public int change(int resource, int delta) {
        log.debug ("Container::change");
        int result = this.value - delta;
        this.value = Math.max(0, result);
        return result;
    }

    public void setValue(int resourceType, int value) {
        this.value = Math.max(0, value);
    }
}